package com.sauzny.sparkbaby.demolog.ml.pipeline;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.Word2Vec;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.ArrayType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;


public class JavaPipelineExample2 {

    public static void main(String[] args) {
        
        String master = "local[*]";
        SparkSession sparkSession =
                SparkSession.builder().master(master).appName("JavaPipelineExample").config("spark.some.config.option", "config-value").getOrCreate();
        
        // 训练数据
        List<Row> data = Arrays.asList(  
                RowFactory.create(Arrays.asList("Hi I heard about Spark".split(" ")), 1.0),  
                RowFactory.create(Arrays.asList("I wish Java could use case classes".split(" ")), 0.0),  
                RowFactory.create(Arrays.asList("Logistic regression models are neat".split(" ")), 0.0), 
                RowFactory.create(Arrays.asList("haha spark ml is not reading".split(" ")), 1.0) 
        );  
  
        StructType schema = new StructType(new StructField[]{  
                new StructField("text", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())  ,
                new StructField("label", DataTypes.DoubleType, false, Metadata.empty())  
        });  
        
        Dataset<Row> training = sparkSession.createDataFrame(data, schema);  
        
        // 算法一
        Word2Vec word2Vec = new Word2Vec()  
                .setInputCol("text")  
                .setOutputCol("features")  
                .setVectorSize(3)  
                .setMinCount(0);  
        
        // 如果需要算法二
        // 则 算法一的输出 作为 算法二的输入
        // HashingTF hashingTF = new HashingTF().setNumFeatures(1000).setInputCol(word2Vec.getOutputCol()).setOutputCol("features");
        
        // 设置 lr
        LogisticRegression lr = new LogisticRegression().setMaxIter(10).setRegParam(0.001);
        
        // 用算法填充 pipeline
        Pipeline pipeline = new Pipeline().setStages(new PipelineStage[] {word2Vec, lr});

        // 训练，生成 model
        PipelineModel model = pipeline.fit(training);

        // 测试数据
        List<Row> testList = Arrays.asList(  
                RowFactory.create(Arrays.asList("spark i j k".split(" "))),  
                RowFactory.create(Arrays.asList("l m n".split(" "))),  
                RowFactory.create(Arrays.asList("spark hadoop spark".split(" ")))  
        );
        
        StructType testSchema = new StructType(new StructField[]{  
                new StructField("text", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())
        }); 
        
        Dataset<Row> test = sparkSession.createDataFrame(testList, testSchema);

        // 预测数据
        Dataset<Row> predictions = model.transform(test);
        
        // 打印预测结果
        predictions.printSchema();
        predictions.show();

        // probability  可能性
        
        // prediction   打分
        
        /*
            +--------------------+--------------------+--------------------+--------------------+----------+
            |                text|            features|       rawPrediction|         probability|prediction|
            +--------------------+--------------------+--------------------+--------------------+----------+
            |    [spark, i, j, k]|[0.03048800490796...|[-1.6127210043860...|[0.16621118055007...|       1.0|
            |           [l, m, n]|       [0.0,0.0,0.0]|[3.49585804214241...|[0.97056968798406...|       0.0|
            |[spark, hadoop, s...|[0.08130134642124...|[-10.127019415266...|[3.99828718647074...|       1.0|
            +--------------------+--------------------+--------------------+--------------------+----------+ 
         */
        sparkSession.stop();
    }
}
