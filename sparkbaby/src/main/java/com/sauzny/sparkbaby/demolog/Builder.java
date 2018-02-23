package com.sauzny.sparkbaby.demolog;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * *************************************************************************
 * @文件名称: Builder.java
 *
 * @包路径  : com.sauzny.sparkbaby.demolog 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   制造demo日志
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年9月19日 - 下午3:17:29 
 *	
 **************************************************************************
 */
public class Builder {
    
    private static void build(){
        
        ExecutorService service = Executors.newFixedThreadPool(20);
        
        for (int i=0; i<50*100*100; i++) {
            
            service.execute(new Worker());
        }
        service.shutdown();
    }
    
    public static void main(String[] args) {
        Builder.build();
    }
}

class Worker implements Runnable {
    
    private static final Logger logger = LoggerFactory.getLogger(Builder.class);

    @Override
    public void run() {
        
        int case_int = RandomUtils.nextInt(0,5);
        
        switch (case_int) {
            
            case 0: logger.trace(Student.getOne().toJSon()); break;
            case 1: logger.debug(Student.getOne().toJSon()); break;
            case 2: logger.info(Student.getOne().toJSon()); break;
            case 3: logger.warn(Student.getOne().toJSon()); break;
            case 4: logger.error(Student.getOne().toJSon()); break;
                
            default:
                break;
        }
        
    }
    
}