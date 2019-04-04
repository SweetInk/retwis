package online.githuboy.retwis.service;

import online.githuboy.retwis.domain.User;

/**
 * UserService
 *
 * @author suchu
 * @since 2019/3/23 9:12
 */
public interface UserService {
    User login(String username, String pwd);

    User register(String username, String pwd);
}
