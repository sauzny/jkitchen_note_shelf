package com.sauzny.sparkbaby.demolog.hsbd;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.StructType;


import static org.apache.spark.sql.types.DataTypes.*;

import com.google.common.base.Joiner;

import jersey.repackaged.com.google.common.collect.Lists;

/**
 * *************************************************************************
 * @文件名称: Hsbd08.java
 *
 * @包路径  : com.sauzny.sparkbaby.demolog.hsbd 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   非对称查询
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年10月12日 - 上午10:38:11 
 *	
 **************************************************************************
 */
public class Hsbd08 {

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
        /*
        +----------+-------+
        | localdate|  sales|
        +----------+-------+
        |2011-01-31|1080.91|
        |2011-02-28| 2049.7|
        |2011-03-31| 1748.0|
        |2011-04-30|2878.04|
        |2011-05-31|1746.61|
        |2011-06-30|1505.76|
        |2011-07-31|1704.63|
        |2011-08-31|1161.15|
        |2011-09-30|2870.37|
        |2011-10-31|2917.25|
        |2011-11-30|2648.46|
        |2011-12-31|1333.15|
        |2012-01-31|2229.69|
        |2012-02-29|1034.08|
        |2012-03-31|1437.18|
        |2012-04-30|1731.68|
        |2012-05-31| 1671.6|
        |2012-06-30|1584.53|
        |2012-07-31|1206.75|
        |2012-08-31|1301.71|
        |2012-09-30|1251.89|
        |2012-10-31|1892.14|
        |2012-11-30|1880.31|
        |2012-12-31|1133.74|
        +----------+-------+ 
     */        
        df01.createOrReplaceTempView("report");
        
        List<String> param  = Arrays.asList("1,2,3","4,5,6,7,8","9,10,11,12");
        
        sparkSession.udf().register("sumByMonths", new SumByMonths());

        String sumByMonthsSql = "";
        
        List<String> sumByMonthsList = Lists.newArrayList();
        
        param.forEach(line->{
            sumByMonthsList.add("sumByMonths(localdate, sales, '"+line+"')");
        });
        
        sumByMonthsSql = Joiner.on(",").join(sumByMonthsList);
        
        String sql = "select year(localdate) as localyear,"+sumByMonthsSql+" from report GROUP BY localyear";
        
        Dataset<Row> ex01df = sparkSession.sql(sql);
        
        ex01df.show();
        
        /*
            +---------+------------------------------------+----------------------------------------+-----------------------------------------+
            |localyear|sumbymonths(localdate, sales, 1,2,3)|sumbymonths(localdate, sales, 4,5,6,7,8)|sumbymonths(localdate, sales, 9,10,11,12)|
            +---------+------------------------------------+----------------------------------------+-----------------------------------------+
            |     2012|                             4700.95|                                 7496.27|                                  6158.08|
            |     2011|                             4878.61|                                 8996.19|                                  9769.23|
            +---------+------------------------------------+----------------------------------------+-----------------------------------------+
         */
    }
    
}

class SumByMonths extends UserDefinedAggregateFunction {

    @Override
    public StructType inputSchema() {
        // TODO Auto-generated method stub
        return new StructType()
                .add("localdate", DateType)
                .add("sales", DoubleType)
                .add("months", StringType);
    }
    
    @Override
    public StructType bufferSchema() {
        return new StructType()
                .add("sum", DoubleType);
    }
    
    @Override
    public DataType dataType() {
        // TODO Auto-generated method stub
        return DoubleType;
    }
    
    @Override
    public boolean deterministic() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void initialize(MutableAggregationBuffer buffer) {
        // TODO Auto-generated method stub
        buffer.update(0, 0d);
    }

    @Override
    public void update(MutableAggregationBuffer buffer, Row input) {
        // TODO Auto-generated method stub
        Date localdate = input.getDate(0);
        double sales = input.getDouble(1);
        String months = input.getString(2);
        
        List<String> monthList = Arrays.asList(months.split(","));
        int localMonth = localdate.toLocalDate().getMonthValue();
        
        if(monthList.contains(String.valueOf(localMonth))){
            BigDecimal sum = BigDecimal.valueOf(buffer.getDouble(0)).add(BigDecimal.valueOf(sales));
            buffer.update(0, sum.doubleValue());
        }
    }

    @Override
    public void merge(MutableAggregationBuffer buffer1, Row buffer2) {
        // TODO Auto-generated method stub
        buffer1.update(0, buffer1.getDouble(0) + buffer2.getDouble(0));
    }
    
    @Override
    public Object evaluate(Row buffer) {
        // TODO Auto-generated method stub
        return buffer.getDouble(0);
    }
    
}

/*
 
-- 非对称

select year(localdate) as localyear, 
sum(CASE WHEN month(localdate) in (1,2,3) THEN sales END) '1-3', 
sum(CASE WHEN month(localdate) in (4,5,6,7,8) THEN sales END) '4-8', 
sum(CASE WHEN month(localdate) in (9,10,11,12) THEN sales END) '9-12' 
from report GROUP BY localyear

 */
