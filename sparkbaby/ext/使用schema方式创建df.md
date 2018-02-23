# 使用schema方式创建df

这篇文档只是记录了解决问题的过程，解决问题的办法直接去看代码就行了

`com.sauzny.sparkbaby.demolog.hsbd.Hsbd02`

## 需求

不在java中硬编码读取数据之后的struct

## 解决过程

因为在写 api demo code 的时候，使用到了打印schema，获取schema这样的方法

所以想到能不能设置schema，实现不硬编码struct

### 一、设置schema

找来找去，找到了 `sparkSession.createDataFrame(rdd, schema);`

### 二、创建rdd和schema

出现了搞笑的事情……

#### 创建 rdd

我想到了有函数 `dataset.toJavaRDD()`，然后我写出了这样的代码过程

`Dataset<String> -> Dataset<Row> -> JavaRDD<Row>`

```
	Dataset<Row> df01 = dataset.map(line -> {
            String[] strs = line.split(" - ");
            return RowFactory.create(strs[0], strs[1]);
        }, Encoders.bean(Row.class));

    JavaRDD<Row> rdd = df01.toJavaRDD();
```

#### 创建 schema

```
        StructField a2 = new StructField("a2", new StringType(), true, null);
        StructField b2 = new StructField("a2", new StringType(), true, null);

        StructType schema = new StructType(new StructField[]{a2, b2});
```

#### 结果就是各种报错，报各种错

- df01.show()，结果是空的

- Match Error StringType

- NullPointerException

### 三、各种找错+变换思路

终于变成代码现在的样子……

### 四、java && scala

大家知道这三人不：

- 曹操
- 曹丕
- 甄姬

用java写spark而不用scala写spark，就好像曹操没有把甄姬赐给曹丕，而是自己上了……