package com.sauzny.sparkbaby.demolog.ml.word;

import org.apache.spark.ml.feature.HashingTF;  
import org.apache.spark.ml.feature.IDF;  
import org.apache.spark.ml.feature.IDFModel;  
import org.apache.spark.ml.feature.Tokenizer;  
import org.apache.spark.sql.Dataset;  
import org.apache.spark.sql.Row;  
import org.apache.spark.sql.RowFactory;  
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;  
import org.apache.spark.sql.types.Metadata;  
import org.apache.spark.sql.types.StructField;  
import org.apache.spark.sql.types.StructType;

import java.util.Arrays;  
import java.util.List;  

public class MyTFIDF {
    
    /*
词频（Term Frequency）：某关键词在文本中出现次数
逆文档频率（Inverse Document Frequency）：大小与一个诩的常见程度成反比
TF = 某个词在文章中出现的次数/文章的总词数
IDF = log(查找的文章总数　/ (包含该词的文章数　+ 1))
TF-IDF = TF(词频)　x IDF(逆文档频率)
     */

    public static void main(String[] args){  

        String master = "local[*]";
        SparkSession sparkSession =
                SparkSession.builder().master(master).appName("TF-IDF").config("spark.some.config.option", "config-value").getOrCreate();
  
        List<Row> data = Arrays.asList(  
                RowFactory.create(0.0, "Hi I heard about Spark"),  
                RowFactory.create(0.0, "I wish Java could use case classes"),  
                RowFactory.create(1.0, "Logistic regression models are neat")  
        );  
  
        StructType schema = new StructType(new StructField[]{  
                new StructField("label", DataTypes.DoubleType, false, Metadata.empty()),  
                new StructField("sentence", DataTypes.StringType, false, Metadata.empty())  
        });  
  
        Dataset<Row> sentenceData = sparkSession.createDataFrame(data, schema);  
  
        Tokenizer tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words");  
        Dataset<Row> wordsData = tokenizer.transform(sentenceData);  
  
        int numFeatures = 20;  
        HashingTF hashingTF = new HashingTF()  
                .setInputCol("words")  
                .setOutputCol("rawFeatures")  
                .setNumFeatures(numFeatures);  
  
        Dataset<Row> featurizedData = hashingTF.transform(wordsData);  
        // alternatively, CountVectorizer can also be used to get term frequency vectors  
  
        IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");  
        IDFModel idfModel = idf.fit(featurizedData);  
  
        Dataset<Row> rescaledData = idfModel.transform(featurizedData);  
        rescaledData.select("label", "features").show(false);  
  
//+-----+----------------------------------------------------------------------------------------------------------------------+  
//|label|features                                                                                                              |  
//+-----+----------------------------------------------------------------------------------------------------------------------+  
//|0.0  |(20,[0,5,9,17],[0.6931471805599453,0.6931471805599453,0.28768207245178085,1.3862943611198906])                        |  
//|0.0  |(20,[2,7,9,13,15],[0.6931471805599453,0.6931471805599453,0.8630462173553426,0.28768207245178085,0.28768207245178085]) |  
//|1.0  |(20,[4,6,13,15,18],[0.6931471805599453,0.6931471805599453,0.28768207245178085,0.28768207245178085,0.6931471805599453])|  
//+-----+----------------------------------------------------------------------------------------------------------------------+  
          
        sparkSession.stop();  
    }  
}
