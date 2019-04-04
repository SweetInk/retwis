package online.githuboy.retwis.web;

import online.githuboy.retwis.domain.FFCounter;
import online.githuboy.retwis.domain.Post;
import online.githuboy.retwis.domain.User;
import online.githuboy.retwis.service.FollowService;
import online.githuboy.retwis.service.PostService;
import online.githuboy.retwis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * @author suchu
 * @since 2019/3/21 17:54
 */
@Controller
public class IndexController {
    @Autowired
    StringRedisTemplate template;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @RequestMapping("/index.html")
    public String index(HttpSession session, Model model) {
        Object user = session.getAttribute("user");
        if (null != user)
            return "redirect:/home.html";
        model.addAttribute("a", true);
        ModelAndView mv;
        return "index";
    }

    @RequestMapping("/home.html")
    public String home(Model model, HttpSession session) {
        model.addAttribute("a", true);
        User user = (User) session.getAttribute("user");
        List<Post> posts = postService.recentlyPosts("-1", 0, 50);

        model.addAttribute("posts", posts);
        FFCounter counter = userService.countFF(user.getUserId());
        model.addAttribute("counter", counter);
        ModelAndView mv;
        return "home";
    }


    @RequestMapping("/timeline.html")
    public String timeline(Model model) {
        model.addAttribute("a", true);
        List<Post> posts = postService.recentlyPosts("-1", 0, 50);
        Set<String> strings = userService.queryLastUsers();
        model.addAttribute("users", strings);
        model.addAttribute("posts", posts);
        return "timeline";
    }

    @RequestMapping("/profile.html")
    public String profile(HttpSession session, Model model, String u, @RequestParam(required = false, defaultValue = "0") Integer start) {

        User login = (User) session.getAttribute("user");
        model.addAttribute("a", true);
        try {
            User user = userService.queryByUName(u);
            if (login != null) {
                if (!user.getUserId().equals(login.getUserId())) {
                    //query the following status
                    boolean followed = followService.isFollowed(login.getUserId(), user.getUserId());
                    model.addAttribute("followed", followed);
                    model.addAttribute("show_follow", true);
                }
            }
            model.addAttribute("ru", user);
            List<Post> posts = postService.recentlyPosts(user.getUserId(), start, 10);
            model.addAttribute("posts", posts);
            return "profile";
        } catch (Exception e) {
            return "redirect:/home.html";
        }

     /*   List<Post> posts = postService.recentlyPosts("-1", 0, 50);
        Set<String> strings = userService.queryLastUsers();
        model.addAttribute("users", strings);
        model.addAttribute("posts", posts);*/

    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
