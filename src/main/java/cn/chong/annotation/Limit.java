package cn.chong.annotation;

import java.lang.annotation.*;

/**
 * @author: tangchongjie
 * @creattime: 2023--05--18 21:04
 * @description 使用redis与ip做限流的自定义注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Limit {

    // 资源名称，用于描述接口功能
    String name() default "";

    // key prefix
    String prefix() default "";

    // 时间的，单位秒
    int period();

    // 限制访问次数
    int count();

}