package com.sauzny.springboot.springutil;

import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.springframework.util.DigestUtils;

/**
 * *************************************************************************
 * @文件名称: DigestUtilsDemo.java
 *
 * @包路径  : com.sauzny.springboot.springutil 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   摘要工具类
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2018年1月19日 - 下午1:46:03 
 *	
 **************************************************************************
 */
public class DigestUtilsDemo {

    @Test
    public void foo01(){
        
        // 只是实现了md5这种摘要算法
        // 第二三种API比较常用
        
        byte[] bytes = "摘要算法".getBytes(StandardCharsets.UTF_8);
        
        byte[] md5Digest = DigestUtils.md5Digest(bytes);
        String md5AsHex = DigestUtils.md5DigestAsHex(bytes);
        StringBuilder appendMd5DigestAsHex = DigestUtils.appendMd5DigestAsHex(bytes, new StringBuilder("builder"));

        System.out.println(new String(md5Digest, StandardCharsets.UTF_8));
        System.out.println(md5AsHex);
        System.out.println(appendMd5DigestAsHex.toString());
    }
}
