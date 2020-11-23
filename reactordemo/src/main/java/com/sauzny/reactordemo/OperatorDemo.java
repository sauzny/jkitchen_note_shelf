package com.sauzny.reactordemo;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class OperatorDemo {

    public static void foo01() {
        mapDemo();
        flatmapDemo();
        zipDemo();
    }

    public static void mapDemo(){
        System.out.println("map filter ====");
        Flux.range(3, 6)
                .filter(i -> i % 2 == 1)
                .map(i -> i * i)
                .subscribe(System.out::println);

    }

    public static void flatmapDemo(){

        System.out.println("flatMap ====");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Flux.just("flux", "mono")
                .flatMap(
                        s -> Flux.fromArray(s.split("\\s*"))
                                .delayElements(Duration.ofMillis(100))
                )
                //.doOnNext(System.out::print);
                .subscribe(
                        System.out::print,
                        null,
                        countDownLatch::countDown);

        Utils.countDownLatchAwait(countDownLatch);

    }

    private static Flux<String> getZipDescFlux() {
        String desc = "Zip two sources together, that is to say wait for all the sources to emit one element and combine these elements once into a Tuple2.";
        return Flux.fromArray(desc.split("\\s+"));  // 1
    }

    public static void zipDemo(){

        System.out.println("zip ====");
        CountDownLatch countDownLatch = new CountDownLatch(1);  // 2
        Flux.zip(
                getZipDescFlux(),
                // 使用Flux.interval声明一个每200ms发出一个元素的long数据流
                Flux.interval(Duration.ofMillis(200)))  // 3
                // zip之后的流中元素类型为Tuple2
                .subscribe(t -> System.out.println(t.getT1() + " " + t.getT2()), null, countDownLatch::countDown);    // 4

        // 除了zip静态方法之外，还有zipWith等非静态方法，效果与之类似：
        // getZipDescFlux().zipWith(Flux.interval(Duration.ofMillis(200)))

        Utils.countDownLatchAwait(countDownLatch, 10);   // 5
    }
}
