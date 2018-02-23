package com.sauzny.sparkbaby.demolog.hsbd;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.*;

import com.sauzny.sparkbaby.demolog.LogEntity;

public class Hsbd01 {

    public static void run(Dataset<String> dataset){

        // 数据转换
        Dataset<LogEntity> logDataset = dataset.flatMap(line -> {
            
            List<LogEntity> list = new ArrayList<LogEntity>();
            
            list.add(new LogEntity(line));
            
            return list.iterator();
        
        }, Encoders.bean(LogEntity.class)).cache();
        

        Dataset<LogEntity> ds01 = logDataset.where("threadName ='[pool-1-thread-13]' OR threadName ='[pool-1-thread-7]'");
    
        Dataset<Row> ds02 = ds01.cube("studentGender","studentAgeRange").agg(avg("studentBalance"));
        
        //ds01.write().saveAsTable("E:/Hsbd01.ds01.txt");
        ds02.show();
        //ds02.write().csv("E:/Hsbd01.ds01");
    }
}
