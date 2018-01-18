package com.sauzny.springboot;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableTransactionManagement
@Slf4j
public class EnableTransaction {

    @PostConstruct
    public void init(){
        log.info("@EnableTransactionManagement  --   开启了事务");
    }
}
