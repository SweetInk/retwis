package online.githuboy.retwis.web;

import online.githuboy.retwis.domain.Post;
import online.githuboy.retwis.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public String home(Model model) {
        model.addAttribute("a", true);
        List<Post> posts = postService.recentlyPosts("-1", 0, 50);
        model.addAttribute("posts", posts);
        ModelAndView mv;
        return "home";
    }


    @RequestMapping("/timeline.html")
    public String timeline(Model model) {
        model.addAttribute("a", true);
        List<Post> posts = postService.recentlyPosts("-1", 0, 50);
        model.addAttribute("posts", posts);
        ModelAndView mv;
        return "timeline";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
