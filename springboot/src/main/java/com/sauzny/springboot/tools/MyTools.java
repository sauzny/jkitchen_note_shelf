package com.sauzny.springboot.tools;

public final class MyTools {

    private MyTools(){}
    
    public static void sleep(Long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void sleep3s(){
        MyTools.sleep(3000L);
    }
    
    public static void sleep8s(){
        MyTools.sleep(8000L);
    }
}
