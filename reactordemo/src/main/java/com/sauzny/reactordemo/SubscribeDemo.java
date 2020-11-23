package com.sauzny.reactordemo;

import org.reactivestreams.Subscription;
import reactor.core.Exceptions;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.util.concurrent.TimeUnit;

public class SubscribeDemo {

    public static void foo01() {
        /*
        // 订阅并触发数据流
        subscribe();

        // 订阅并指定对正常数据元素如何处理
        subscribe(Consumer<? super T> consumer);

        // 订阅并定义对正常数据元素和错误信号的处理
        subscribe(Consumer<? super T> consumer,
                  Consumer<? super Throwable> errorConsumer);

        // 订阅并定义对正常数据元素、错误信号和完成信号的处理
        subscribe(Consumer<? super T> consumer,
                  Consumer<? super Throwable> errorConsumer,
                  Runnable completeConsumer);

        // 订阅并定义对正常数据元素、错误信号和完成信号的处理，以及订阅发生时的处理逻辑
        subscribe(Consumer<? super T> consumer,
                  Consumer<? super Throwable> errorConsumer,
                  Runnable completeConsumer,
                  Consumer<? super Subscription> subscriptionConsumer);
         */

        Flux.just(1, 2, 3, 4, 5, 6).subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Completed!"));


        SampleSubscriber<Integer> ss = new SampleSubscriber<Integer>();
        System.out.println("-----");
        System.out.println("这种四参数的subscribe写法应该是不常用，此处无任何输出");
        Flux.just(1, 2, 3, 4, 5, 6).subscribe(
                i -> System.out.println(i),
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"),
                s -> ss.request(10));
        System.out.println("-----");
        System.out.println("使用 BaseSubscriber 来配置“背压”");
        // request(n) 就是这样一个方法。它能够在任何 hook 中，通过 subscription 向上游传递 背压请求。
        // 这里我们在开始这个流的时候请求1个元素值。
        Flux.just(1, 2, 3, 4, 5, 6).subscribe(ss);

        Mono.error(new Exception("some error")).subscribe(
                System.out::println,
                System.err::println,
                () -> System.out.println("Completed!")
        );
    }
}

class SampleSubscriber<T> extends BaseSubscriber<T> {

    /*
    至少重写这两个方法
     */
    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        System.out.println("SampleSubscriber start Subscribed");
        // requestUnbounded() 方法来切换到“无限”模式（等同于 request(Long.MAX_VALUE)）
        request(1);
    }
    @Override
    protected void hookOnNext(T value) {
        System.out.println("hookOnNext : " + value);
        request(1);
    }

    /*
    建议重写
     */
    @Override
    protected void hookOnComplete() {
        System.out.println("Completed!");
    }
    @Override
    protected void hookOnError(Throwable throwable) {
        System.err.println("this is error");
        throw Exceptions.errorCallbackNotImplemented(throwable);
    }
    @Override
    protected void hookOnCancel() {
        System.out.println("this is cancel");
    }
    @Override
    protected void hookFinally(SignalType type) {
        System.out.println("SampleSubscriber final and singalType : " + type);
    }
}