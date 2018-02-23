

- map: 对RDD每个元素转换

- mapPartitions是map的一个变种。map的输入函数是应用于RDD中每个元素，而mapPartitions的输入函数是应用于每个分区，也就是把每个分区中的内容作为整体来处理的。

- flatMap: 对RDD每个元素转换, 然后再扁平化（即将所有对象合并为一个对象）

e.g.

// data 有两行数据，第一行 a,b,c，第二行1,2,3

``` scala

scala>data.map(line1 => line1.split(",")).collect()
res11: Array[Array[String]] = Array(Array(a, b, c),Array(1, 2, 3))

scala> val a = sc.parallelize(1 to 9, 3)
scala> def myfunc[T](iter: Iterator[T]) : Iterator[(T, T)] = {
    var res = List[(T, T)]() 
    var pre = iter.next while (iter.hasNext) {
        val cur = iter.next; 
        res .::= (pre, cur) pre = cur;
    } 
    res.iterator
}
scala> a.mapPartitions(myfunc).collect
res0: Array[(Int, Int)] = Array((2,3), (1,2), (5,6), (4,5), (8,9), (7,8))

scala>data.flatMap(line1 => line1.split(",")).collect()
res13: Array[String] = Array(a, b, c, 1, 2, 3)

```