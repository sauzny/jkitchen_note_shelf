package org.example.demo0301.ddd.infrastructure.dao.snapshot;

public interface Diff {

    DiffType getType();

    Object getOldValue();

    Object getNewValue();
}
