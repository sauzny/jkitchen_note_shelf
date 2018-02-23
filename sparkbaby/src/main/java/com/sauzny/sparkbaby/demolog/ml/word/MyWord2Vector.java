package com.sauzny.sparkbaby.demolog.ml.word;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.ml.feature.Word2Vec;  
import org.apache.spark.ml.feature.Word2VecModel;  
import org.apache.spark.ml.linalg.Vector;  
import org.apache.spark.sql.Dataset;  
import org.apache.spark.sql.Row;  
import org.apache.spark.sql.RowFactory;  
import org.apache.spark.sql.types.*;

import com.sauzny.sparkbaby.demolog.ml.entity.JavaDocument;

import java.util.Arrays;  
import java.util.List; 

public class MyWord2Vector {
    
    /*
Word2vec是一个Estimator，它采用一系列代表文档的词语来训练word2vecmodel。
该模型将每个词语映射到一个固定大小的向量。
word2vecmodel使用文档中每个词语的平均数来将文档转换为向量，然后这个向量可以作为预测的特征，来计算文档相似度计算等等。
 在下面的代码段中，我们首先用一组文档，其中每一个文档代表一个词语序列。对于每一个文档，我们将其转换为一个特征向量。此特征向量可以被传递到一个学习算法。
     */
    public static void main(String[] args){  

        String master = "local[*]";
        SparkSession sparkSession =
                SparkSession.builder().master(master).appName("Word2Vector").config("spark.some.config.option", "config-value").getOrCreate();
    
        // Input data: Each row is a bag of words from a sentence or document.  
        List<Row> data = Arrays.asList(  
                RowFactory.create(Arrays.asList("Hi I heard about Spark".split(" "))),  
                RowFactory.create(Arrays.asList("I wish Java could use case classes".split(" "))),  
                RowFactory.create(Arrays.asList("Logistic regression models are neat".split(" ")))  
        );  
  
        StructType schema = new StructType(new StructField[]{  
                new StructField("text", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())  
        });  
        Dataset<Row> documentDF = sparkSession.createDataFrame(data, schema);  
  
        // Learn a mapping from words to Vectors.  
        Word2Vec word2Vec = new Word2Vec()  
                .setInputCol("text")  
                .setOutputCol("result")  
                .setVectorSize(1)  
                .setMinCount(0);  
        Word2VecModel model = word2Vec.fit(documentDF);  
        Dataset<Row> result = model.transform(documentDF);  
  
        result.show();
        
        for(Row  row : result.collectAsList()){  
            List<String> text  = row.getList(0);  
            Vector vector = (Vector)row.get(1);  
            System.out.println("Text: " + text + "\t=>\t Vector: " + vector);  
        }  
  
//Text: [Hi, I, heard, about, Spark]    =>    Vector: [-0.02205655723810196]  
//Text: [I, wish, Java, could, use, case, classes]  =>    Vector: [-0.009554644780499595]  
//Text: [Logistic, regression, models, are, neat]   =>    Vector: [-0.12159877410158515]  
          
        

        List<Row> testList = Arrays.asList(
                RowFactory.create(Arrays.asList("Hi I heard about Spark".split(" "))),
                RowFactory.create(Arrays.asList("I wish Java could use case classes".split(" "))),
                RowFactory.create(Arrays.asList("Logistic regression models are neat".split(" "))));

        Dataset<Row> test = sparkSession.createDataFrame(testList, schema);

        // Make predictions on test documents.
        Dataset<Row> predictions = model.transform(test);
        predictions.printSchema();
        predictions.show();
        
        
        
        sparkSession.stop();  
    }  
}
