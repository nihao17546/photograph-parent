package com.nihaov.photograph.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by nihao on 17/6/29.
 */
@Component
public class RedisUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String redisCode = "utf-8";

    @PostConstruct
    public void init() throws Exception {
    }

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    public void set(String key, String value, long liveTime) {
        this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    public void set(String key, String value) {
        this.set(key, value, 0L);
    }

    public void set(byte[] key, byte[] value) {
        this.set(key, value, 0L);
    }

    public String get(final String key) {
        return redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] result=connection.get(key.getBytes());
                    if(result==null)
                        return null;
                    return new String(result, redisCode);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    public void expire(final byte[] key, final long liveTime) {
        if (liveTime > 0) {
            redisTemplate.execute(new RedisCallback() {
                public Long doInRedis(RedisConnection connection) throws DataAccessException {
                    connection.expire(key, liveTime);
                    return 1L;
                }
            });
        }
    }

    public void expire(String key, long liveTime) {
        expire(key.getBytes(),liveTime);
    }

    public Long incr(final String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incr(key.getBytes());
            }
        });
    }

    public Long incr(final String key,final long value) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.incrBy(key.getBytes(), value);
            }
        });
    }

    public List<String> keys(final String pattern) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Set<byte[]> set = redisConnection.keys(pattern.getBytes());
                List<String> se = new ArrayList<String>();
                if(set!=null){
                    for(byte[] by:set){
                        if(by!=null){
                            se.add(new String(by));
                        }
                    }
                }
                return se;
            }
        });
    }

    public Map<String,String> mulGet(final String pattern){
        Map<String,String> result = new LinkedHashMap<>();
        List<String> keyList = new ArrayList<>();
        Set<byte[]> set = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {
            @Override
            public Set<byte[]> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.keys(pattern.getBytes());
            }
        });
        if(set.isEmpty()){
            return result;
        }
        final byte[][] key = new byte[set.size()][];
        int i = 0;
        for(byte[] by:set){
            key[i++] = by;
            keyList.add(new String(by));
        }
        List<String> valueList = redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                List<byte[]> li = redisConnection.mGet(key);
                List<String> re = new ArrayList<>();
                for(int s=0;s<li.size();s++){
                    byte[] by = li.get(s);
                    if(by==null){
                        re.add(null);
                    }
                    else{
                        re.add(new String(by));
                    }
                }
                return re;
            }
        });
        for(int s=0;s<keyList.size();s++){
            String k = keyList.get(s);
            String v = valueList.get(s);
            result.put(k,v);
        }
        return result;
    }


}
