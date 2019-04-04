package online.githuboy.retwis.service;

import online.githuboy.retwis.domain.Post;

import java.util.List;

/**
 * @author suchu
 * @since 2019/3/23 9:14
 */
public interface PostService {

    void post(String userId, String userName, String content);

    List<Post> recentlyPosts(String userId, Integer start, Integer end);
}
