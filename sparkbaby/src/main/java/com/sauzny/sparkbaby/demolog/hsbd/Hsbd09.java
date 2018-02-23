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
public class Hsbd09 {
    
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
        
        /*

        String sql22 = "select * from (SELECT ROW_NUMBER() OVER(ORDER BY year(localdate)) as "+rownumName+", year(localdate) localyear, setFlagByLocaldate(localdate) flag, sum(sales) tsales from report GROUP BY year(localdate), setFlagByLocaldate(localdate)) t where "+rownumName+" BETWEEN 2 and 4";
        

        List<Row> row2 = sparkSession.sql(sql2).collectAsList();
        List<Row> row22 = sparkSession.sql(sql22).collectAsList();

        System.out.println(row2);
        System.out.println("------------------------------------------");
        System.out.println(row22);
        */
        /*
        Dataset<Row> t1 = sparkSession.sql(sql1);
        Dataset<Row> t2 = sparkSession.sql(sql2);
        
        t1.createOrReplaceTempView("t1");
        t2.createOrReplaceTempView("t2");
        */
        //String sql = "SELECT localdate,sales,tsales,sales/tsales*100 zhanbi from t1 JOIN t2 on t1.flag = t2.flag order by localdate";
        
        //String rownumName = RandomStringUtils.randomAlphabetic(6) + "_" + System.currentTimeMillis();
        
        String sql = "select * from (SELECT ROW_NUMBER() OVER(ORDER BY localdate) as "+rownumName+", localdate,sales,tsales,sales/tsales*100 zhanbi from ("+sql1+") t1 JOIN ("+sql2+") t2 on t1.localyear = t2.localyear and t1.flag = t2.flag order by localdate) t where "+rownumName+" BETWEEN 10 and 15";
        
        //String sql = "select row_number() id, localdate,sales,tsales,sales/tsales*100 zhanbi from ("+sql1+") t1 JOIN ("+sql2+") t2 on t1.localyear = t2.localyear and t1.flag = t2.flag order by localdate";

        Dataset<Row> result = sparkSession.sql(sql);
        
        //System.out.println(result.schema());
        
        result.drop(rownumName).show(100);
        
        /*
            +----------+-------+--------+------------------+
            | localdate|  sales|  tsales|            zhanbi|
            +----------+-------+--------+------------------+
            |2011-01-31|1080.91|11009.02| 9.818403454621755|
            |2011-02-28| 2049.7|11009.02|18.618369300809697|
            |2011-03-31| 1748.0|11009.02|15.877889221747257|
            |2011-04-30|2878.04|11009.02|26.142563098259426|
            |2011-05-31|1746.61|11009.02|15.865263211439345|
            |2011-06-30|1505.76|11009.02|13.677511713122511|
            |2011-07-31|1704.63|12635.01|13.491322919412015|
            |2011-08-31|1161.15|12635.01| 9.189941282199223|
            |2011-09-30|2870.37|12635.01|22.717591834118057|
            |2011-10-31|2917.25|12635.01| 23.08862438573456|
            |2011-11-30|2648.46|12635.01|20.961281391941913|
            |2011-12-31|1333.15|12635.01|10.551238186594233|
            |2012-01-31|2229.69| 9688.76|23.013161642975984|
            |2012-02-29|1034.08| 9688.76| 10.67298601678646|
            |2012-03-31|1437.18| 9688.76| 14.83347714258584|
            |2012-04-30|1731.68| 9688.76|17.873081797877127|
            |2012-05-31| 1671.6| 9688.76| 17.25298180572127|
            |2012-06-30|1584.53| 9688.76|16.354311594053314|
            |2012-07-31|1206.75| 8666.54|13.924241969690323|
            |2012-08-31|1301.71| 8666.54|15.019950291581182|
            |2012-09-30|1251.89| 8666.54|14.445095736014604|
            |2012-10-31|1892.14| 8666.54| 21.83270370874651|
            |2012-11-30|1880.31| 8666.54| 21.69620171371735|
            |2012-12-31|1133.74| 8666.54|13.081806580250019|
            +----------+-------+--------+------------------+ 
         */
        
    }
}

/*

-- 半年占比
SELECT
    localdate,
    sales,
    tsales,
    sales / tsales * 100 zhanbi
FROM
    (
        SELECT
            localdate,
            YEAR (localdate) localyear,
            (
                CASE
                WHEN MONTH (localdate) IN (1, 2, 3, 4, 5, 6) THEN
                    'u'
                WHEN MONTH (localdate) IN (7, 8, 9, 10, 11, 12) THEN
                    'd'
                ELSE
                    ''
                END
            ) ban,
            sales
        FROM
            report
    ) t1
JOIN (
    SELECT
        YEAR (localdate) localyear,
        (
            CASE
            WHEN MONTH (localdate) IN (1, 2, 3, 4, 5, 6) THEN
                'u'
            WHEN MONTH (localdate) IN (7, 8, 9, 10, 11, 12) THEN
                'd'
            ELSE
                ''
            END
        ) ban,
        sum(sales) tsales
    FROM
        report
    GROUP BY
        YEAR (localdate),
        ban
) t2 ON t1.localyear = t2.localyear
AND t1.ban = t2.ban
 */
