package online.githuboy.retwis.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author suchu
 * @since 2019/3/21 17:54
 */
@Controller
public class IndexController {
    @Autowired
    StringRedisTemplate template;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("a", true);
        ModelAndView mv;
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
