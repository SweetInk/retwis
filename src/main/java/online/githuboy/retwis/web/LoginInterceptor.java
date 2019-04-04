package online.githuboy.retwis.web;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author suchu
 * @since 2019/3/23 11:24
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            System.out.println("用户未登录");
            response.sendRedirect("index.html");
            return false;
        }
        return true;
    }
}
