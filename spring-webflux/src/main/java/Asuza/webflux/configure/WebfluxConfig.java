package Asuza.webflux.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Azusa-Yuan
 * @description
 * @github <a href="https://github.com/Azusa-Yuan">...</a>
 * @Copyright Azusa-Yuan
 */
@Configuration
public class WebfluxConfig {
    @Bean
    public WebClient webClient() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("custom")
                .maxConnections(3) // 设置最大并发连接数为 3
                .pendingAcquireMaxCount(2 * 3) // 设置等待队列大小，根据需要调整
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider);

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    // 设置线程池
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        // 核心线程池大小
        int corePoolSize = 2;
        // 最大线程池大小
        int maximumPoolSize = 5;
        // 线程池中超过corePoolSize数目的空闲线程最大存活时间  这个当时答错了
        long keepAliveTime = 60;
        // 时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        // 线程池中的任务队列
        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        // 创建线程池
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
}
