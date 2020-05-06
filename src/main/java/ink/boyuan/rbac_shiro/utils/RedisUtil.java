package ink.boyuan.rbac_shiro.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wyy
 * @version 1.0
 * @date 2019/11/21 8:54
 * @description
 **/
@Component
public class RedisUtil implements Serializable {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param offset 位 8Bit=1Byte
     * @return
     */
    public boolean setBit(String key, long offset, boolean isShow) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.setBit(key, offset, isShow);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 写入缓存
     *
     * @param key
     * @param offset
     * @return
     */
    public boolean getBit(String key, long offset) {
        boolean result = false;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            result = operations.getBit(key, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 写入缓存设置过期时间
     *
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean setExpire(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 批量删除
     *
     * @param keys
     * @return
     */
    public boolean batchDel(final String... keys) {
        boolean result = false;
        try {
            Arrays.stream(keys)
                    .forEach(x -> redisTemplate.delete(x));
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据key删除value
     *
     * @param key
     * @return
     */
    public boolean delForValue(final String key) {
        boolean result = false;
        try {
            Boolean delete = redisTemplate.delete(key);
            result = delete;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断是否存在key
     *
     * @param key
     * @return
     */
    public boolean isExist(final String key) {
        boolean result = false;
        try {
            result = redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入 hash 值
     *
     * @param key
     * @param hashKey
     * @param hValue
     * @return
     */
    public boolean hmSet(String key, Object hashKey, Object hValue) {
        boolean result = false;
        try {
            HashOperations hashOperations = redisTemplate.opsForHash();
            hashOperations.put(key, hashKey, hValue);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取hash值
     *
     * @param key
     * @param hKey
     * @return
     */
    public Object hmGet(String key, Object hKey) {
        Object result = null;
        HashOperations hashOperations = redisTemplate.opsForHash();
        result = hashOperations.get(key, hKey);
        return result;
    }


    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void setList(String k, Object v) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List<Object> getList(String k, long l, long l1) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void add(String key, Object value) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key, value);
    }

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set<Object> setMembers(String key) {
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param value1
     */
    public void zAdd(String key, Object value, Double value1) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        zSet.add(key, value, value1);
    }


    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     */
    public Double zSetScore(String key, Object value) {
        ZSetOperations<String, Object> zSet = redisTemplate.opsForZSet();
        return zSet.score(key, value);
    }

}
