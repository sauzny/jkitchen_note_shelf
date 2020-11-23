# Reactor

http://htmlpreview.github.io/?https://github.com/get-set/reactor-core/blob/master-zh/src/docs/index.html

## 更多

Reactor中提供了非常丰富的操作符，除了以上几个常见的，还有：
   
- 用于编程方式自定义生成数据流的create和generate等及其变体方法；
- 用于“无副作用的peek”场景的doOnNext、doOnError、doOncomplete、doOnSubscribe、doOnCancel等及其变体方法；
- 用于数据流转换的when、and/or、merge、concat、collect、count、repeat等及其变体方法；
- 用于过滤/拣选的take、first、last、sample、skip、limitRequest等及其变体方法；
- 用于错误处理的timeout、onErrorReturn、onErrorResume、doFinally、retryWhen等及其变体方法；
- 用于分批的window、buffer、group等及其变体方法；
- 用于线程调度的publishOn和subscribeOn方法。

## 错误处理

常见的包括如下几种：

- 捕获并返回一个静态的缺省值。
- 捕获并执行一个异常处理方法或动态计算一个候补值来顶替。
- 捕获，并再包装为某一个 业务相关的异常，然后再抛出业务异常。
- 捕获，记录错误日志，然后继续抛出。
- 使用 finally 来清理资源，或使用 Java 7 引入的 “try-with-resource”。
