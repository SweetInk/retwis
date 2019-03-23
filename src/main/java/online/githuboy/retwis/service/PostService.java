package online.githuboy.retwis.service;

/**
 * @author suchu
 * @since 2019/3/23 9:14
 */
public interface PostService {

    void post(String userId, String userName, String content);

    void recentlyPosts();
}
