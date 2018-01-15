package com.sauzny.guava.primitives;

import org.junit.Test;

import com.google.common.primitives.*;

public class Primitives {

    @Test
    public void foo01() {
        Bytes.asList();
        //SignedBytes.asList();
        //UnsignedBytes.asList();
        
        Shorts.asList();
        
        Ints.asList();
        //UnsignedInteger.asList();
        //UnsignedInts.asList();
        
        Longs.asList();
        //UnsignedLong.asList();
        //UnsignedLongs.asList();
        
        Floats.asList();
        
        Doubles.asList();
        
        Chars.asList();
        
        Booleans.asList();
    }
}
