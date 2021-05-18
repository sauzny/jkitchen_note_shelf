package org.example.demo0301.ddd.types.maker;

public interface Identifiable<ID extends Identifier> {
    ID getId();
}
