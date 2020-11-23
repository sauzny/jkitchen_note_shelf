package com.sauzny.reactordemo;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

public class SchedulersDemo {

    /*
    当前线程（Schedulers.immediate()）；
    可重用的单线程（Schedulers.single()）。注意，这个方法对所有调用者都提供同一个线程来使用， 直到该调度器被废弃。如果你想使用独占的线程，请使用Schedulers.newSingle()；
    弹性线程池（Schedulers.elastic()）。它根据需要创建一个线程池，重用空闲线程。线程池如果空闲时间过长 （默认为 60s）就会被废弃。对于 I/O 阻塞的场景比较适用。Schedulers.elastic()能够方便地给一个阻塞 的任务分配它自己的线程，从而不会妨碍其他任务和资源；
    固定大小线程池（Schedulers.parallel()），所创建线程池的大小与CPU个数等同；
    自定义线程池（Schedulers.fromExecutorService(ExecutorService)）基于自定义的ExecutorService创建 Scheduler（虽然不太建议，不过你也可以使用Executor来创建）。
     */

    public static void foo01() {
        publishOnDemo();
        subscribeOnDemo();
        bothDemo();
    }

    public static void publishOnDemo() {

        System.out.println("publishOnDemo =====");

        Repository repository = new Repository();
        Flux.just("Alice", "Bob")
                .publishOn(Schedulers.elastic())
                .doOnNext(repository::save)
                .then();
    }

    public static void subscribeOnDemo() {

        System.out.println("subscribeOnDemo =====");

        Repository repository = new Repository();
        Flux.defer(() -> Flux.fromIterable(repository.findAll()))
                .subscribeOn(Schedulers.elastic());
    }

    public static void bothDemo() {

        System.out.println("bothDemo 测试出两个函数的作用范围 =====");

        Flux.just("tom")
                .map(s -> {
                    System.out.println("[map] Thread name: " + Thread.currentThread().getName());
                    return s.concat("@mail.com");
                })
                .publishOn(Schedulers.newElastic("thread-publishOn"))
                .filter(s -> {
                    System.out.println("[filter] Thread name: " + Thread.currentThread().getName());
                    return s.startsWith("t");
                })
                .subscribeOn(Schedulers.newElastic("thread-subscribeOn"))
                .subscribe(s -> {
                    System.out.println("[subscribe] Thread name: " + Thread.currentThread().getName());
                    System.out.println(s);
                });
    }
}

class Repository {

    public void save(String user){}

    public List<String> findAll(){
        return new ArrayList<String>();
    }
}