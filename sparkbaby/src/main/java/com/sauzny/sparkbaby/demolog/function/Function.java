package com.sauzny.sparkbaby.demolog.function;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.DataFrameWriter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sauzny.sparkbaby.demolog.LogEntity;

import scala.Tuple2;

/**
 * *************************************************************************
 * @文件名称: Function.java
 *
 * @包路径  : com.sauzny.sparkbaby.demolog.function 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   Function demo
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年9月20日 - 上午10:08:28 
 *	
 **************************************************************************
 */
public class Function {
    
    private static final Logger logger = LoggerFactory.getLogger(Function.class);

    public static void run(Dataset<LogEntity> dataset){

        /* Returns a new Dataset where each record has been mapped on to the specified type. */
        /* 没用明白... */
        // dataset.as
        
        
        /* 输出对象，后面可以继续 jdbc text等方法 */
        /* Interface for saving the content of the non-streaming Dataset out into external storage. */
        DataFrameWriter<LogEntity> write = dataset.write();
        
        /* Interface for saving the content of the streaming Dataset out into external storage. */
        //dataset.writeStream();
        
        /* 创建一个全局视图，这个视图可以被 sparksession.sql使用，sparksession 是可以创建多个的  */
        dataset.createOrReplaceTempView("myLog");
        //sparkSession.sql("SELECT * FROM global_temp.myLog").show();
        
        /* 一些转换  */
        JavaRDD<LogEntity> javaRdd = dataset.toJavaRDD();
        RDD<LogEntity> rdd = dataset.rdd();
        Dataset<Row> df = dataset.toDF();
        // Exception in thread "main" java.lang.IllegalArgumentException: requirement failed: The number of columns doesn't match.
        dataset.toDF("date", "level", "localTime", "message", "studentAge", "studentBalance", "studentEmail", "studentGender", "threadName").show();
        
        /* 缓存数据 */
        dataset.cache();
        
        /* 所有字段 */
        String[] columns = dataset.columns();
        
        /* 所有字段及其类型 */
        Tuple2<String, String>[] dtypes = dataset.dtypes();
        
        /* Prints the physical plan to the console for debugging purposes. */
        /* 把计划信息打印到控制台，就像mysql里的explain */
        dataset.explain(true);
        
        /* Returns true if the collect and take methods can be run locally (without any Spark executors). */
        boolean isLocal = dataset.isLocal();
        
        /* Returns true if this Dataset contains one or more sources that continuously return data as it arrives. */
        boolean isStreaming = dataset.isStreaming();
        
        /* Persist this Dataset with the default storage level (MEMORY_AND_DISK). */
        /* 使用默认存储级别存储数据，默认是 内存和硬盘 */
        dataset.persist();
        
        /* 获取schema */
        StructType schema = dataset.schema();
        
        /* 把schema打印在控制台 */
        dataset.printSchema();
        
        /* 将数据集标记为非持久性，并从内存和磁盘中删除所有数据块。 */
        dataset.unpersist(true);
        /* 默认是 false */
        dataset.unpersist();
        
        /* Returns a best-effort snapshot of the files that compose this Dataset. */
        /* 不明白... */
        String[] inputFiles = dataset.inputFiles();

        for(String column : columns){
            logger.info("dataset.columns() = {}", column);
        }
        
        for(Tuple2<String, String> dtype : dtypes){
            logger.info("dataset.dtypes() = {} and {}", dtype._1, dtype._2);
        }
        
        for(String inputFile : inputFiles){
            logger.info("dataset.inputFiles() = {}", inputFile);
        }
        
        logger.info("dataset.isLocal() = {}", isLocal);
        logger.info("dataset.isStreaming() = {}", isStreaming);
        logger.info("dataset.schema() = {}", schema);
    }

}
