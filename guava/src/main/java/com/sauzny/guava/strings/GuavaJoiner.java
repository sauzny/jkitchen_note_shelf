package com.sauzny.guava.strings;

import java.util.Arrays;

import org.junit.Test;

import com.google.common.base.Joiner;

/**
 * *************************************************************************
 * @文件名称: GuavaJoiner.java
 *
 * @包路径  : com.sauzny.jkitchen_note.guava.strings 
 *				 
 * @版权所有: Personal xinxin (C) 2016
 *
 * @类描述:  字符串连接器
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年8月1日 - 下午3:27:21 
 *	
 **************************************************************************
 */
public class GuavaJoiner {

    @Test
    public void foo01(){
        Joiner joiner = Joiner.on("; ").skipNulls();
        String foo01 = joiner.join("Harry", null, "Ron", "Hermione");
        System.out.println("foo01：" + foo01);
        
        String foo02 = Joiner.on(",").join(Arrays.asList(1, 5, 7));
        System.out.println("foo02：" + foo02);
    }
}
