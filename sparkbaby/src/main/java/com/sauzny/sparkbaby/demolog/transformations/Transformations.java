package com.sauzny.sparkbaby.demolog.transformations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.KeyValueGroupedDataset;
import org.apache.spark.sql.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.spark.sql.functions.*;

import com.sauzny.sparkbaby.demolog.LogEntity;

import scala.Tuple2;

/**
 * *************************************************************************
 * @文件名称: Transformations.java
 *
 * @包路径  : com.sauzny.sparkbaby.demolog.transformations 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   Transformations demo
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年9月20日 - 上午10:08:36 
 *	
 **************************************************************************
 */
public class Transformations {
    
    private static final Logger logger = LoggerFactory.getLogger(Transformations.class);

    public static void run(Dataset<LogEntity> dataset){
        
        /*
            api demo
        
         *   people.filter(people.col("age").gt(30))
         *     .join(department, people.col("deptId").equalTo(department.col("id")))
         *     .groupBy(department.col("name"), people.col("gender"))
         *     .agg(avg(people.col("salary")), max(people.col("age")));
        
         */
        
        /* -----Typed transformations---- */
        
        /* Returns a new Dataset with an alias set. */
        Dataset<LogEntity> aliasDataset = dataset.as("alias");
        
        /* 同 as */
        // dataset.alias("alias");
        
        /* Returns a new Dataset that has exactly numPartitions partitions, when the fewer partitions are requested. */
        /* 按照设置的partitions数量，返回一个新的 dataset */
        dataset.coalesce(2);
        
        /* 数据去重 */
        // distinct 操作内部是会进行shuffle 排序的
        dataset.distinct().show();
        
        /* 数据去重 */
        
        /* 所有字段的值都重复，被认为是重复记录，去掉重复记录 */
        dataset.dropDuplicates().show();
        
        /* 字段threadName的值重复，被认为是重复记录，去掉重复记录 */
        dataset.dropDuplicates("threadName").show();
        
        /* 字段level的值重复，被认为是重复记录，去掉重复记录 */
        dataset.dropDuplicates("level").show();
        
        /* 字段threadName的值重复 && 字段level的值重复，被认为是重复记录，去掉重复记录 */
        dataset.dropDuplicates("threadName", "level").show();
        
        /* 并集   dataset 和 otherDataset 两个集合的所有数据，不去重复 */
        dataset.union(dataset);
        // unionAll不推荐使用   官方api @deprecated("use union()", "2.0.0")
        // dataset.unionAll(dataset);
        
        /* except 排除    dataset 排除otherDataset 中的数据  */
        dataset.except(dataset);
        
        /* 交集 */
        // dataset.intersect(otherDataset).show();
        
        /* join操作 */
        /* 默认是使用内连接，有参数可以设置，join的时候哪些字段需要相等，设置连接方式 */
        /* 注意返回类型的区别 */
        // join 是 Typed transformations ，在Typed transformations部分代码中，有简单写法
        Dataset<Row> rowDataset = dataset.join(dataset, dataset.col("studentAge").equalTo(dataset.col("studentAge")));
        Dataset<Tuple2<LogEntity, LogEntity>> tuple2Dataset = dataset.joinWith(dataset, dataset.col("studentAge").equalTo(dataset.col("studentAge")));
        /* 笛卡尔积…… 把表A和表B的数据进行一个N*M的组合，即笛卡尔积。如本例会产生4*4=16条记录，在开发过程中我们肯定是要过滤数据，所以这种很少用。 */
        Dataset<Row> crossDataset = dataset.crossJoin(dataset);
        
        // filter 有多种写法
        // 字段是String类型，可以使用equals来比较
        long agegt30fun = dataset.filter(logEntity -> logEntity.getStudentAge() > 30).count();
        long agegt40str = dataset.filter("studentAge > 40").count();
        long agegt50col = dataset.filter(dataset.col("studentAge").gt(40)).count();

        // where写法
        long agegt40str_where = dataset.where("studentAge > 40").count();
        long agegt50col_where = dataset.where(dataset.col("studentAge").gt(40)).count();
        
        /*
            eq 等于
            neq 不等于
            gt 大于
            egt 大于等于
            lt 小于
            elt 小于等于
            like LIKE
            between BETWEEN
            notnull IS NUT NULL
            null IS NULL
         */
        
        logger.info("agegt30fun 的数量是 {}", agegt30fun);
        logger.info("agegt40str 的数量是 {}", agegt40str);
        logger.info("agegt50col 的数量是 {}", agegt50col);
        logger.info("agegt40str_where 的数量是 {}", agegt40str_where);
        logger.info("agegt50col_where 的数量是 {}", agegt50col_where);
        
        /* 查询结果展示字段选择 */
        dataset.select("studentAge").show();
        
        /* 排序 */
        dataset.sort("level").show();
        dataset.sort(dataset.col("level"), dataset.col("studentAge").desc()).show();
        dataset.sortWithinPartitions("level");
        
        /* 不明白... */
        //dataset.transform();
        
        
        
        /* 转换dataset中的entity的类型，LogTask这个类中使用 */
        /* 另有文档说明这三个函数的区别 */
        // dataset.map(func, evidence$6)
        // dataset.mapPartitions(func, evidence$7)
        // dataset.flatMap(f, encoder)
        
        /* 性能较差，使用其他函数代替，reduce??? */
        // 按照 level 这个字段 groupby，这里可以给一个function
        KeyValueGroupedDataset<String, LogEntity> kvgDataset = dataset.groupByKey(logEntity -> logEntity.getLevel(), Encoders.STRING());
        
        /* top2 */
        dataset.limit(2).show();
        
        /* order by */
        dataset.orderBy("studentAge").show();
        dataset.orderBy(dataset.col("studentAge")).show();
        
        /* 这几个分割子集的函数 会使用 立柏松采样器或伯努利采样器 */
        
        /* randomSplit */
        //注意，权重的总和加起来为1，否则会不正常
        Dataset<LogEntity>[] datasets = dataset.randomSplit(new double[]{0.3, 0.4, 0.3});
        List<Dataset<LogEntity>> datasetList = dataset.randomSplitAsList(new double[]{0.3, 0.4, 0.3}, 0L);
        
        /* 重新分配 partition，可以按照 partition 可以按照编号、字段分*/
        dataset.repartition(2);
        dataset.repartition(dataset.col("studentAge"));
        dataset.repartition(4, dataset.col("studentAge"), dataset.col("threadName"));
        
        //Returns a new Dataset by sampling a fraction of rows, using a random seed.
        //返回一个新的数据集抽样的一小部分行,使用一个随机种子
        dataset.sample(true, 0.6);
        
        
        /* -----Typed transformations---- */
        
        //dataset.groupBy("threadName").agg(org.apache.spark.sql.functions.max(dataset.col("studentAge")));
        dataset.groupBy("threadName").agg(max("studentAge"),avg(dataset.col("studentAge"))).show();
        
        // 这个map参数 搞笑了……  studentAge作为key，既要算max又要算avg， 要怎么写？
        dataset.groupBy("threadName").agg(new HashMap<String, String>() {{ put("studentAge", "max");  put("studentAge", "avg"); }}).show();
        
        // 获取这个字段
        Column studentAgeColumn_apply = dataset.apply("studentAge");
        Column studentAgeColumn_col = dataset.col("studentAge");
        
        // 组成 cube cube这个概念有文档
        dataset.cube("threadName","studentAge").agg(avg("studentAge")).show();
        
        // 组成rollup rollup这个概念有文档 和cube有对比
        dataset.rollup("studentAge", "threadName").avg().show();
        
        // 去掉字段
        dataset.drop("studentAge").show();
        dataset.drop(dataset.col("studentAge")).show();
        
        // select 别名
        dataset.selectExpr("threadName","studentAge+1","threadName as NAME","studentAge as AGE").show();
        dataset.select(expr("threadName"),expr("studentAge+1"), expr("threadName as NAME"), expr("studentAge as AGE")).show();
        
        // 多展示一个NAME列，展示的是threadName列的内容
        dataset.withColumn("NAME",col("threadName")).show();
        
        /**
            +--------------------+-----+----------+------------+--------------------+----------+------------------+-----+------------------+
            |           className|level| localDate|   localTime|             message|studentAge|        threadName| zone|              NAME|
            +--------------------+-----+----------+------------+--------------------+----------+------------------+-----+------------------+
            |com.sauzny.sparkb...|DEBUG|2017-09-20|09:30:51.555|{"name":"呼若","age...|        20|[pool-1-thread-13]|+0800|[pool-1-thread-13]|
            |com.sauzny.sparkb...| INFO|2017-09-20|09:30:51.555|{"name":"诸葛菁","ag...|        38| [pool-1-thread-7]|+0800| [pool-1-thread-7]|
            |com.sauzny.sparkb...|ERROR|2017-09-20|09:30:51.557|{"name":"杨秀玲","ag...|        23|[pool-1-thread-13]|+0800|[pool-1-thread-13]|
            |com.sauzny.sparkb...|DEBUG|2017-09-20|09:30:51.557|{"name":"琴翠佳","ag...|        45| [pool-1-thread-7]|+0800| [pool-1-thread-7]|
            |com.sauzny.sparkb...| WARN|2017-09-20|09:30:51.557|{"name":"罗涛时","ag...|        31|[pool-1-thread-13]|+0800|[pool-1-thread-13]|
            |com.sauzny.sparkb...| INFO|2017-09-20|09:30:51.557|{"name":"芒妹","age...|        44| [pool-1-thread-7]|+0800| [pool-1-thread-7]|
            |com.sauzny.sparkb...|DEBUG|2017-09-20|09:30:51.558|{"name":"休辰承","ag...|        31|[pool-1-thread-13]|+0800|[pool-1-thread-13]|
            |com.sauzny.sparkb...| INFO|2017-09-20|09:30:51.558|{"name":"真河策","ag...|        19| [pool-1-thread-7]|+0800| [pool-1-thread-7]|
            |com.sauzny.sparkb...| INFO|2017-09-20|09:30:51.558|{"name":"鱼言固","ag...|        43|[pool-1-thread-13]|+0800|[pool-1-thread-13]|
            |com.sauzny.sparkb...| INFO|2017-09-20|09:30:51.555|{"name":"度欣娟","ag...|        17| [pool-1-thread-9]|+0800| [pool-1-thread-9]|
            |com.sauzny.sparkb...|DEBUG|2017-09-20|09:30:51.555|{"name":"巫马利军","a...|        51| [pool-1-thread-8]|+0800| [pool-1-thread-8]
            |com.sauzny.sparkb...|DEBUG|2017-09-20|09:30:51.558|{"name":"赫明永","ag...|        36| [pool-1-thread-9]|+0800| [pool-1-thread-9]|
            |com.sauzny.sparkb...|ERROR|2017-09-20|09:30:51.555|{"name":"韦康","age...|        53| [pool-1-thread-5]|+0800| [pool-1-thread-5]|
            |com.sauzny.sparkb...|ERROR|2017-09-20|09:30:51.555|{"name":"天妍","age...|        48|[pool-1-thread-16]|+0800|[pool-1-thread-16]|
            |com.sauzny.sparkb...|DEBUG|2017-09-20|09:30:51.555|{"name":"关奇","age...|        20| [pool-1-thread-3]|+0800| [pool-1-thread-3]|
            |com.sauzny.sparkb...|DEBUG|2017-09-20|09:30:51.559|{"name":"圭达","age...|        32|[pool-1-thread-16]|+0800|[pool-1-thread-16]|
            |com.sauzny.sparkb...|TRACE|2017-09-20|09:30:51.559|{"name":"左青","age...|        25| [pool-1-thread-5]|+0800| [pool-1-thread-5]|
            |com.sauzny.sparkb...| INFO|2017-09-20|09:30:51.559|{"name":"索仪","age...|        32| [pool-1-thread-3]|+0800| [pool-1-thread-3]|
            |com.sauzny.sparkb...| WARN|2017-09-20|09:30:51.559|{"name":"玉蕊","age...|        50|[pool-1-thread-16]|+0800|[pool-1-thread-16]|
            |com.sauzny.sparkb...| INFO|2017-09-20|09:30:51.559|{"name":"圣璧娣","ag...|        37| [pool-1-thread-3]|+0800| [pool-1-thread-3]|
            +--------------------+-----+----------+------------+--------------------+----------+------------------+-----+------------------+
            only showing top 20 rows
         */
        
        // 字段别名
        dataset.withColumnRenamed("threadName","newName").show();
        
        // Dropping rows containing any null values.
        dataset.na().drop();
        
        // Finding frequent items in column with name 'threadName'.
        dataset.stat().freqItems(new String[]{"threadName"}).show();
        
        //dataSet.show()
        
        /**
        *
        +-------+---+
        |   name|age|
        +-------+---+
        |Michael| 29|
        |   Andy| 30|
        | Justin| 19|
        +-------+---+
        */
        
        // join的写法
        
        //dataSet.join(tmpDataSet).show()

        /**
          * +-------+---+--------+---+
            |Michael| 29|legotime|100|
            |Michael| 29|    lego| 19|
            |   Andy| 30|legotime|100|
            |   Andy| 30|    lego| 19|
            | Justin| 19|legotime|100|
            | Justin| 19|    lego| 19|
            +-------+---+--------+---+
          */
        //dataSet.join(tmpDataSet,"age").show()

        /**
          * +---+------+----+
            |age|  name|name|
            +---+------+----+
            | 19|Justin|lego|
            +---+------+----+
          */
        //dataSet.join(tmpDataSet,Seq("age","name")).show()

        /**
          * +---+----+
            |age|name|
            +---+----+
            +---+----+
          */
    }
}
