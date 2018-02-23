package com.sauzny.sparkbaby.demolog.actions;

import java.util.Iterator;

import org.apache.spark.sql.Dataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sauzny.sparkbaby.demolog.LogEntity;

/**
 * *************************************************************************
 * @文件名称: Actions.java
 *
 * @包路径  : com.sauzny.sparkbaby.demolog.actions 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   Actions demo
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年9月20日 - 上午10:08:11 
 *	
 **************************************************************************
 */
public class Actions {
    
    private static final Logger logger = LoggerFactory.getLogger(Actions.class);

    private static LogEntity reduceDemoFunction(LogEntity x, LogEntity y){
        // x 和  y 做一些逻辑处理
        return x;
    }
    
    public static void run(Dataset<LogEntity> dataset){
        /* 转换为 list */
        dataset.collectAsList().forEach(log -> System.out.println(log));
        
        /* 转换为 iterator */
        Iterator<LogEntity> iterator = dataset.toLocalIterator();
        
        /* 总数 */
        long count = dataset.count();
        
        /* top 1 */
        LogEntity first = dataset.first();
        
        /* top 1 */
        LogEntity head = dataset.head();
        
        /* top n */
        // 如果是 scala 代码的话，此处可直接继续使用 foreach遍历，java代码，则需要强制转换
        Object headObject = dataset.head(2);
        LogEntity[] head_2s = (LogEntity[]) headObject;
        
        // 第一次 使用 reduce， 文档中有一个图解，说明reduce
        dataset.reduce((x,y) -> reduceDemoFunction(x, y));
        
        // Applies a function f to all elements of this RDD.
        // foreach用于遍历RDD,将函数f应用于每一个元素。
        dataset.foreach(log -> System.out.println(log));
        
        // Applies a function f to each partition of this RDD.
        // foreachPartition和foreach类似，只不过是对每一个分区使用f。
        dataset.foreachPartition(log -> System.out.println(log));
        
        // 打印全部
        dataset.show();
        
        // 打印top2
        dataset.show(2);
        // 打印top20
        dataset.show(true);
        
        // 直方图
        dataset.describe("threadName","level").show();
        
        /*
        +-------+------------------+------+
        |summary|        threadName| level|
        +-------+------------------+------+
        |  count|            500000|500000|
        |   mean|              null|  null|
        | stddev|              null|  null|
        |    min|[pool-1-thread-10]| DEBUG|
        |    max| [pool-1-thread-9]|  WARN|
        +-------+------------------+------+
         */
        
        logger.info("dataset.count() = {}", count);
        logger.info("dataset.first() = {}", first);
        logger.info("dataset.head() = {}", head);
        logger.info("dataset.head(2) = {}", headObject);
        
        for(LogEntity logEntity : head_2s){
            logger.info("head_2s = {}", logEntity);
        }
    }
}
