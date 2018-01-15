package com.sauzny.guava.strings;

import java.util.List;

import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

/**
 * *************************************************************************
 * @文件名称: GuavaSplitter.java
 *
 * @包路径  : com.sauzny.jkitchen_note.guava.strings 
 *				 
 * @版权所有: Personal xinxin (C) 2016
 *
 * @类描述:  字符串分割器
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年8月1日 - 下午3:27:37 
 *	
 **************************************************************************
 */
public class GuavaSplitter {

    @Test
    public void foo01() {
        // 按单个字符拆分
        Iterable<String> iterable01 = Splitter.on(',').trimResults().omitEmptyStrings().split("foo,bar,,   qux");
        Iterable<String> iterable011 = Splitter.on(',').trimResults().omitEmptyStrings().limit(2).split("foo,bar,,   qux");
        System.out.println(iterable01.toString());
        System.out.println("这种方式下是不是出现了bug：" + iterable011.toString());
        
        // 按字符匹配器拆分
        @SuppressWarnings("unused")
        Iterable<String> iterable02 = Splitter.on(CharMatcher.breakingWhitespace()).trimResults().omitEmptyStrings().split("foo,bar,,   qux");
        
        //按正则表达式拆分
        @SuppressWarnings("unused")
        Iterable<String> iterable03 = Splitter.on("\r?\n").trimResults().omitEmptyStrings().split("foo,bar,,   qux");
        
        //按固定长度拆分；最后一段可能比给定长度短，但不会为空。
        Iterable<String> iterable041 = Splitter.fixedLength(3).split("jsf   hgvsihfshflsdaflishlifjhsligdhu");
        Iterable<String> iterable042 = Splitter.fixedLength(3).trimResults().trimResults(CharMatcher.breakingWhitespace()).omitEmptyStrings().split("jsf   hgvsihfshflsdaflishlifjhsligdhu");
        Iterable<String> iterable043 = Splitter.fixedLength(3).trimResults().trimResults(CharMatcher.breakingWhitespace()).omitEmptyStrings().limit(4).split("jsf   hgvsihfshflsdaflishlifjhsligdhu");
        System.out.println(iterable041.toString());
        System.out.println(iterable042.toString());
        System.out.println(iterable043.toString());
        
        // 返回list
        List<String> list = Lists.newArrayList(Splitter.fixedLength(3).split("jsf   hgvsihfshflsdaflishlifjhsligdhu"));
        System.out.println(list);
        
    }
}
