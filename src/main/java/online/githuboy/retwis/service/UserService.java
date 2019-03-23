package online.githuboy.retwis.service;

/**
 * UserService
 *
 * @author suchu
 * @since 2019/3/23 9:12
 */
public interface UserService {
    void login(String username, String pwd);

    void register(String username, String pwd);
}
