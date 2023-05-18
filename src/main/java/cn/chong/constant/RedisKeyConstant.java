package cn.chong.constant;

import cn.chong.annotation.Limit;

/**
 * @author: tangchongjie
 * @creattime: 2023--05--05 17:01
 * @description redis的key
 */
public interface RedisKeyConstant {


    /**
     * 重建label缓存时，防止缓存击穿问题使用的分布式锁
     */
    String UPDATE_WALLET_LOCK_KEY = "label:lock";

    /**
     * 重建label缓存分布式锁使用的key的过期时间
     */
    Long EXPIRE_TIME = 30l;

    /**
     * 对消费接口（用户钱包数据进行更改的接口）进行幂等校验的key
     */
    String MDN_CONSUMER_KEY = "mdn:consumer:key";

    /**
     * 对消费接口（用户钱包数据进行更改的接口）进行幂等校验的key的过期时间，也是同样请求再次请是的限制时间间隔
     */
    Long MDN_EXPIRE_TIME = 5 * 1000l;

    /**
     * 在进行跟新redis中的数据时，延迟双删的第二次删除前延迟的时间，毫秒
     */
    Long UPDATE_SLEEP_TIME = 3 * 6 * 1000l;

    /**
     * redis限流的key
     */
    String LIMIT_IP_KEY = "limit:";

}
