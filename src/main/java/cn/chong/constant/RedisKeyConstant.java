package cn.chong.constant;

/**
 * @author: tangchongjie
 * @creattime: 2023--05--05 17:01
 * @description redis的key
 */
public interface RedisKeyConstant {

    /**
     * 缓存在redis中的树状label数据的key
     */
    String LABEL_DATA_KEY = "label:data:all";

    /**
     * 以label的labelName为key和label的id为value的map
     */
    String LABEL_MAP_KEY = "label:data:map";

    /**
     * 重建label缓存时，防止缓存击穿问题使用的分布式锁
     */
    String UPDATE_WALLET_LOCK_KEY = "label:lock";

    /**
     * 重建label缓存分布式锁使用的key的过期时间
     */
    Long EXPIRE_TIME = 30l;

    /**
     * 在进行跟新redis中的数据时，延迟双删的第二次删除前延迟的时间，毫秒
     */
    Long UPDATE_SLEEP_TIME = 3 * 6 * 1000l;

}
