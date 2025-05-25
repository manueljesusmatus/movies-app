package cl.dev.mmatush.moviesapp.service.impl;

import cl.dev.mmatush.moviesapp.service.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCacheServiceImpl implements RedisCacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void deleteCacheByKey(String key) {
        log.info("Borrando cache <key: {}>", key);
        redisTemplate.delete(key);
    }

}
