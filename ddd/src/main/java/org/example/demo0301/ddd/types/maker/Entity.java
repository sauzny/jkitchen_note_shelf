package org.example.demo0301.ddd.types.maker;

// 实体类的Marker接口
public interface Entity<ID extends Identifier> extends Identifiable<ID> {

}
