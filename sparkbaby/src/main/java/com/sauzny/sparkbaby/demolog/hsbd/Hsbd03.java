package com.sauzny.sparkbaby.demolog.hsbd;

import java.util.HashMap;
import java.util.Properties;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 * *************************************************************************
 * @文件名称: Hsbd03.java
 *
 * @包路径  : com.sauzny.sparkbaby.demolog.hsbd 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   spark jdbc 读写
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年9月26日 - 下午4:22:03 
 *	
 **************************************************************************
 */
public class Hsbd03 {

    public static void run(SparkSession sparkSession){
        
        // 注意表名的写法，库名.表名
        
        Dataset<Row> df01 = sparkSession.read().format("jdbc").options(
                new HashMap<String, String>(){{
                    put("url", "jdbc:mysql://192.168.73.128:3306");
                    put("dbtable", "(SELECT a.id aid, b.id bid from kula.ssq a JOIN kula.ssq b ON a.id = b.id) as t1");
                    put("driver", "com.mysql.cj.jdbc.Driver");
                    put("user", "root");
                    put("password", "root");
                    put("fetchsize", "3");
        }}).load();
        
        df01.show();

        
        // 另一种写法
        
        Properties properties = new Properties();
        properties.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        
        String url = "jdbc:mysql://192.168.73.128:3306/kula?user=root&password=root&useUnicode=true&characterEncoding=utf8&useSSL=false";
        
        String table = "ssq";
        
        Dataset<Row> df02 = sparkSession.read().jdbc(url, table, properties);
        
        df02.show();
        
        // 另一种写法
        
        Properties properties3 = new Properties();
        properties3.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        properties3.setProperty("user", "root");
        properties3.setProperty("password", "root");
        
        
        String url3 = "jdbc:mysql://192.168.73.128:3306";
        String table3 = "kula.ssq";
        
        Dataset<Row> df03 = sparkSession.read().jdbc(url3, table3, properties3);
        
        df03.show();
        
        
        df03.limit(100).write().mode(SaveMode.Append).jdbc(url3, "kula.ssq1", properties3);
    }
}
