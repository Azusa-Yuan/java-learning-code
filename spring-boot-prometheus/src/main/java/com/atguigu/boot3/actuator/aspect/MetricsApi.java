package com.atguigu.boot3.actuator.aspect;

import java.lang.annotation.*;

/**
 * @author Azusa-Yuan
 * @description 用于监控的注解
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface MetricsApi {
    String name();
}
