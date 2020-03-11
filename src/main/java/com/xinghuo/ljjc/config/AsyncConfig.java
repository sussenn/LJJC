package com.xinghuo.ljjc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @ClassName AsyncConfig   异步@Async多线程配置
 * @Author sussen
 * @Version 1.0
 * @Data 2019/12/31
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    @Autowired
    private SuRejectHandle rejectHandle;

    @Bean("myAsync")
    public Executor myAsync() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(16);//核心线程数
        executor.setMaxPoolSize(20);//最大线程数
        //传参正值使用无界LinkedBlockingQueue
        //其他传参使用不缓存SynchronousQueue
        executor.setQueueCapacity(6000);//队列长度(超过队列长度无法存储,则开启最大线程数)
        executor.setKeepAliveSeconds(50);//空闲线程最大存活时间 默认60s
        executor.setThreadNamePrefix("myAsync-");//线程名前缀
        executor.setRejectedExecutionHandler(rejectHandle);//任务丢失处理策略
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        return executor;
    }

}
