package com.yitong.locationpusher.common.thread;

import com.yitong.locationpusher.service.task.PusherJpeLocationInfoTask;
import com.yitong.locationpusher.service.task.PusherLocationInfoTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@SuppressWarnings("all")
public class ExecutorConfig {

    private final int corePoolSize = 2;  // 核心线程
    private final int maxPoolSize = 5;   //允许最大线程
    private final int queueCapacity = 1000;  //最大等待任务数量

    @Resource
    PusherLocationInfoTask pusherLocationInfoTask;

    @Resource
    PusherJpeLocationInfoTask pusherJpeLocationInfoTask;

    @Bean(name = "pusherExecutor")
    public ScheduledFuture pusherExecutor() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        ScheduledFuture scheduledFuture = scheduledThreadPoolExecutor.scheduleAtFixedRate(pusherLocationInfoTask, 0, 200, TimeUnit.MILLISECONDS);
        return scheduledFuture;
    }

    @Bean(name = "pusheJperExecutor")
    public ScheduledFuture pusheJperExecutor() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2);
        ScheduledFuture scheduledFuture = scheduledThreadPoolExecutor.scheduleAtFixedRate(pusherJpeLocationInfoTask, 0, 200, TimeUnit.MILLISECONDS);
        return scheduledFuture;
    }



}
