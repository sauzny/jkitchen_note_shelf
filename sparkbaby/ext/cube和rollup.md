
cube和rollup的区别在于

- cube是group by所有列
- rollup只group by第一列

`dataSet.show()`

        +-------+---+
        |   name|age|
        +-------+---+
        |Michael| 29|
        |   Andy| 30|
        | Justin| 19|
        +-------+---+

`dataSet.cube("age","name").agg(avg($"age")).show()`

		+----+-------+--------+
        | age|   name|max(age)|
        +----+-------+--------+
        |null|Michael|      29|
        |null|   null|      30|
        |  29|Michael|      29|
        |  19|   null|      19|
        |  30|   Andy|      30|
        |  30|   null|      30|
        |null|   Andy|      30|
        |  19| Justin|      19|
        |  29|   null|      29|
        |null| Justin|      19|
        +----+-------+--------+

`dataSet.rollup("age", "name").avg().show()`

        +----+-------+--------+
        | age|   name|avg(age)|
        +----+-------+--------+
        |null|   null|    26.0|
        |  29|Michael|    29.0|
        |  19|   null|    19.0|
        |  30|   Andy|    30.0|
        |  30|   null|    30.0|
        |  19| Justin|    19.0|
        |  29|   null|    29.0|
        +----+-------+--------+