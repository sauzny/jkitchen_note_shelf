package org.example.demo0301.ddd.infrastructure.dao.snapshot.utils;

import org.example.demo0301.ddd.types.maker.Aggregate;
import org.example.demo0301.ddd.types.maker.Identifier;

public class ReflectionUtils <T extends Aggregate<ID>, ID extends Identifier> {

    public static <T extends Aggregate<ID>, ID extends Identifier> void writeField(String fieldName, T aggregate, ID id) {

    }
}
