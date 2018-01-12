package com.sauzny.jackson;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;

/**
 * *************************************************************************
 * @文件名称: JacksonTools.java
 *
 * @包路径  : com.sauzny.jackson 
 *				 
 * @版权所有: Personal xinxin (C) 2017
 *
 * @类描述:   jackson实现的json工具类
 * 
 * @创建人:   ljx 
 *
 * @创建时间: 2018年1月12日 - 上午10:58:53 
 *	
 **************************************************************************
 */
public final class JacksonTools {

    private static Logger logger = LoggerFactory.getLogger(JacksonTools.class);

    private static ObjectMapper mapper;

    // 获取mapper
    public ObjectMapper getMapper() {
        return mapper;
    }
    
    // 初始化 ObjectMapper
    public JacksonTools(){
        this(null);
    }
    
    public JacksonTools(Include include) {
        
        // findAndRegisterModules() 注册所有的modules
        mapper = new ObjectMapper().findAndRegisterModules();
        
        //设置序列化时日期格式，默认不是时间戳，默认为 yyyy-MM-dd'T'HH:mm:ss.SSSZ 
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        
        //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        //设置输出时包含属性的风格
        if (include != null) {
            mapper.setSerializationInclusion(include);
        }
    }
    
    /* 使用内部静态类单例模式提供三种序列化风格 -- 开始 */
    private static class NonNull {  
        private static JacksonTools instance;  
        static {  
            // 序列化时 忽略 null
            instance = new JacksonTools(Include.NON_NULL);  
        }  
    }  
    
    private static class NonEmpty {  
        private static JacksonTools instance;  
        static {  
            // 序列化时 忽略 null 空字符串
            instance = new JacksonTools(Include.NON_EMPTY);  
        }  
    }
    
    private static class NonDefault {  
        private static JacksonTools instance;  
        static {  
            // 序列化时 忽略 null 空字符串 0
            instance = new JacksonTools(Include.NON_DEFAULT);  
        }  
    }

    // 序列化时 忽略 null
    public static JacksonTools nonNull() {
        return NonEmpty.instance;
    }

    // 序列化时 忽略 null 空字符串
    public static JacksonTools nonEmpty() {
        return NonNull.instance;
    }

    // 序列化时 忽略 null 空字符串 0
    public static JacksonTools nonDefault() {
        return NonDefault.instance;
    }
    /* 使用内部静态类单例模式提供三种序列化风格 -- 结束 */
    
    /**
     * @描述: 序列化，Object -> json字符串
     * @param object
     * @return
     * @返回 String
     * @创建人  ljx 创建时间 2018年1月12日 上午10:30:08
     */
    public String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            logger.warn("write to json string error:" + object, e);
            throw new RuntimeException(e);
        }
    }
    

    /**
     * @描述: 反序列化，一般POJO，json字符串 -> Object
     * @param jsonString
     * @param clazz
     * @return
     * @返回 T
     * @创建人  ljx 创建时间 2018年1月12日 上午10:31:39
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }
    
    /**
     * @描述: 反序列化，Collection<POJO>，json字符串 -> Object
     * @param jsonString
     * @param collectionClass
     * @param elementClasses
     * @return
     * @返回 T
     * @创建人  ljx 创建时间 2018年1月12日 上午10:40:50
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJson(String jsonString, Class<?> collectionClass, Class<?>... elementClasses) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }

        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }
    
    /**
     * @描述: 修改object中的部分值，jsonString包含object的部分属性时，使用jsonString替换object中的属性值，只能覆盖部分属性
     * @param jsonString
     * @param object
     * @return
     * @返回 T
     * @创建人  ljx 创建时间 2018年1月12日 上午10:53:02
     */
    @SuppressWarnings("unchecked")
    public <T> T update(String jsonString, T object) {
        try {
            return (T) mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        } catch (IOException e) {
            logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
        }
        return null;
    }
    
    /**
     * @描述: 返回jsonp格式字符串
     * @param functionName
     * @param object
     * @return
     * @返回 String
     * @创建人  ljx 创建时间 2018年1月12日 上午10:56:12
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }
    
    public static void main(String[] args) {
        
    }
}
