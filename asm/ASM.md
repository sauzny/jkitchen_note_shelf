# ASM框架

ASM是一款基于java字节码层面的代码分析和修改工具。

无需提供源代码即可对应用嵌入所需debug代码，用于应用API性能分析。

ASM可以直接产生二进制class文件，也可以在类被加入JVM之前动态修改类行为。


## 一、class文件结构

在了解ASM之前，有必要先了解一下class文件结构。

对于每个class文件其实都是有固定的结构信息，而且保留了源码文件中的符号。

下图是class文件的格式图。其中带 * 号的表示可重复的结构。

- Header and Constant Stack
- [*]Class Attributes
- [*]Fields
  - Field Name，Descriptor，etc
  - Field Attributes
- [*]Methods
  - Method Name，Descriptor，etc
  - Method max stack and locals
  - [*]Method Code table
  - [*]Method Exception table
  - [*]Method Code Attributes
  - [*]Method Attributes

- 类结构体中所有的修饰符、字符常量和其他常量都被存储在class文件开始的一个常量堆栈(Constant Stack)中，其他结构体通过索引引用。
- 每个类必须包含headers（包括：class name, super class, interface, etc.）和常量堆栈（Constant Stack）其他元素，例如：字段（fields）、方法（methods）和全部属性（attributes）可以选择显示或者不显示。
- 每个字段块（Field section）包括名称、修饰符（public, private, etc.）、描述符号(descriptor)和字段属性。
- 每个方法区域（Method section）里面的信息与header部分的信息类似，信息关于最大堆栈（max stack）和最大本地变量数量（max local variable numbers）被用于修改字节码。对于非abstract和非native的方法有一个方法指令表，exceptions表和代码属性表。除此之外，还可以有其他方法属性。
- 每个类、字段、方法和方法代码的属性有属于自己的名称记录在类文件格式的JVM规范的部分，这些属性展示了字节码多方面的信息，例如源文件名、内部类、签名、代码行数、本地变量表和注释。JVM规范允许定义自定义属性，这些属性会被标准的VM（虚拟机）忽略，但是可以包含附件信息。
- 方法代码表包含一系列对java虚拟机的指令。有些指令在代码中使用偏移量，当指令从方法代码被插入或者移除时，全部偏移量的值可能需要调整。


## 二、ASM库的结构

### 2.1 介绍

- Core 为其他包提供基础的读、写、转化Java字节码和定义的API，并且可以生成Java字节码和实现大部分字节码的转换
- Tree提供了Java字节码在内存中的表现
- Analysis为存储在tree包结构中的java方法字节码提供基本的数据流统计和类型检查算法
- Commons提供一些常用的简化字节码生成转化和适配器
- Util包含一些帮助类和简单的字节码修改，有利于在开发或者测试中使用
- XML提供一个适配器将XML和SAX-comliant转化成字节码结构，可以允许使用XSLT去定义字节码转化。

在Core包中逻辑上分为2部分：

- 字节码生产者，例如ClassReader
- 字节码消费者，例如writers（ClassWriter, FieldWriter, MethodWriter和AnnotationWriter），adapters（ClassAdapter和MethodAdapter）

### 2.2 基本类型

java类型 | type | 含义
--- | --- | ---
boolean | Z | 布尔
char | C | 字符
byte | B | 字节
short | S | 短整型
int | I | 整型
long | J | 长整型
float | F | 浮点数
reference | L | 类的引用
void | V | 空
double | D | 双精度浮点型
Object | Ljava/lang/Object; | 对象
int[] | [I | 整型数组
Object[][] | [[Ljava/lang/Object; | 对象数组

`注： L+className;代表某类的引用(";"不能省略)`

### 2.3 字节码实例:

Java代码 | 字节码表示 | 注释
--- | --- | ---
double[][] | [[D | -
Object run(int i,double d,Thread t) | (IDLjava/lang/Thread)Ljava/lang/Object; | (方法参数字节码类型)方法返回参数类型
