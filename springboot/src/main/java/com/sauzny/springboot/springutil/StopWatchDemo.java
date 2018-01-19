package com.sauzny.springboot.springutil;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

import com.sauzny.springboot.tools.MyTools;

/**
 * *************************************************************************
 * @文件名称: StopWatchDemo.java
 *
 * @包路径  : com.sauzny.springboot.springutil 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   耗时统计
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2018年1月19日 - 下午1:01:17 
 *	
 **************************************************************************
 */
public class StopWatchDemo {
    
    public static void work() {
        System.out.println("数据抓取任务");
        StopWatch clock = new StopWatch("业务1");
        clock.start("任务1");
        MyTools.sleep(100L);
        clock.stop();
        clock.start("任务2");
        MyTools.sleep(200L);
        clock.stop();
        clock.start("任务3");
        MyTools.sleep(300L);
        clock.stop();
        clock.start("任务4");
        MyTools.sleep(400L);
        clock.stop();
        System.out.println("数据抓取任务全部执行结束");
        System.out.println(clock.prettyPrint());
        double seconds = clock.getTotalTimeSeconds();
        System.out.println("共耗费秒数=" + seconds);
        
        TaskInfo[] taskInfos = clock.getTaskInfo();
        List<TaskInfo> list = Arrays.asList(taskInfos);
        list.forEach(System.out::println);
    }
    
    @Test
    public void foo01(){
        StopWatchDemo.work();
    }
}
