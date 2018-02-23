# windows7安装spark环境

## 下载

```
JDK1.8 -- 我已经安装过了
http://spark.apache.org/downloads.html
http://hadoop.apache.org/releases.html
http://www.scala-lang.org/download/2.11.8.html
https://github.com/steveloughran/winutils/tree/master/hadoop-2.8.1
```

## 下载之后的安装包

- hadoop-2.8.1.tar.gz
- scala-2.11.8.zip
- spark-2.2.0-bin-hadoop2.7.tgz
- winutils.exe

## 配置环境变量

变量名 | 变量值 | 说明
---|---|---
JAVA_HOME | D:\Program Files\Java\jdk1.8.0_131 | cmd校验：java javac
SCALA_HOME | D:\scala-2.11.8\bin | cmd校验：scala
SPARK_HOME | D:\spark-2.2.0-bin-hadoop2.7\bin | cmd校验：spark-shell
HADOOP_HOME | D:\hadoop-2.8.1 | 注意没有变量值中没有`bin`
PATH | ;%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;%HADOOP_HOME%;%SCALA_HOME%;%SPARK_HOME%; | 需要配置`JDK``JRE``HADOOP``SCALA``SPARK`的目录

## 设置`winutils.exe`

1. 将文件 `winutils.exe` 放在 `D:\hadoop-2.8.1\bin` 目录下

2. 执行命令

```
D:\hadoop-2.8.1\bin\winutils.exe chmod 777 c:\tmp\hive
```

ps：这两步骤不执行的话，启动spark会报错。

## 测试启动spark

```
spark-shell
```

出现（不报错就ok了）：

```
Spark context Web UI available at http://172.16.1.71:4040
Spark context available as 'sc' (master = local[*], app id = local-1505726368561).
Spark session available as 'spark'.
Welcome to
      ____              __
     / __/__  ___ _____/ /__
    _\ \/ _ \/ _ `/ __/  '_/
   /___/ .__/\_,_/_/ /_/\_\   version 2.2.0
      /_/

Using Scala version 2.11.8 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_131)
Type in expressions to have them evaluated.
Type :help for more information.

scala>

```

其中 `http://172.16.1.71:4040` 访问看看


## 测试官方demo

```
D:\spark-2.2.0-bin-hadoop2.7>spark-submit  --class   org.apache.spark.examples.SparkPi  --master   local  examples/jars/spark-examples_2.11-2.2.0.jar
```

## tips

血的教训：永远不要在软件的安装路径中留有任何的空格