package org.example.demo0301.ddd.infrastructure.dao.snapshot;

public class EntityDiff {

    public static final EntityDiff EMPTY = new EntityDiff();

    public boolean isEmpty(){
        return Boolean.FALSE;
    }

    public boolean isSelfModified() {
        return Boolean.FALSE;
    }

    public Diff getDiff(String name){
        return new ListDiff();
    }
}
