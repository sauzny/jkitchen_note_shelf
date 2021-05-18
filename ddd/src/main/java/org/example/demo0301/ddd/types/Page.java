package org.example.demo0301.ddd.types;

import org.example.demo0301.ddd.domain.acl.cqe.OrderQuery;

import java.util.List;

public class Page<T> {

    public static <T> Page<T> with(List<T> result, OrderQuery query, long count){
        return new Page<T>();
    }
}
