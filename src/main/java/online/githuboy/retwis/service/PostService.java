package online.githuboy.retwis.service;

import online.githuboy.retwis.domain.Post;

import java.util.List;

/**
 * @author suchu
 * @since 2019/3/23 9:14
 */
public interface PostService {

    /**
     * Post the content
     * @param userId user id
     * @param userName user name;
     * @param content content
     */
    void post(String userId, String userName, String content);

    /**
     * Query post list with pagination
     * @param userId user id
     * @param start startIndex
     * @param end endIndex
     * @return post list;
     */
    List<Post> recentlyPosts(String userId, Integer start, Integer end);
}
