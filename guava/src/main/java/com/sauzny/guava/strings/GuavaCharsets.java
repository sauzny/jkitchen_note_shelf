package com.sauzny.guava.strings;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

/**
 * *************************************************************************
 * @文件名称: GuavaCharsets.java
 *
 * @包路径  : com.sauzny.jkitchen_note.guava.strings 
 *				 
 * @版权所有: Personal xinxin (C) 2016
 *
 * @类描述:  字符集
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年8月1日 - 下午5:26:37 
 *	
 **************************************************************************
 */
public class GuavaCharsets {

    @Test
    public void foo01(){
        
        // JDK 1.7 之后  guava推荐使用jdk中的 StandardCharsets
        
        //StandardCharsets.ISO_8859_1;
        //StandardCharsets.US_ASCII;
        //StandardCharsets.UTF_16;
        //StandardCharsets.UTF_16BE;
        //StandardCharsets.UTF_16LE
        //StandardCharsets.UTF_8;
        
        String str = "abc";
        
        @SuppressWarnings("unused")
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        
    }
}
