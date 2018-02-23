# SparkBaby

从零开始，用java写spark代码， :joy:  :joy:  :joy:

![](https://img.shields.io/badge/windows-7-red.svg)  ![](https://img.shields.io/badge/jdk-1.8-blue.svg)  ![](https://img.shields.io/badge/hadoop-2.8.1-brightgreen.svg)  ![](https://img.shields.io/badge/scala-2.11.8-green.svg)  ![](https://img.shields.io/badge/spark-2.2.0.bin.hadoop2.7-orange.svg)

## 环境

环境安装文档 -- [windows7安装spark环境](ext/windows7安装spark环境.md)

## demo测试

### DemoTask

从数据库获取数据，进行demo运算

``` sh
mvn install

spark-submit  --jars E:\gbase\sparkjar\mysql-connector-java-8.0.7-dmr.jar --class com.sauzny.sparkbaby.DemoTask --master local E:\code\github\sauzny\sparkbaby\target\sparkbaby-0.0.1-SNAPSHOT.jar
```

### LogTask

生成测试日志文件，执行`com.sauzny.sparkbaby.demolog.Builder`，生成测试日志

测试中用到了jdbc相关代码，在`--jars`命令中增加了jdbc的jar包

执行命令（路径自己修改）：

``` sh
mvn install

spark-submit  --jars E:\gbase\sparkjar\mysql-connector-java-8.0.7-dmr.jar --class com.sauzny.sparkbaby.LogTask --master local E:\code\github\sauzny\sparkbaby\target\sparkbaby-0.0.1-SNAPSHOT.jar E:\data\log\sparkbaby\demo.2017-09-20.0.log
```

结果截取：
```
...
+-------+------------------+------+
|summary|        threadName|leavel|
+-------+------------------+------+
|  count|            500000|500000|
|   mean|              null|  null|
| stddev|              null|  null|
|    min|[pool-1-thread-10]| DEBUG|
|    max| [pool-1-thread-9]|  WARN|
+-------+------------------+------+
...
```

## demo代码说明

demo代码主要在此目录中 `com.sauzny.sparkbaby.demolog.hsbd`

| 文件名 | 功能描述 |
|--------|--------|
| Hsbd01 | flatMap函数，数据转换 |
| Hsbd02 | 获取源数据后，自定义schema |
| Hsbd03 | JDBC读写数据 |
| Hsbd04 | UDF和UDAF，基本用法 |
| Hsbd05 | UDAF实现年度同比 |
| Hsbd06 | Spark streaming kafka |
| Hsbd07 | Spark streaming 文件系统、scoket端口 |
| Hsbd08 | 非对称查询 |
| Hsbd09 | 半年占比计算 |

## 参考

http://www.cnblogs.com/arachis/p/Spark_Shuffle.html

https://spark.apache.org/docs/latest/sql-programming-guide.html#datasets-and-dataframes

https://spark.apache.org/docs/latest/api/java/index.html

## TODO

- [ ] spark中的一些概念
- [ ] shuffle

## 其他文档

[柏松采样器或伯努利采样器](ext/柏松采样器或伯努利采样器.md)

[日志log4j变logbak](ext/日志log4j变logbak.md)

[使用schema方式创建df](ext/使用schema方式创建df.md)

[cube和rollup](ext/cube和rollup.md)

[Encoders](ext/Encoders.md)

[map、mapPartitions、flatMap](ext/map、mapPartitions、flatMap.md)

[reduce](ext/reduce.md)

[reduceByKey和GroupByKey](ext/reduceByKey和GroupByKey.md)

[windows7安装spark环境](ext/windows7安装spark环境.md)

## Tips

windows上运行spark后会出现一个异常，暂不影响使用：

```
java.io.IOException: Failed to delete: C:\Users\1\AppData\Local\Temp\spark-635a2a52-c05f-43ab-b8cf-fc1dd4335ec7\userFiles-d6ce4cae-ef62-4f83-983f-b649ef2519d3
```