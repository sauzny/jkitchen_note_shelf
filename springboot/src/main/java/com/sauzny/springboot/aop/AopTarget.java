package com.sauzny.springboot.aop;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Component  
@Slf4j
public class AopTarget {

    @PostConstruct
    public void init(){
        log.info("AopTarget init ... ");
    }
    
	public void say(){
		log.info("hello world");
	}
}
