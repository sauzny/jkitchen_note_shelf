package com.sauzny.sparkbaby.demolog.hsbd;

import java.util.Arrays;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import scala.Tuple2;

/**
 * *************************************************************************
 * @文件名称: Hsbd07.java
 *
 * @包路径  : com.sauzny.sparkbaby.demolog.hsbd 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   spark streaming basic
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年10月11日 - 下午3:47:31 
 *	
 **************************************************************************
 */
public class Hsbd07 {

    public void runWithSocket(SparkSession sparkSession) throws InterruptedException{
        
        JavaStreamingContext jssc = new JavaStreamingContext(sparkSession.sparkContext().conf(), new Duration(1000));
    
        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("localhost", 9999);
        
        JavaDStream<String> words = lines.flatMap(x -> Arrays.asList(x.split(" ")).iterator());
        
        JavaPairDStream<String, Integer> pairs = words.mapToPair(s -> new Tuple2<>(s, 1));
        JavaPairDStream<String, Integer> wordCounts = pairs.reduceByKey((i1, i2) -> i1 + i2);
        
        wordCounts.print();
        
        jssc.start();              // Start the computation
        jssc.awaitTermination();   // Wait for the computation to terminate
    }
    
    public void runWithFile(SparkSession sparkSession){
        
    }
}
