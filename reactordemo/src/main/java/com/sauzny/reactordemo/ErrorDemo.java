package com.sauzny.reactordemo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

public class ErrorDemo {

    /**
     * 常见的包括如下几种：
     * <p>
     * 捕获并返回一个静态的缺省值。
     * 捕获并执行一个异常处理方法或动态计算一个候补值来顶替。
     * 捕获，并再包装为某一个 业务相关的异常，然后再抛出业务异常。
     * 捕获，记录错误日志，然后继续抛出。
     * 使用 finally 来清理资源，或使用 Java 7 引入的 “try-with-resource”。
     */

    public static void foo01() {
        error1();
        error2();
        error3();
        error4();
        error5();
        retryDemo();
    }

    private static Flux<Integer> callExternalService(String s) {
        return Flux.just(Integer.parseInt(s));
    }

    private static Flux<Integer> getFromCache(Throwable t) {
        System.out.println(t.getMessage());
        return Flux.just(0);
    }

    public static void error1() {
        System.out.println("error1 捕获并返回一个静态的缺省值 =====");
        Flux.range(1, 6)
                .map(i -> 10 / (i - 3))
                .onErrorReturn(0)   // 1
                .map(i -> i * i)
                .subscribe(System.out::println, System.err::println);
    }

    public static void error2() {
        System.out.println("error2 捕获并执行一个异常处理方法或计算一个候补值来顶替 =====");

        Flux.range(1, 6)
                .map(i -> 10 / (i - 3))
                .onErrorResume(e -> Mono.just(new Random().nextInt(6))) // 提供新的数据流
                .map(i -> i * i)
                .subscribe(System.out::println, System.err::println);
    }

    public static void error3() {
        System.out.println("error3 捕获，并再包装为某一个业务相关的异常，然后再抛出业务异常 =====");
        Flux.just("timeout1")
                .flatMap(k -> callExternalService(k))   // 1
                .onErrorMap(original -> new BusinessException("SLA exceeded", original)); // 2

        Flux.just("timeout1")
                .flatMap(k -> callExternalService(k))
                .onErrorResume(original -> Flux.error(
                        new BusinessException("SLA exceeded", original))
                );
    }

    public static void error4() {
        System.out.println("error4 捕获，记录错误日志，然后继续抛出 =====");

        Flux.just("endpoint1", "endpoint2")
                .flatMap(k -> callExternalService(k))
                .doOnError(e -> {   // 1
                    System.err.println("uh oh, falling back, service failed for key " + e);    // 2
                })
                .onErrorResume(e -> getFromCache(e));
    }

    public static void error5() {
        System.out.println("error5 捕获并执行一个异常处理方法或计算一个候补值来顶替 =====");
        /*
        Flux.using(
                () -> getResource(),    // 1
                resource -> Flux.just(resource.getAll()),   // 2
                MyResource::clean   // 3
        );
        */

        LongAdder statsCancel = new LongAdder();    // 1

        Flux<String> flux =
                Flux.just("foo", "bar")
                        .doFinally(type -> {
                            if (type == SignalType.CANCEL)  // 2
                                statsCancel.increment();  // 3
                        })
                        .take(1);   // 4
    }

    public static void retryDemo(){
        Flux.range(1, 6)
                .map(i -> 10 / (3 - i))
                .retry(1)
                .subscribe(System.out::println, System.err::println);
        try {
            Thread.sleep(100);  // 确保序列执行完
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class BusinessException extends Exception {

    public BusinessException(String message, Throwable t) {
        super(message, t);
    }
}
