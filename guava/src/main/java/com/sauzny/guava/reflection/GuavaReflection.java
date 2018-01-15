package com.sauzny.guava.reflection;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.junit.Test;

import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;

public class GuavaReflection {

    public static <K, V> TypeToken<Map<K, V>> mapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
        return new TypeToken<Map<K, V>>() {}
            .where(new TypeParameter<K>() {}, keyToken)
            .where(new TypeParameter<V>() {}, valueToken);
    }
    
    @Test
    public void foo01(){
        TypeToken<String> stringTok = TypeToken.of(String.class);
        TypeToken<Integer> intTok = TypeToken.of(Integer.class);
        TypeToken<List<String>> stringListTok = new TypeToken<List<String>>() {};
        TypeToken<Map<?, ?>> wildMapTok = new TypeToken<Map<?, ?>>() {};
        TypeToken<Map<String, BigInteger>> mapToken = mapToken(
                TypeToken.of(String.class),
                TypeToken.of(BigInteger.class)
            );
            TypeToken<Map<Integer, Queue<String>>> complexToken = mapToken(
               TypeToken.of(Integer.class),
               new TypeToken<Queue<String>>() {}
            );
    }
}

class Action{
    
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void say(){
        System.out.println("hello i'm a actor");
    }
    
    public Action getNewAction(){
        return new Action();
    }
}