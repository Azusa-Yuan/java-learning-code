package com.atguigu.boot3.actuator.aspect;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Azusa-Yuan
 * @description 用于监控的切面
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
@Component
@Slf4j
@Aspect
public class MetricsAspect {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    MeterRegistry registry;

    private Counter counter_all_total;

    // 统计在线数
    private AtomicInteger pc_online_count;

    class Information{
        Counter total;
        Counter fail;
        Information(String name){
            total = registry.counter(name, "total", "count");
            fail = registry.counter(name, "fail", "count");
        }
    }

    @PostConstruct
    public void init() {
        counter_all_total = registry.counter("pc_aop_all_requests_count", "aop_all_method", "count");
        pc_online_count = registry.gauge("pc_all_online_count", new AtomicInteger(0));
    }

    ConcurrentHashMap<String, Information> methodMap = new ConcurrentHashMap<>();
    @Pointcut("@annotation(com.atguigu.boot3.actuator.aspect.MetricsApi)")
    public void aopPoint() {
    }
    /**
     * 这是一个在 Spring AOP 中使用的切面（Aspect）注解。具体含义如下：
     *
     *     @Around: 这是一个通知注解，用于标识一个环绕通知。环绕通知是 AOP 中的一种通知类型，它可以在目标方法执行之前和之后都执行一段代码逻辑。
     *     "aopPoint()": 这是一个切入点表达式，指定了切入的位置。切入点表达式定义了哪些方法会被通知所影响。在这里，aopPoint() 是一个切入点表达式，表示切入到 aopPoint 方法所在的位置。
     *     @annotation(metricsApi): 这是一个注解切点，它指定了切入到带有特定注解的方法中。metricsApi 是一个自定义的注解类，在切面中，带有这个注解的方法会被通知所影响。
     *
     * 综合起来，上述代码表示在满足切入点 aopPoint() 并且目标方法带有 metricsApi 注解的情况下，会执行环绕通知的代码逻辑。这可以用来在特定注解的方法执行前后添加一些额外的逻辑，例如性能监控、日志记录等。
     * @param jp
     * @param metricsApi
     * @throws Throwable
     */
    @Around("aopPoint() && @annotation(metricsApi)")
    public Object doRouter(ProceedingJoinPoint  jp, MetricsApi metricsApi) throws Throwable{
        counter_all_total.increment();
        // 计时
        long startTime = System.currentTimeMillis();

        String totalName = getClassMethodName(jp);
        // 计数
        methodMap.getOrDefault(totalName, new Information(totalName)).total.increment();
        Object result = jp.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info(totalName + "----" + "执行时间：" + executionTime + "ms");
        // 返回结果
        return result;
    }
    /**
     * 获取请求的接口名
     * @param joinPoint
     * @return
     */
    private String getClassMethodName(JoinPoint joinPoint) {
        // 获取类名
        String className = joinPoint.getTarget().getClass().getName();
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取参数个数  一定程度上防止重载
        long argsNum = joinPoint.getArgs().length;
        return applicationName + "-" + className + "-" + methodName + "(" + argsNum + ")";
    }
}
