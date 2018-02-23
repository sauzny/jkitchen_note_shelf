# Spark函数扩展

## UDF

注册之后即可使用

```java

// 在目前版本 2.2.0 中 UDF函数最多可以接受22个输入参数

sparkSession.udf().register("nameLength", (String name) -> name.length(), IntegerType);

Dataset<Row> df01 = sparkSession.sql("SELECT nameLength(studentName) as nameLength_int, count(studentName) FROM myLog group by nameLength_int");

df01.show();

```

UDF缺点：无法在函数内部支持对表数据的聚合运算。

例如，当我要对销量执行年度同比计算，就需要对当年和上一年的销量分别求和，然后再利用同比公式进行计算。

## UDAF

### 1.继承`UserDefinedAggregateFunction`

创建一个继承`UserDefinedAggregateFunction`的自定义类的实例

实现其中的八个抽象方法，用来设置输入输出类型、中间过程的计算、最后的合并。具体代码查看demo04或者demo05。

``` java

    @Override
    public StructType bufferSchema() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DataType dataType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean deterministic() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Object evaluate(Row arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initialize(MutableAggregationBuffer arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public StructType inputSchema() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void merge(MutableAggregationBuffer arg0, Row arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void update(MutableAggregationBuffer arg0, Row arg1) {
        // TODO Auto-generated method stub
    }
```

### 2.注册UDAF

```java
// 注册自定义函数
DateRange dateRange = new DateRange(LocalDate.parse("2015-01-01"), LocalDate.parse("2015-12-31"));
sparkSession.udf().register("yearOnYear", new YearOnYear(dateRange));

Dataset<Row> df01 = sparkSession.sql("select yearOnYear(sales, saleDate)as yearOnYear from sales");

df01.show();

```
