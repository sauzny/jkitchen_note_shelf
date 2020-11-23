package com.sauzny.reactordemo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public class CreateDemo {
    public static void foo01() {

        Flux.just(1, 2, 3, 4, 5, 6);
        Mono.just(1);

        // 数组 集合 stream 或者 范围
        /*
        Integer[] array = new Integer[]{1,2,3,4,5,6};
        Flux.fromArray(array);

        List<Integer> list = Arrays.asList(array);
        Flux.fromIterable(list);

        Stream<Integer> stream = list.stream();
        Flux.fromStream(stream);

        Flux.range(5, 3);
         */

        // 只有完成信号的空数据流
        Flux.just();
        Flux.empty();
        Mono.empty();
        Mono.justOrEmpty(Optional.empty());

        // 只有错误信号的数据流
        Flux.error(new Exception("some error"));
        Mono.error(new Exception("some error"));

        Flux.just(1, 2, 3, 4, 5, 6).subscribe(System.out::print);
        System.out.println();
        Mono.just(1).subscribe(System.out::println);
    }
}
