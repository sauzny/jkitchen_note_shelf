package com.sauzny.springboot.aop;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Component  
@Slf4j
public class AopTarget {
    
	public void say(){
		log.info("hello world");
	}
}
