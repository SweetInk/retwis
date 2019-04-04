package online.githuboy.retwis.web;

import online.githuboy.retwis.domain.User;
import online.githuboy.retwis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        return "index";
    }
}
