# transformations和actions

Operations available on Datasets are divided into transformations and actions.

Transformations are the ones that produce new Datasets

actions are the ones that trigger computation and return results.

Example transformations include map, filter, select, and aggregate (groupBy).

Example actions count, show, or writing data out to file systems.


## 数据集上的操作分为转换（Transformations）和操作（actions）。

- 转换是生成新数据集的转换。转换包括 地图、过滤、选择、和骨料（分组）。操作是延迟计算的。

- 操作是触发计算和返回结果的操作。操作包括 计数、显示或写入文件系统的数据。会触发 Spark 提交作业（Job），并将数据输出 Spark系统。
