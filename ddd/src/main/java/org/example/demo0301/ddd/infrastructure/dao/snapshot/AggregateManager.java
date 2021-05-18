package org.example.demo0301.ddd.infrastructure.dao.snapshot;

import org.example.demo0301.ddd.types.maker.Aggregate;
import org.example.demo0301.ddd.types.maker.Identifier;

public interface AggregateManager<T extends Aggregate<ID>, ID extends Identifier> {

    static <T> AggregateManager newInstance(Class<T> targetClass) {
        return new ThreadLocalAggregateManager(targetClass);
    }

    void attach(T aggregate);

    void attach(T aggregate, ID id);

    void detach(T aggregate);

    EntityDiff detectChanges(T aggregate);

    void merge(T aggregate);

    T find(ID id);
}
