package com.sauzny.springboot.springutil;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.junit.Test;
import org.springframework.util.Base64Utils;

/**
 * *************************************************************************
 * @文件名称: Base64UtilsDemo.java
 *
 * @包路径  : com.sauzny.springboot.springutil 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   Base64
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2018年1月19日 - 下午1:32:37 
 *	
 **************************************************************************
 */
public class Base64UtilsDemo {

    /*  base64 有三种编码
        1）Basic编码 
        2）URL编码 
        3）MIME编码 
     */
    
    @Test
    public void foo01() throws UnsupportedEncodingException{
        
        // java8 实现了三种
        
        // Base64.getEncoder();
        // Base64.getUrlEncoder();
        // Base64.getMimeEncoder();
        
        String str = Base64.getEncoder().encodeToString("testBob".getBytes("utf-8"));
        System.out.println(str);

        byte[] bytes=Base64.getDecoder().decode(str);
        System.out.println(new String(bytes,"utf-8"));
    }
    
    @Test
    public void foo02() throws UnsupportedEncodingException {
        
        // spring util 实现了两种
        
        // Base64Utils.encodeToString(src);
        // Base64Utils.encodeToUrlSafeString(src);
        
        String str = Base64Utils.encodeToString("testBob".getBytes("utf-8"));
        System.out.println(str);

        byte[] bytes=Base64Utils.decodeFromString(str);
        System.out.println(new String(bytes,"utf-8"));
    }
}
