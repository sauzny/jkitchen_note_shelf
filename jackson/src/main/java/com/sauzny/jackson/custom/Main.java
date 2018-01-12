package com.sauzny.jackson.custom;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sauzny.jackson.ApmOne;

public class Main {

    public static void main(String[] args) {
        
        SimpleModule module = new SimpleModule("myModule"); 
        module.addSerializer(new CustomSerializer(ApmOne.class)); 
        module.addDeserializer(ApmOne.class, new CustomDeserializer(ApmOne.class)); 
        
        // 在mapper中添加使用
        // mapper.registerModule(module); 
        
        
        /*
                        也可通过注解方式加在 java 对象的属性，方法或类上面来调用它们， 
        @JsonSerialize(using = CustomSerializer.class) 
        @JsonDeserialize(using = CustomDeserializer.class) 
        public class ApmOne
        */
    }
}
