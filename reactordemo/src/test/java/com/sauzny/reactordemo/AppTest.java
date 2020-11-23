package com.sauzny.reactordemo;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

/**
 * Unit test for simple App.
 */
public class AppTest {
    private Flux<Integer> generateFluxFrom1To6() {
        return Flux.just(1, 2, 3, 4, 5, 6);
    }

    private Mono<Integer> generateMonoWithError() {
        return Mono.error(new Exception("some error"));
    }

    @Test
    public void testViaStepVerifier() {
        /*
        expectNext用于测试下一个期望的数据元素，
        expectErrorMessage用于校验下一个元素是否为错误信号，
        expectComplete用于测试下一个元素是否为完成信号。
         */
        StepVerifier.create(generateFluxFrom1To6())
                .expectNext(1, 2, 3, 4, 5, 6)
                .expectComplete()
                .verify();

        StepVerifier.create(generateMonoWithError())
                .expectErrorMessage("some error")
                .verify();
    }

}
