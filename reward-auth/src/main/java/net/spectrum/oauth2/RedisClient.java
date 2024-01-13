package net.spectrum.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisClient {

    @Autowired
    private RedisTemplate<String, Object> template;

    public Object get(String key) {
        return template.opsForValue().get(key);
    }

    public void set(String key, String value) {
        template.opsForValue().set(key, value);
    }

    public boolean delete(String key) {
        return template.delete(key);
    }

    public void set(String key, Object value, long expireTime) {
        template.opsForValue().set(key, value);
        template.expire(key, expireTime, TimeUnit.SECONDS);
    }
}


