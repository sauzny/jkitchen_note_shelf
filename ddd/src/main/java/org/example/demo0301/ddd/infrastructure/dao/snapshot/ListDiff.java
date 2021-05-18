package org.example.demo0301.ddd.infrastructure.dao.snapshot;

import java.util.ArrayList;

public class ListDiff extends ArrayList<Diff> implements Diff{

    @Override
    public DiffType getType() {
        return DiffType.Removed;
    }

    @Override
    public Object getOldValue() {
        return null;
    }

    @Override
    public Object getNewValue() {
        return null;
    }
}
