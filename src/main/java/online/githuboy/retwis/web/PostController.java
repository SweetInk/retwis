package online.githuboy.retwis.web;

import online.githuboy.retwis.domain.User;
import online.githuboy.retwis.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * PostController
 *
 * @author suchu
 * @since 2019/3/23 13:59
 */
@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/")
    public String post(HttpSession session, String content) {
        User user = (User) session.getAttribute("user");
        postService.post(user.getUserId(), user.getUserName(), content);
        System.out.println("content：" + content);
        return "redirect:/home.html";
    }

}
