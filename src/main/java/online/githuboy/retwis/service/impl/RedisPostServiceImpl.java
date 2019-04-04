package online.githuboy.retwis.service.impl;

import online.githuboy.retwis.common.DateUtils;
import online.githuboy.retwis.domain.Post;
import online.githuboy.retwis.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author suchu
 * @since 2019/3/23 13:52
 */
@Service
public class RedisPostServiceImpl implements PostService {
    @Autowired
    StringRedisTemplate template;

    @Override
    public void post(String userId, String userName, String content) {
        Long nextPostId = template.opsForValue().increment("retwis:next_post_id");
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("userId", userId);
        dataMap.put("time", new Date().getTime() + "");
        dataMap.put("body", content);
        template.opsForHash().putAll("retwis:post:" + nextPostId, dataMap);
        List<String> result = template.opsForList().range("retwis:followers:" + userId, 0, -1);
        assert result != null;
        result.add(userId);
        result.forEach(uid -> {
            template.opsForList().leftPush("retwis:posts:" + uid, nextPostId + "");
        });
        //  Push the post on the timeline, and trim the timeline to the
        //  newest 1000 elements.
        template.opsForList().leftPush("retwis:timeline", nextPostId + "");
        template.opsForList().trim("retwis:timeline", 0, 1000);
        System.out.println(result);
        //TODO add posters
    }

    @Override
    public List<Post> recentlyPosts(String userId, Integer start, Integer end) {
        HashOperations<String, String, String> hashOperations = template.opsForHash();

        String key = "-1".equals(userId) ? "retwis:timeline" : "retwis:posts:" + userId;
        List<String> result = template.opsForList().range(key, start, end);
        assert result != null;
        return result.stream().map(postId -> {
            String postKey = "retwis:post:" + postId;
            String currentUserId = hashOperations.get(postKey, "userId");
            if (StringUtils.isEmpty(currentUserId)) return null;
            String userName = hashOperations.get("retwis:user:" + currentUserId, "username");
            String time = hashOperations.get(postKey, "time");
            String body = hashOperations.get(postKey, "body");
            assert time != null;
            return Post.builder().content(body).time(DateUtils.strElapsed(new Date(Long.parseLong(time)))).userName(userName).build();
            //hashOperations.get(postKey,)
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
