package com.sauzny.sparkbaby.demolog.hsbd;

import static org.apache.spark.sql.types.DataTypes.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.spark_project.guava.collect.Lists;

/**
 * *************************************************************************
 * @文件名称: Hsbd05.java
 *
 * @包路径  : com.sauzny.sparkbaby.demolog.hsbd 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   使用UDAF实现一个 年度 同比计算
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年10月10日 - 上午10:06:14 
 *	
 **************************************************************************
 */
public class Hsbd05 {

public static void run(SparkSession sparkSession){
        
        StructType schema = DataTypes.createStructType(new StructField[]{
            DataTypes.createStructField("name", StringType, true),
            DataTypes.createStructField("sales", DoubleType, true),
            DataTypes.createStructField("state", StringType, true),
            DataTypes.createStructField("saleDate", DateType, true)
            });
    
        List<Row> rows = Lists.newArrayList();
        rows.add(RowFactory.create("Widget Co", 1000.00, "AZ", Date.valueOf("2014-01-01")));
        rows.add(RowFactory.create("Acme Widgets", 2000.00, "CA", Date.valueOf("2014-02-01")));
        rows.add(RowFactory.create("Widgetry", 1000.00, "CA", Date.valueOf("2015-01-11")));
        rows.add(RowFactory.create("Widgets R Us", 2000.00, "CA", Date.valueOf("2015-02-19")));
        rows.add(RowFactory.create("Ye Olde Widgete", 3000.00, "MA", Date.valueOf("2015-02-28")));
        
        Dataset<Row> salesDF = sparkSession.createDataFrame(rows, schema);
        salesDF.createOrReplaceTempView("sales");
        
        // 注册自定义函数
        DateRange dateRange = new DateRange(LocalDate.parse("2015-01-01"), LocalDate.parse("2015-12-31"));
        sparkSession.udf().register("yearOnYear", new YearOnYear(dateRange));
        
        Dataset<Row> df01 = sparkSession.sql("select yearOnYear(sales, saleDate)as yearOnYear from sales");
        
        df01.show();
    }
}

class DateRange implements Serializable{

    private static final long serialVersionUID = 1L; 
    
    private LocalDate startDate;
    private LocalDate endDate;
    
    public DateRange(LocalDate startDate, LocalDate endDate) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean in(LocalDate targetDate){
        return targetDate.isBefore(endDate) && targetDate.isAfter(startDate);
    }
    
}


// 年度同比
class YearOnYear extends UserDefinedAggregateFunction {

    private DateRange current;
    
    public YearOnYear(DateRange current) {
        super();
        this.current = current;
    }

    // 该方法指定具体输入数据的类型 
    @Override
    public StructType inputSchema() {
        // TODO Auto-generated method stub
        return new StructType()
                .add("metric", DoubleType)
                .add("timeCategory", DateType);
    }
    
    // 在进行聚合操作的时候所要处理的数据的结果的类型
    @Override
    public StructType bufferSchema() {
        // TODO Auto-generated method stub
        return new StructType()
                .add("sumOfCurrent", DoubleType)
                .add("sumOfPrevious", DoubleType);
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
        buffer.update(0, 0d);
        buffer.update(1, 0d);
    }

    // 在进行聚合的时候，每当有新的值进来，对分组后的聚合如何进行计算  
    // 本地的聚合操作，相当于Hadoop MapReduce模型中的Combiner
    @Override
    public void update(MutableAggregationBuffer buffer, Row input) {
        // TODO Auto-generated method stub

        if (current.in(input.getDate(1).toLocalDate())) {
            buffer.update(0, buffer.getDouble(0) + input.getDouble(0));
        }

        DateRange previous = new DateRange(current.getStartDate().minusYears(1), current.getEndDate().minusYears(1));
        if (previous.in(input.getDate(1).toLocalDate())) {
            buffer.update(1, buffer.getDouble(0) + input.getDouble(0));
        }

    } 

    // 最后在分布式节点进行Local Reduce完成后需要进行全局级别的Merge操作
    @Override
    public void merge(MutableAggregationBuffer buffer1, Row buffer2) {
        // TODO Auto-generated method stub
        
        buffer1.update(0, buffer1.getDouble(0) + buffer2.getDouble(0));
        buffer1.update(1, buffer1.getDouble(1) + buffer2.getDouble(1));
        
    }

    // 返回UDAF最后的计算结果
    @Override
    public Object evaluate(Row buffer) {
        // TODO Auto-generated method stub

        BigDecimal b0 = BigDecimal.valueOf(buffer.getDouble(0));
        BigDecimal b1 = BigDecimal.valueOf(buffer.getDouble(1));
        
        if(b1.compareTo(BigDecimal.valueOf(0)) == 0){
            return String.valueOf(0);
        }else{
            //(b0 -b1) / b1 * 100
            
            return String.valueOf(b0.subtract(b1).divide(b1).multiply(BigDecimal.valueOf(100))+"%");
        }
        
    }
    
  }  

