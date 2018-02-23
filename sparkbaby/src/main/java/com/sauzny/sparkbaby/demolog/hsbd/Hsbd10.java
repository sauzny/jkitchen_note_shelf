package com.sauzny.sparkbaby.demolog.hsbd;

import static org.apache.spark.sql.types.DataTypes.*;

import java.sql.Date;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

/**
 * *************************************************************************
 * @文件名称: Hsbd09.java
 *
 * @包路径  : com.sauzny.sparkbaby.demolog.hsbd 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   半年占比
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年10月12日 - 上午10:39:58 
 *	
 **************************************************************************
 */
public class Hsbd10 {
    
    public static void run(SparkSession sparkSession){
        
        Dataset<Row> df01 = sparkSession.read().format("jdbc").options(
                new HashMap<String, String>(){{
                    put("url", "jdbc:mysql://192.168.73.128:3306");
                    put("dbtable", "sparkdemo.report");
                    put("driver", "com.mysql.cj.jdbc.Driver");
                    put("user", "root");
                    put("password", "root");
                    put("fetchsize", "3");
        }}).load();
        
        //df01.show();
        
        df01.createOrReplaceTempView("report");
        
        List<String> param  = Arrays.asList(
                "1,2,3,4,5,6",
                "7,8,9,10,11,12"
                );
        
        //String asB64 = Base64.getEncoder().encodeToString("some string".getBytes("utf-8"));
        

        sparkSession.udf().register("setFlagByLocaldate", (Date localdate) -> {
            
            String result = "--";
            
            int month = localdate.toLocalDate().getMonthValue();
            
            for(String line : param){
                List<String> list = Arrays.asList(line.split(","));
                if(list.contains(String.valueOf(month))){
                    result = Base64.getEncoder().encodeToString(line.getBytes("utf-8"));
                    break;
                }
            }
            
            return result;
            
        }, StringType);

        String sql1 = "select localdate, year(localdate) localyear, setFlagByLocaldate(localdate) flag, sales from report";  
        String sql2 = "select year(localdate) localyear, setFlagByLocaldate(localdate) flag, sum(sales) tsales from report GROUP BY year(localdate), flag";
        
        
        String rownumName = RandomStringUtils.randomAlphabetic(6) + "_" + System.currentTimeMillis();

        String sql22 = "select * from (SELECT ROW_NUMBER() OVER(ORDER BY year(localdate)) as "+rownumName+", year(localdate) localyear, setFlagByLocaldate(localdate) flag, sum(sales) tsales from report GROUP BY year(localdate), setFlagByLocaldate(localdate)) t where "+rownumName+" BETWEEN 2 and 4";
        

        List<Row> row2 = sparkSession.sql(sql2).collectAsList();
        List<Row> row22 = sparkSession.sql(sql22).collectAsList();

        System.out.println(row2);
        System.out.println("------------------------------------------");
        System.out.println(row22);
    }
}