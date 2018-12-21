package com.mpf.permission.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

@Service("redisPool")
@Slf4j
public class RedisPool {

    /*获取spring中配置的shardedJedisPool redis的连接池*/
    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    /*通过单例模式获取Redis连接池*/
    public ShardedJedis instance(){
        return shardedJedisPool.getResource();
    }

    public void  safeClose(ShardedJedis shardedJedis){

        try {
            if (shardedJedis != null){
                shardedJedis.close();
            }
        }catch (Exception e){
            log.error("return redis resource exception",e);
        }
    }


}
