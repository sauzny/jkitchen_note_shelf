package org.example.demo0301.ddd.types;

import lombok.Data;
import org.example.demo0301.ddd.types.maker.Identifier;

@Data
public class StoreId implements Identifier {

    private Long value;
}
