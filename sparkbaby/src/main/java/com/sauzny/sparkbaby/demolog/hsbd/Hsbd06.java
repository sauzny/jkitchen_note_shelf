package com.sauzny.sparkbaby.demolog.hsbd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import com.sauzny.sparkbaby.demolog.LogEntity;

/**
 * *************************************************************************
 * @文件名称: Hsbd06.java
 *
 * @包路径  : com.sauzny.sparkbaby.demolog.hsbd 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   spark streaming kafka
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年10月11日 - 上午11:17:25 
 *	
 **************************************************************************
 */
public class Hsbd06 {

    public void run(SparkSession sparkSession){

        String topics = "topic1";
        Collection<String> topicsSet = new HashSet<>(Arrays.asList(topics.split(",")));
        
        //kafka相关参数，必要！缺了会报错
        
        String brokers = "master2:6667";
        
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("metadata.broker.list", brokers) ;
        kafkaParams.put("bootstrap.servers", brokers);
        kafkaParams.put("group.id", "group1");
        kafkaParams.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaParams.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaParams.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        
        //Topic分区
        Map<TopicPartition, Long> offsets = new HashMap<>();
        offsets.put(new TopicPartition("topic1", 0), 2L);
        
        JavaStreamingContext jssc = new JavaStreamingContext(sparkSession.sparkContext().conf(), new Duration(2000));
        
        // 从kafka中获取数据
        JavaInputDStream<ConsumerRecord<Object, Object>> lines = KafkaUtils.createDirectStream(
                jssc, 
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topicsSet, kafkaParams, offsets)
                );

        /**
                            以下数据转换只是举例， 目的是为了说明转换过程，DStream -> Dataset（RDD）
         
                             转换过程或者结果的具体数据类型，也可以是Row（再自行设置schema即可）
         */
        
        // 数据类型转换
        JavaDStream<LogEntity> jdsLogEntity = lines.flatMap(line -> {

            List<LogEntity> list = new ArrayList<LogEntity>();

            list.add(new LogEntity(line.value().toString()));

            return list.iterator();
        });

        // 转换成 Dataset（RDD）处理数据
        jdsLogEntity.foreachRDD((rdd, time) -> {
            
            //rdd.foreach(record -> System.out.println(record) );
            
            SparkSession spark = SparkSession.builder().config(rdd.context().getConf()).getOrCreate();
            
            Dataset<LogEntity> ds = spark.createDataset(rdd.rdd(), Encoders.bean(LogEntity.class));
            
            ds.show();
            
            ds.createGlobalTempView("log");
            
            spark.sql("select * from log where xxx");
        });
        
        // 启动   -- 开始计算
        jssc.start();
        
        
        // 等待流计算完成（手动或由于任何错误），来防止应用退出
        try {
            jssc.awaitTermination();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        // 停止
        //jssc.stop();
    }
}
