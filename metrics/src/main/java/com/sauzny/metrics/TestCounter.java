package com.sauzny.metrics;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/*
 * Counter是Gauge的一个特例，维护一个计数器，可以通过inc()和dec()方法对计数器做修改。
 * 使用步骤与Gauge基本类似，在MetricRegistry中提供了静态方法可以直接实例化一个Counter。
 * 
 * 
 */

public class TestCounter {

    /**
     * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
     */
    private static final MetricRegistry metrics = new MetricRegistry();

    /**
     * 在控制台上打印输出
     */
    private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

    /**
     * 实例化一个counter,同样可以通过如下方式进行实例化再注册进去
     * pendingJobs = new Counter();
     * metrics.register(MetricRegistry.name(TestCounter.class, "pending-jobs"), pendingJobs);
     */
    private static Counter pendingJobs = metrics.counter(MetricRegistry.name(TestCounter.class, "pedding-jobs"));
//    private static Counter pendingJobs = metrics.counter(MetricRegistry.name(TestCounter.class, "pedding-jobs"));



    private static Queue<String> queue = new LinkedList<String>();

    public static void add(String str) {
        pendingJobs.inc();
        queue.offer(str);
    }

    public String take() {
        pendingJobs.dec();
        return queue.poll();
    }

    public static void main(String[]args) throws InterruptedException {
        reporter.start(3, TimeUnit.SECONDS);
        while(true){
            add("1");
            Thread.sleep(1000);
        }

    }
}
