package online.githuboy.retwis.service.impl;

import online.githuboy.retwis.common.RetwisException;
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

    private HashOperations<String, String, String> hashOperations = template.opsForHash();

    private ListOperations<String,String> listOperations = template.opsForList();


    @Override
    public void login(String username, String pwd) {
        if (!template.opsForHash().hasKey("user", "suchuxx")) {
            throw new RetwisException("The username or password not correct");
        }
    }

    @Override
    public void register(String username, String password) {
        String user = hashOperations.get("retwis:users", username);
        if(!StringUtils.isEmpty(user)){
            throw new RetwisException("username has been used");
        }
        Long userId = template.opsForValue().increment("retwis:next_user_id");

        Map<String,String> dataMap = new HashMap<>(2);
        dataMap.put("userId",userId+"");
        dataMap.put("username",username);
        dataMap.put("password",password);
        hashOperations.put("retwis:users",username,userId+"");
        hashOperations.putAll("retwis:user:"+userId,dataMap);
    }
}
