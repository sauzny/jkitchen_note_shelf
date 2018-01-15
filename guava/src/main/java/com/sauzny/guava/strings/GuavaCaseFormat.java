package com.sauzny.guava.strings;

import org.junit.Test;

import com.google.common.base.CaseFormat;

/**
 * *************************************************************************
 * @文件名称: GuavaCaseFormat.java
 *
 * @包路径  : com.sauzny.jkitchen_note.guava.strings 
 *				 
 * @版权所有: Personal xinxin (C) 2016
 *
 * @类描述:  大小写格式
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2017年8月1日 - 下午5:37:01 
 *	
 **************************************************************************
 */
public class GuavaCaseFormat {

    @Test
    public void foo1(){
        
        
        /*
        LOWER_CAMEL lowerCamel
        LOWER_HYPHEN    lower-hyphen
        LOWER_UNDERSCORE    lower_underscore
        UPPER_CAMEL UpperCamel
        UPPER_UNDERSCORE    UPPER_UNDERSCORE
         */
        
        //String source_lowerCamel = "lowerCamel";
        //String source_lowerHENXIANhyphen = "lower-hyphen";
        //String source_lower_underscore = "lower_underscore";
        //String source_UpperCamel = "UpperCamel";
        String source_UPPER_UNDERSCORE = "UPPER_UNDERSCORE";
        
        String target_LOWER_CAMEL = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, source_UPPER_UNDERSCORE);

        System.out.println("源格式.to(目标格式，源字符串)");
        System.out.println("CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, source_UPPER_UNDERSCORE)：" + source_UPPER_UNDERSCORE + " -> " + target_LOWER_CAMEL);
        
    }
}
