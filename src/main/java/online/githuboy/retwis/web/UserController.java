package online.githuboy.retwis.web;

import online.githuboy.retwis.domain.User;
import online.githuboy.retwis.service.FollowService;
import online.githuboy.retwis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * @author suchu
 * @since 2019/3/22 14:43
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    StringRedisTemplate template;


    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    UserService userService;

    @Autowired
    FollowService followService;

    @RequestMapping("/login")
    public String login(String username, String password, Model model, HttpSession session) {
        try {
            User user = userService.login(username, password);
            session.setAttribute("user", user);
            return "redirect:/home.html";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
            return "index";
        }

    }

    static class TUser implements Serializable {
        public Integer a;
        public String b;
    }

    @RequestMapping("/register")
    public String register(String username, String password, Model model, HttpSession session) {
        try {
            User user = userService.register(username, password);
            session.setAttribute("user", user);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/home.html";
    }

    @GetMapping("/follow")
    public String follow(HttpSession session, String uid) {
        try {
            User user1 = userService.queryById(uid);
            User logined = (User) session.getAttribute("user");
            followService.follow(logined.getUserId(), uid, true);
            return "redirect:/profile.html?u=" + user1.getUserName();
        } catch (Exception e) {
            return "redirect:/home.html";
        }

    }

    @GetMapping("/unfollow")
    public String unFollow(HttpSession session, String uid) {
        try {
            User user1 = userService.queryById(uid);
            User logined = (User) session.getAttribute("user");
            followService.follow(logined.getUserId(), uid, false);
            return "redirect:/profile.html?u=" + user1.getUserName();
        } catch (Exception e) {
            return "redirect:/home.html";
        }
    }
}
