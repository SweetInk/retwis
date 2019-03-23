package online.githuboy.retwis.web;

import online.githuboy.retwis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author suchu
 * @since 2019/3/22 14:43
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    StringRedisTemplate template;

    private HashOperations<String, String, String> hashOperations = template.opsForHash();
    private ListOperations<String, String> listOperations = template.opsForList();

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(String user, String pwd, Model model) {
        try {
            userService.login(user, pwd);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "index";
    }

    @RequestMapping("/register")
    public String register(String username, String password, Model model) {
        try {
            userService.register(username, password);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "index";
    }
}
