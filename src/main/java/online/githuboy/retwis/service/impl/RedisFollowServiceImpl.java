package online.githuboy.retwis.service.impl;

import online.githuboy.retwis.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The follow service base on redis implementation
 *
 * @author suchu
 * @since 2019/4/4 14:21
 */
@Service
public class RedisFollowServiceImpl implements FollowService {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public void follow(String curId, String opId, boolean follow) {
        if (curId.equals(opId)) {
            throw new IllegalArgumentException("user id:" + curId + " cannot follow or Unfollow yourself");
        }
        Date now = new Date();
        //follow
        if (follow) {
            redisTemplate.opsForZSet().add("retwis:followers:" + opId, curId, now.getTime());
            redisTemplate.opsForZSet().add("retwis:following:" + curId, opId, now.getTime());
        } else {
            //un follow
            redisTemplate.opsForZSet().remove("retwis:followers:" + opId, curId);
            redisTemplate.opsForZSet().remove("retwis:following:" + curId, opId);
        }
    }

    @Override
    public boolean isFollowed(String curId,String opId){
        Double score = redisTemplate.opsForZSet().score("retwis:following:" + curId, opId);
        return score !=null;
    }
}
