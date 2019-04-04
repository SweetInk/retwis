package online.githuboy.retwis.service.impl;

import online.githuboy.retwis.common.RetwisException;
import online.githuboy.retwis.domain.User;
import online.githuboy.retwis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis based  UserService implementation
 *
 * @author suchu
 * @since 2019/3/23 9:16
 */
@Service
public class RedisUserServiceImpl implements UserService {

    @Autowired
    StringRedisTemplate template;


    @Override
    public User login(String username, String pwd) {
        HashOperations<String, String, String> hashOperations = template.opsForHash();
        String userId = hashOperations.get("retwis:users", username);
        if (StringUtils.isEmpty(userId)) {
            throw new RetwisException("The username or password incorrect");
        }
        String password = hashOperations.get("retwis:user:" + userId, "password");
        if (!pwd.equals(password)) {
            throw new RetwisException("The username or password incorrect");
        }
        return User.builder().userId(userId).userName(username).build();
    }

    @Override
    public User register(String username, String password) {
        HashOperations<String, String, String> hashOperations = template.opsForHash();
        ListOperations<String, String> listOperations = template.opsForList();
        String user = hashOperations.get("retwis:users", username);
        if (!StringUtils.isEmpty(user)) {
            throw new RetwisException("Username has been used");
        }
        Long userId = template.opsForValue().increment("retwis:next_user_id");
        Map<String, String> dataMap = new HashMap<>(2);
        dataMap.put("userId", userId + "");
        dataMap.put("username", username);
        dataMap.put("password", password);
        hashOperations.put("retwis:users", username, userId + "");
        hashOperations.putAll("retwis:user:" + userId, dataMap);
        return User.builder().userId(String.valueOf(userId)).userName(username).build();

    }
}
