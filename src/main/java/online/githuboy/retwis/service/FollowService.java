package online.githuboy.retwis.service;

/**
 * @author suchu
 * @since 2019/4/4 14:20
 */

public interface FollowService {

    /**
     * Follow or un follow the user.<br/>
     * e.g: if follow is true, then curId will follow the opId:
     *
     * @param curId  current logined id
     * @param opId   target user id
     * @param follow true:follow, false: unfollow
     * @throws IllegalArgumentException if the curId equals the opId
     */
    void follow(String curId, String opId, boolean follow);

    /**
     * Check whether the curId is following the opId
     *
     * @param curId current logined user
     * @param opId  target user id
     * @return true: curId is following the  opId;
     */
    boolean isFollowed(String curId, String opId);
}
