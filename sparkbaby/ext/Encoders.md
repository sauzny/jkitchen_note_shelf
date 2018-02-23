# Encoders
| column | column | column |
|--------|--------|--------|
|`static <T> Encoder<T>` | `bean(Class<T> beanClass) Creates` | An encoder for Java Bean of type T.
|`static Encoder<byte[]>` | `BINARY()` | An encoder for arrays of bytes.
|`static Encoder<Boolean>` | `BOOLEAN()` | An encoder for nullable boolean type.
|`static Encoder<Byte>` | `BYTE()` | An encoder for nullable byte type.
|`static Encoder<java.sql.Date>` | `DATE()` | An encoder for nullable date type.
|`static Encoder<java.math.BigDecimal>` | `DECIMAL()` | An encoder for nullable decimal type.
|`static Encoder<Double>` | `DOUBLE()` | An encoder for nullable double type.
|`static Encoder<Float>` | `FLOAT()` | An encoder for nullable float type.
|`static Encoder<Integer>` | `INT()` | An encoder for nullable int type.
|`static <T> Encoder<T>` | `javaSerialization(Class<T> clazz) Creates` | An encoder that serializes objects of type T using generic Java serialization.
|`static <T> Encoder<T>` | `javaSerialization(scala.reflect.ClassTag<T> evidence$2) (Scala-specific) Creates` | An encoder that serializes objects of type T using generic Java serialization.
|`static <T> Encoder<T>` | `kryo(Class<T> clazz) Creates` | An encoder that serializes objects of type T using Kryo.
|`static <T> Encoder<T>` | `kryo(scala.reflect.ClassTag<T> evidence$1) (Scala-specific) Creates` | An encoder that serializes objects of type T using Kryo.
|`static Encoder<Long>` | `LONG()` | An encoder for nullable long type.
|`static <T extends scala.Product> Encoder<T>` | `product(scala.reflect.api.TypeTags.TypeTag<T> evidence$5)` | An encoder for Scala's product type (tuples, case classes, etc).
|`static Encoder<Object>` | `scalaBoolean()` | An encoder for Scala's primitive boolean type.
|`static Encoder<Object>` | `scalaByte()` | An encoder for Scala's primitive byte type.
|`static Encoder<Object>` | `scalaDouble()` | An encoder for Scala's primitive double type.
|`static Encoder<Object>` | `scalaFloat()` | An encoder for Scala's primitive float type.
|`static Encoder<Object>` | `scalaInt()` | An encoder for Scala's primitive int type.
|`static Encoder<Object>` | `scalaLong()` | An encoder for Scala's primitive long type.
|`static Encoder<Object>` | `scalaShort()` | An encoder for Scala's primitive short type.
|`static Encoder<Short>` | `SHORT()` | An encoder for nullable short type.
|`static Encoder<String>` | `STRING()` | An encoder for nullable string type.
|`static Encoder<java.sql.Timestamp>` | `TIMESTAMP()` | An encoder for nullable timestamp type.
|`static <T1,T2> Encoder<scala.Tuple2<T1,T2>>` | `tuple(Encoder<T1> e1, Encoder<T2> e2)` | An encoder for 2-ary tuples.
|`static <T1,T2,T3> Encoder<scala.Tuple3<T1,T2,T3>>` | `tuple(Encoder<T1> e1, Encoder<T2> e2, Encoder<T3> e3)` | An encoder for 3-ary tuples.
|`static <T1,T2,T3,T4> Encoder<scala.Tuple4<T1,T2,T3,T4>>` | `tuple(Encoder<T1> e1, Encoder<T2> e2, Encoder<T3> e3, Encoder<T4> e4)` | An encoder for 4-ary tuples.
|`static <T1,T2,T3,T4,T5> Encoder<scala.Tuple5<T1,T2,T3,T4,T5>>` | `tuple(Encoder<T1> e1, Encoder<T2> e2, Encoder<T3> e3, Encoder<T4> e4, Encoder<T5> e5)` | An encoder for 5-ary tuples.