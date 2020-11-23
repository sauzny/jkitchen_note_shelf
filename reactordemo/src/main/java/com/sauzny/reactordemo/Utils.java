package com.sauzny.reactordemo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static void sleep(long seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void countDownLatchAwait(CountDownLatch countDownLatch){

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void countDownLatchAwait(CountDownLatch countDownLatch, int seconds){

        try {
            countDownLatch.await(seconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
