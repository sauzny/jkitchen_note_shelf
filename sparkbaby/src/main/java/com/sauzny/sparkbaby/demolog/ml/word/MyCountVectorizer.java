package com.sauzny.sparkbaby.demolog.ml.word;

import org.apache.spark.ml.feature.CountVectorizer;  
import org.apache.spark.ml.feature.CountVectorizerModel;  
import org.apache.spark.sql.Dataset;  
import org.apache.spark.sql.Row;  
import org.apache.spark.sql.RowFactory;  
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.*;  
  
import java.util.Arrays;  
import java.util.List;  
  
public class MyCountVectorizer { 
    
    /*
Countvectorizer和Countvectorizermodel旨在通过计数来将一个文档转换为向量。
当不存在先验字典时，Countvectorizer可作为Estimator来提取词汇，并生成一个Countvectorizermodel。
该模型产生文档关于词语的稀疏表示，其表示可以传递给其他算法如LDA。
在fitting过程中，countvectorizer将根据语料库中的词频排序选出前vocabsize个词。
一个可选的参数minDF也影响fitting过程中，它指定词汇表中的词语在文档中最少出现的次数。
另一个可选的二值参数控制输出向量，如果设置为真那么所有非零的计数为1。
这对于二值型离散概率模型非常有用。
        
        示例：假设我们有一个DataFrame包含id和texts属性：
        
 id | texts
----|----------
 0  | Array("a", "b", "c")
 1  | Array("a", "b", "b", "c", "a")
 
texts的每一行是一个Array[String]类型的文档，调用CountVectorizer的fit方法产生一个（a,b,c）的词汇CountVectorizerModel ，经过transformation后产生如下结果：
        
 id | texts                           | vector
----|---------------------------------|---------------
 0  | Array("a", "b", "c")            | (3,[0,1,2],[1.0,1.0,1.0])
 1  | Array("a", "b", "b", "c", "a")  | (3,[0,1,2],[2.0,2.0,1.0])
     */
    
    public static void main(String[] args){  
        
        String master = "local[*]";
        SparkSession sparkSession =
                SparkSession.builder().master(master).appName("CountVectorizer").config("spark.some.config.option", "config-value").getOrCreate();
        
        // Input data: Each row is a bag of words from a sentence or document.  
        //输入每一行都是一个文档类型的数组（字符串）  
        List<Row> data = Arrays.asList(  
                RowFactory.create(Arrays.asList("a", "b", "c")),  
                RowFactory.create(Arrays.asList("a", "b", "b", "c", "a"))  
        );  
  
        StructType schema = new StructType(new StructField[] {  
                new StructField("text", new ArrayType(DataTypes.StringType, true), false, Metadata.empty())  
        });  
  
        Dataset<Row> df = sparkSession.createDataFrame(data, schema);  
  
        // fit a CountVectorizerModel from the corpus  
        CountVectorizerModel cvModel = new CountVectorizer()  
                .setInputCol("text")  
                .setOutputCol("feature")  
                .setVocabSize(3) //词典大小  
                .setMinDF(2) //指定词汇表中的词语在文档中最少出现的次数  
                .fit(df);  
  
  
        cvModel.transform(df).show(false);  
  
//输出：每个向量代表文档的词汇表中每个词语出现的次数  
//+---------------+-------------------------+  
//|text           |feature                  |  
//+---------------+-------------------------+  
//|[a, b, c]      |(3,[0,1,2],[1.0,1.0,1.0])|  
//|[a, b, b, c, a]|(3,[0,1,2],[2.0,2.0,1.0])|  
//+---------------+-------------------------+  
  
        sparkSession.stop();  
    }  
}  
