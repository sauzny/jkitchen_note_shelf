package org.example.demo0401.ddd;

import lombok.Value;

@Value
public class Transform {

    public static final Transform ORIGIN = new Transform(0, 0);
    long x;
    long y;
}
