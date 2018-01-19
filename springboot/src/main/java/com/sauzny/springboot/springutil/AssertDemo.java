package com.sauzny.springboot.springutil;

import java.lang.reflect.Method;

import org.junit.Test;
import org.springframework.util.Assert;

/**
 * *************************************************************************
 * @文件名称: AssertDemo.java
 *
 * @包路径  : com.sauzny.springboot.springutil 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   断言
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2018年1月19日 - 下午1:18:37 
 *	
 **************************************************************************
 */
public class AssertDemo {

    @Test
    public void printAllMethodNames(){
        Method[] methods = Assert.class.getMethods();
        for(Method method : methods){
            System.out.println(method.getName());
        }
        
    }
    
    @SuppressWarnings("deprecation")
    @Test
    public void foo01(){
        // 一般api都有两个，其中一个过期了。
        
        // 过期
        Assert.isTrue("10".equals("10"));
        Assert.isNull(null);
        
        // 推荐使用
        Assert.isTrue("10".equals("10"), "message");
        Assert.isNull("10","message");
    }
    
}
