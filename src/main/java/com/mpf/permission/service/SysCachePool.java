package com.mpf.permission.service;

import com.google.common.base.Joiner;
import com.mpf.permission.bean.CacheKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;

@Service
@Slf4j
public class SysCachePool {

    @Resource(name = "redisPool")
    private RedisPool redisPool;

    public void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants perfix){

        saveCache(toSaveValue,timeoutSeconds,perfix,null);

    }

    private void saveCache(String toSaveValue, int timeoutSeconds, CacheKeyConstants perfix, String... args) {
        if (toSaveValue == null){
            return;
        }
        ShardedJedis shardedJedis = null;

        try {
            String cacheKey = generatorCacheKey(perfix,args);

            shardedJedis = redisPool.instance();
            shardedJedis.setex(cacheKey,timeoutSeconds,toSaveValue);
        }catch (Exception e){
            log.error("get connection error!save cache exception prefix :{},keys :{}" , perfix,args,e);
        }finally {
            redisPool.safeClose(shardedJedis);
        }
    }

    public String getFromCache(CacheKeyConstants prefix,String... keys){
        ShardedJedis shardedJedis = null;
        String cacheKey = generatorCacheKey(prefix, keys);
        try {
            shardedJedis = redisPool.instance();
            String value = shardedJedis.get(cacheKey);
            return value;
        }catch (Exception e){
            log.error("get cache error",e);
            return null;
        }finally {
            redisPool.safeClose(shardedJedis);
        }

    }

    private String generatorCacheKey(CacheKeyConstants prefix,String... keys){
        String key = prefix.name();

        if (keys != null && keys.length > 0){
            key += "_" +Joiner.on("_").join(keys);
        }
        return key;
    }

}
