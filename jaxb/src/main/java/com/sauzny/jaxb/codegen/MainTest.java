package com.sauzny.jaxb.codegen;

public class MainTest {

    public static void foo01(){
        try {
            Class<?> clazz = Class.forName("com.sauzny.jaxb.codegen.JaxbUtils");
            clazz.getMethod("main", String[].class).invoke(null, new String[1]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MainTest.foo01();
        MainTest.foo01();
    }
}
