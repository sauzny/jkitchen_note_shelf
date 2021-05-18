package org.example.demo0301.ddd.infrastructure.converter;

import org.example.demo0301.ddd.domain.entity.LineItem;
import org.example.demo0301.ddd.infrastructure.dao.LineItemDO;

public class LineItemDataConverter {
    public LineItemDO toData(LineItem line) {
        return new LineItemDO();
    }
}
