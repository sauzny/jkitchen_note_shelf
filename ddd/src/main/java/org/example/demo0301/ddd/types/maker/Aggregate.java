package org.example.demo0301.ddd.types.maker;

// 聚合根的Marker接口
public interface Aggregate<ID extends Identifier> extends Entity<ID> {

}
