package com.sauzny.sparkbaby.demolog.hsbd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import static org.apache.spark.sql.types.DataTypes.*;

import com.sauzny.sparkbaby.demolog.LogEntity;

/**
 * *************************************************************************
 * @文件名称: Hsbd04.java
 *
 * @包路径  : com.sauzny.sparkbaby.demolog.hsbd 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   UDF和UDAF
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年9月26日 - 下午4:21:46 
 *	
 **************************************************************************
 */
public class Hsbd04 {

    /**
     * @描述: 
        UDF: User Defined Function，用户自定义的函数，函数的输入是一条具体的数据记录，实现上讲就是普通的Scala函数；
        UDAF：User Defined Aggregation Function，用户自定义的聚合函数，函数本身作用于数据集合，能够在聚合操作的基础上进行自定义操作；
                        实质上讲，例如说UDF会被Spark SQL中的Catalyst封装成为Expression，最终会通过eval方法来计算输入的数据Row（此处的Row和DataFrame中的Row没有任何关系）
     * @param sparkSession
     * @返回 void
     * @创建人  ljx 创建时间 2017年9月26日 下午4:22:58
     */
    public static void run(SparkSession sparkSession, Dataset<String> dataset){
        
        Dataset<LogEntity> logDataset = dataset.flatMap(line -> {
            
            List<LogEntity> list = new ArrayList<LogEntity>();
            
            list.add(new LogEntity(line));
            
            return list.iterator();
        
        }, Encoders.bean(LogEntity.class));
        
        
        logDataset.createOrReplaceTempView("myLog");
        
        // 注册自定义函数
        
        // 在目前版本 2.2.0 中 UDF函数最多可以接受22个输入参数
        sparkSession.udf().register("nameLength", (String name) -> name.length(), IntegerType);
        sparkSession.udf().register("MyUDAF", new MyUDAF());
        
        Dataset<Row> df01 = sparkSession.sql("SELECT nameLength(studentName) as nameLength_int, count(studentName) FROM myLog group by nameLength_int");
        
        df01.show();
        
        Dataset<Row> df02 = sparkSession.sql("SELECT studentAgeRange, avg(studentBalance), MyUDAF(studentBalance) FROM myLog group by studentAgeRange");
     
        df02.show();
    }
}

/**
 * @类描述: 平均数
 * 
 * 一定要理解这个八个函数！！！
 * 
 */
class  MyUDAF extends UserDefinedAggregateFunction {

    // 该方法指定具体输入数据的类型 
    @Override
    public StructType inputSchema() {
        // TODO Auto-generated method stub
        return new StructType().add("myinput", DoubleType);
    }
    
    // 在进行聚合操作的时候所要处理的数据的结果的类型
    @Override
    public StructType bufferSchema() {
        // TODO Auto-generated method stub
        return new StructType().add("mycnt", LongType).add("mysum", DoubleType);
    }
    
    //指定UDAF函数计算后返回的结果类型
    @Override
    public DataType dataType() {
        // TODO Auto-generated method stub
        return StringType;
    }

    // 确保一致性 一般用true
    @Override
    public boolean deterministic() {
        // TODO Auto-generated method stub
        return true;
    }

    // 在Aggregate之前每组数据的初始化结果
    @Override
    public void initialize(MutableAggregationBuffer buffer) {
        // TODO Auto-generated method stub
        buffer.update(0, 0l);
        buffer.update(1, 0d);
    }

    // 在进行聚合的时候，每当有新的值进来，对分组后的聚合如何进行计算  
    // 本地的聚合操作，相当于Hadoop MapReduce模型中的Combiner
    @Override
    public void update(MutableAggregationBuffer buffer1, Row buffer2) {
        // TODO Auto-generated method stub
        
        buffer1.update(0, buffer1.getLong(0) + 1L);
        buffer1.update(1, buffer1.getDouble(1) + buffer2.getDouble(0));
    }  

    // 最后在分布式节点进行Local Reduce完成后需要进行全局级别的Merge操作
    @Override
    public void merge(MutableAggregationBuffer buffer1, Row buffer2) {
        // TODO Auto-generated method stub
        
        buffer1.update(0, buffer1.getLong(0) + buffer2.getLong(0));
        buffer1.update(1, buffer1.getDouble(1) + buffer2.getDouble(1));
        
    }

    // 返回UDAF最后的计算结果
    @Override
    public Object evaluate(Row buffer) {
        // TODO Auto-generated method stub
        
        BigDecimal b1 = BigDecimal.valueOf(buffer.getDouble(1));
        BigDecimal b2 = BigDecimal.valueOf(buffer.getLong(0));
        
        //System.out.println(b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).toString());
        
        return b1.divide(b2,2, BigDecimal.ROUND_HALF_UP).toString();
        
    }
    
  }  
