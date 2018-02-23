package com.sauzny.sparkbaby;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        
        String line = "2017-09-22 09:45:20.625 +0800 [pool-1-thread-7] DEBUG com.sauzny.sparkbaby.demolog.Builder - {}";
        
        String[] values = line.split(" - ");
        
        System.out.println(values[0]);
        
        System.out.println(values[1]);
        
        BigDecimal b1 = new BigDecimal(Double.toString(0.48));
        BigDecimal b2 = BigDecimal.valueOf(0.27);
        
        
        System.out.println(b1.divide(b2,2, BigDecimal.ROUND_HALF_UP).toString());
    }
}
