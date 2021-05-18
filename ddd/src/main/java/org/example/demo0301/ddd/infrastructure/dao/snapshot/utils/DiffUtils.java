package org.example.demo0301.ddd.infrastructure.dao.snapshot.utils;

import org.example.demo0301.ddd.infrastructure.dao.snapshot.EntityDiff;
import org.example.demo0301.ddd.types.maker.Aggregate;
import org.example.demo0301.ddd.types.maker.Identifier;

public class DiffUtils<T extends Aggregate<ID>, ID extends Identifier> {

    public static <T extends Aggregate<ID>, ID extends Identifier> EntityDiff diff(T snapshot, T aggregate) {
        return EntityDiff.EMPTY;
    }
}
