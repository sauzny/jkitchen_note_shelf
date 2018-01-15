package com.sauzny.guava.eventbus;

import java.util.concurrent.Executors;

import org.junit.Test;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * *************************************************************************
 * @文件名称: GuavaEventBus.java
 *
 * @包路径  : com.sauzny.jkitchen_note.guava.eventbus 
 *				 
 * @版权所有: Personal xinxin (C) 2016
 *
 * @类描述:  消息事件总线
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年8月3日 - 上午9:56:28 
 *	
 **************************************************************************
 */
public class GuavaEventBus {
    
    
    final EventBus eventBus = new EventBus();
    
    // 异步消息总线
    AsyncEventBus asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(4));
    
    // @Subscribe 默认是使用了synchronize同步
    // @AllowConcurrentEvents 允许多线程执行
    // 如果当前观察者（method）是线程安全的thread-safe，建议增加注解@AllowConcurrentEvents，以减少同步开销。
    // 对于使用的是异步（AsyncEventBus），也建议增加@AllowConcurrentEvents，因为不需要进行同步。
    
    @Test
    public void foo01() {
        eventBus.register(new Object() {
            
            @AllowConcurrentEvents
            @Subscribe
            public void lister(Integer integer) {
                System.out.printf("%s from int%n", integer);
            }

            @AllowConcurrentEvents
            @Subscribe
            public void lister(Number integer) {
                System.out.printf("%s from Number%n", integer);
            }

            @AllowConcurrentEvents
            @Subscribe
            public void lister(Long integer) {
                System.out.printf("%s from long%n", integer);
            }

            @AllowConcurrentEvents
            @Subscribe
            public void lister(Student student) {
                System.out.println(student.toString());
            }

            @AllowConcurrentEvents
            @Subscribe
            public void lister(DeadEvent event) {
                System.out.printf("%s=%s from dead events%n", event.getSource().getClass(), event.getEvent());
            }
            
        });
    
        eventBus.post(1);
        eventBus.post(1L);
        
        eventBus.post(new Student(){{this.setId("123");this.setName("name");}});
        
        eventBus.post("这是一个没未被  监听的事件");
        
    }
    
}

class Student{

    private String id;
    private String name;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + "]";
    }
    
}
