package com.sauzny.springboot.scheduling;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

/**
 * *************************************************************************
 * 
 * @文件名称: DynamicScheduling.java
 *
 * @包路径 : com.sauzny.springboot.scheduling
 * 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述: 动态定时任务
 * 
 * @创建人: ljx
 *
 * @创建时间: 2018年1月17日 - 上午9:25:30
 * 
 **************************************************************************
 */

@Component
@EnableScheduling
public class DynamicScheduling {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
       return new ThreadPoolTaskScheduler();
    }    
    private Map<String, ScheduledFuture<?>> scheduledFuturePool = Maps.newHashMap();

    public String startCron(Runnable runnable, String cron) {
        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(runnable, new CronTrigger(cron));
        String uuid = UUID.randomUUID().toString();
        scheduledFuturePool.put(uuid, future);
        return uuid;
    }

    public String stopCron(String futureId) {
        ScheduledFuture<?> future = scheduledFuturePool.get(futureId);
        if (future != null) {
            future.cancel(true);
            scheduledFuturePool.remove(futureId);
        }
        return "stop";
    }

    public void updateCron() {
        throw new UnsupportedOperationException("不支持这个操作，请stop，然后重新start一个新的task");
    }

}
