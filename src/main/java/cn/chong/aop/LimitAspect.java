package cn.chong.aop;

import cn.chong.annotation.Limit;
import cn.chong.common.ErrorCode;
import cn.chong.constant.RedisKeyConstant;
import cn.chong.exception.BusinessException;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HttpUtil;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author: tangchongjie
 * @creattime: 2023--05--18 21:09
 * @description 使用请求的ip的redis来做限流操作
 */
@Slf4j
@Aspect
@Component
public class LimitAspect {

    private final RedisTemplate<String, Serializable> limitRedisTemplate;

    @Autowired
    public LimitAspect(RedisTemplate<String, Serializable> limitRedisTemplate) {
        this.limitRedisTemplate = limitRedisTemplate;
    }

    //定义切点
    @Pointcut("@annotation(annotation.Limit)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Limit limitAnnotation = method.getAnnotation(Limit.class);
        String name = limitAnnotation.name();

        //从请求中获取ip
        String ip = ServletUtil.getClientIP(request);
        int limitPeriod = limitAnnotation.period();
        int limitCount = limitAnnotation.count();

        ImmutableList<String> keys = ImmutableList.of(RedisKeyConstant.LIMIT_IP_KEY + limitAnnotation.prefix() + ":" + ip);

        String luaScript = buildLuaScript();
        RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
        Number count = limitRedisTemplate.execute(redisScript, keys, limitCount, limitPeriod);

        log.info("IP:{} 第 {} 次访问key为 {}，描述为 [{}] 的接口", ip, count, keys, name);

        if (count != null && count.intValue() <= limitCount) {
            //放行
            return point.proceed();
        } else {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "接口访问超出频率限制");
        }
    }

    /**
     * 限流脚本
     * 调用的时候不超过阈值，则直接返回并执行计算器自加。
     *
     * @return lua脚本
     */
    private String buildLuaScript() {
        return "local c" +
                "\nc = redis.call('get',KEYS[1])" +
                "\nif c and tonumber(c) > tonumber(ARGV[1]) then" +
                "\nreturn c;" +
                "\nend" +
                "\nc = redis.call('incr',KEYS[1])" +
                "\nif tonumber(c) == 1 then" +
                "\nredis.call('expire',KEYS[1],ARGV[2])" +
                "\nend" +
                "\nreturn c;";
    }

}

