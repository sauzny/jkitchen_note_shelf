package com.sauzny.jackson.include;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sauzny.jackson.entity.ApmOne;

/**
 * *************************************************************************
 * @文件名称: IncludeTest.java
 *
 * @包路径  : com.sauzny.jackson.include 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   对于某些类型转换json时的过滤
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2018年1月12日 - 上午9:53:31 
 *	
 **************************************************************************
 */
public class IncludeTest {

    public static void main(String[] args) throws IOException {
        ApmOne apmOne = new ApmOne();
        apmOne.setName("");
        // 可以理解 对 
        // 空
        // null
        // 0 
        // 的过滤
        for(Include include : Include.values()){
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(include);
            String json = mapper.writeValueAsString(apmOne);
            System.out.println(include +"\t\t" + json);
            System.out.println(apmOne);
            
            // 测试 readerForUpdating方法
            ObjectMapper mapper1 = new ObjectMapper();
            mapper1.setSerializationInclusion(include);
            String updateJson = "{\"name\":\"aaaaaa\"}";
            mapper1.readerForUpdating(apmOne).readValue(updateJson);
            System.out.println(include +"\t\t" + json);
        }
    }
}
