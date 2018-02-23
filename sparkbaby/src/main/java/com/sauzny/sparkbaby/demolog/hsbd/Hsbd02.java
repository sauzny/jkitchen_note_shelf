package com.sauzny.sparkbaby.demolog.hsbd;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 * *************************************************************************
 * @文件名称: Hsbd02.java
 *
 * @包路径  : com.sauzny.sparkbaby.demolog.hsbd 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   使用schema方式创建df，另有文档记录这次代码的编写过程
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年9月25日 - 下午4:25:25 
 *	
 **************************************************************************
 */
public class Hsbd02 {

    public static void run(SparkSession sparkSession, Dataset<String> dataset){
        
        JavaRDD<Row> rdd =  dataset.toJavaRDD().map(line -> {
            String[] strs = line.split(" - ");
            return RowFactory.create(strs[0], strs[1]);
        });
        
        
        // 注意使用工具类DataTypes
        
        StructType schema = DataTypes.createStructType(new StructField[]{
                DataTypes.createStructField("a2", DataTypes.StringType, true),
                DataTypes.createStructField("b2", DataTypes.StringType, true)
                });
        
        
        Dataset<Row> df02 = sparkSession.createDataFrame(rdd, schema);
        
        df02.show();
    }
}
