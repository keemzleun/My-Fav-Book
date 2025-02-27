package beyond.myfavoirtebook.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Component
public class RedisService {
    private final RedisTemplate<String, Object> emailRedisTemplate;

    @Autowired
    public RedisService(@Qualifier("3") RedisTemplate<String, Object> emailRedisTemplate) {
        this.emailRedisTemplate = emailRedisTemplate;
    }

    public void setValues(String key, String data, Duration duration){
        ValueOperations<String, Object> values = emailRedisTemplate.opsForValue();
        values.set(key, data, duration);
    }

    @Transactional(readOnly = true)
    public String getValues(String key){
        ValueOperations<String, Object> values = emailRedisTemplate.opsForValue();
        if (values.get(key) == null){
            return "false";
        }
        return (String) values.get(key);
    }

    public void deleteValues(String key){
        emailRedisTemplate.delete(key);
    }

    public boolean checkExistValue(String value){
        return !value.equals("false");
    }

}
