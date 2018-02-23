# 日志log4j变logbak

## 方法

- 1.将jars文件夹下
    - `apache-log4j-extras-1.2.17.jar`
    - `commons-logging-1.1.3.jar`
    - `log4j-1.2.17.jar`
    - `slf4j-log4j12-1.7.16.jar`

  替换成

  - `log4j-over-slf4j-1.7.23.jar`
  - `logback-access-1.2.1.jar`
  - `logback-classic-1.2.1.jar`
  - `logback-core-1.2.1.jar`

- 2.将`conf`文件夹下的`log4j.properties.template`通过 [log4j.properties Translator](https://logback.qos.ch/translator/) 转换成 `logback.xml`即可，将`logback.xml`放在文件夹`conf`中。

ps：理论上应该可行，但是我没有测试过。

## 其他

我想换logback的目的是想制作一些测试使用的日志文件，所以我替换了pom中的jar包。

说明的是，本身代码（pom）中的的jar包和实际被`spark submit`运行起来使用的jar包，并不是一个环境。