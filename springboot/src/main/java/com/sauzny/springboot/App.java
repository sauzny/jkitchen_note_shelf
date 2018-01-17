package com.sauzny.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;

/**
 * *************************************************************************
 * @文件名称: App.java
 *
 * @包路径  : com.sauzny.springboot 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   服务启动类
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2018年1月16日 - 下午4:13:11 
 *	
 **************************************************************************
 */
@SpringBootApplication
public class App {
    
    public static void main(String[] args) {
        
        start(args);
    }
    
    private static void start(String[] args){
        
        // 结束前执行的钩子
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                App.stop();
            }
        });

        SpringApplication app = new SpringApplication(App.class);
        // 启动，写PID文件，文件path可在 application.properties 中配置
        app.addListeners(new ApplicationPidFileWriter("PID"));
        app.run(args);
    }
    
    private static void status(){
        
    }
    
    private static void stop() {
        System.out.println("finagle thrift server stop");
    }
}
