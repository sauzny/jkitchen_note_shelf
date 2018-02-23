package com.sauzny.sparkbaby;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StringType;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sauzny.sparkbaby.demolog.LogEntity;
import com.sauzny.sparkbaby.demolog.actions.Actions;
import com.sauzny.sparkbaby.demolog.function.Function;
import com.sauzny.sparkbaby.demolog.hsbd.*;
import com.sauzny.sparkbaby.demolog.transformations.Transformations;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LogTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogTask.class);

    public static void main(String[] args) {
        //checkArgument(args.length > 0, "Please provide the path of input file as first parameter.");
        //new LogTask().run(args[0]);
        new LogTask().run("E:/data/log/sparkbaby/demo.2017-09-22.0.log");
    }

    /**
     * The task body
     */
    public void run(String inputFilePath) {


        String master = "local[*]";

        /*
         * Initialises a SparkSession
         */

        SparkSession sparkSession = SparkSession.builder().master(master).appName("SparkBaby - " + LogTask.class.getName())
                .config("spark.some.config.option", "config-value").getOrCreate();

        //JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());
        
        Dataset<String> dataset = sparkSession.read().textFile(inputFilePath);
        
        /**
         * 关于这两个函数
         * flatMap
         * map
         * 猜着写的，写完测试一下，好使……
         */
        
        // 这里只截取了前100条数据
        /*
        Dataset<LogEntity> logDataset = dataset.flatMap(line -> {
            
            List<LogEntity> list = new ArrayList<LogEntity>();
            
            list.add(new LogEntity(line));
            
            return list.iterator();
        
        }, Encoders.bean(LogEntity.class)).limit(100).cache();
        */
        /*
        Dataset<LogEntity> logDataset = dataset.mapPartitions(lines -> {
            
            List<LogEntity> list = new ArrayList<LogEntity>();
            
            while(lines.hasNext()){
                list.add(new LogEntity(lines.next()));
            }
            
            return list.iterator();
        
        }, Encoders.bean(LogEntity.class)).cache();
        */
        
        //Dataset<LogEntity> logDataset = dataset.map(line -> new LogEntity(line), Encoders.bean(LogEntity.class)).cache();
        
        // testing api demo 
        //Actions.run(logDataset);
        //Function.run(logDataset);
        //Transformations.run(logDataset);

        long a = System.currentTimeMillis();
        //
        //Hsbd01.run(dataset);
        //Hsbd02.run(sparkSession, dataset);
        //Hsbd03.run(sparkSession);
        Hsbd04.run(sparkSession, dataset);
        //Hsbd05.run(sparkSession);

        long b = System.currentTimeMillis();
        
        LOGGER.info("log task end");

        sparkSession.stop();
        
        System.out.println("执行时间：" + (b-a));
        
        while(true){
            
        }

    }
}
