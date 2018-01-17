package com.sauzny.springboot.stopwatch;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

public class Main {

    public static void sleep(){
        sleep(100L);
    }
    
    public static void sleep(long sleep){
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void work() {
        System.out.println("数据抓取任务");
        StopWatch clock = new StopWatch("业务1");
        clock.start("任务1");
        sleep(100L);
        clock.stop();
        clock.start("任务2");
        sleep(200L);
        clock.stop();
        clock.start("任务3");
        sleep(300L);
        clock.stop();
        clock.start("任务4");
        sleep(400L);
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
        Main.work();
    }
}
