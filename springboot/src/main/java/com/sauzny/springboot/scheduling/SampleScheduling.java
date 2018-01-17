package com.sauzny.springboot.scheduling;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@Slf4j
public class SampleScheduling{
    
    // 周一 周三周五 11点执行 
    @Scheduled(cron = "0 11 * * 1,3,5 ?")
    public void getLast() {
        
        log.info("SampleScheduling，周一 周三周五 11点执行");
    }
    
    // 没隔3秒执行一次
    @Scheduled(cron = "*/3 * * * * ?")
    public void perSecond() {
        
        log.info("SampleScheduling，没隔3秒执行一次");
    }
    
}
