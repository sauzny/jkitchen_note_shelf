package com.sauzny.sparkbaby;

import org.apache.spark.sql.SparkSession;

import com.sauzny.sparkbaby.demolog.hsbd.*;

public class DemoTask {

    public static void run(){

        long a = System.currentTimeMillis();
        String master = "local[*]";

        SparkSession sparkSession = SparkSession.builder().master(master).appName("SparkBaby - " + LogTask.class.getName())
                .config("spark.some.config.option", "config-value").getOrCreate();
        
        
        //Hsbd08.run(sparkSession);
        Hsbd10.run(sparkSession);

        long b = System.currentTimeMillis();
        
        sparkSession.stop();
        
        System.out.println("执行时间：" + (b-a));
    }
    
    public static void main(String[] args) {
        DemoTask.run();
    }
}
