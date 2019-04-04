package online.githuboy.retwis.service;

import online.githuboy.retwis.domain.FFCounter;
import online.githuboy.retwis.domain.User;

import java.util.Set;

/**
 * UserService
 *
 * @author suchu
 * @since 2019/3/23 9:12
 */
public interface UserService {
    /**
     *  user login validate
     * @param username the user name
     * @param pwd the user password
     * @return logined user info
     * @throws online.githuboy.retwis.common.RetwisException if the username not matched the password
     */
    User login(String username, String pwd);

    /**
     * Query the user info by username;
     * @param username the user name;
     * @return the user info
     */
    User queryByUName(String username);

    /**
     * Query the user info by user id
     * @param userId the user id;
     * @return the user info
     */
    User queryById(String userId);

    /**
     * Register user info
     * @param username user name;
     * @param pwd user password
     * @return the user info
     */
    User register(String username, String pwd);

    /**
     * Query the recently registered users;
     * @return user list
     */
    Set<String> queryLastUsers();

    /**
     * Count the followers and the following of the user;
     * @param userId user id
     * @return FFCounter
     */
    FFCounter countFF(String userId);
}
