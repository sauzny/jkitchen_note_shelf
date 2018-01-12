package com.sauzny.jackson.anyothers;

import java.util.HashMap;  
import java.util.Map;  
  
import com.fasterxml.jackson.annotation.JsonAnyGetter;  
import com.fasterxml.jackson.annotation.JsonAnySetter;  
import com.fasterxml.jackson.annotation.JsonCreator;  
import com.fasterxml.jackson.annotation.JsonProperty;  
  
public class Department {  
    private String name;  
    private String pm;  
    private Map<String, Object> otherProperties = new HashMap<String, Object>(); //otherProperties用来存放Department中未定义的json字段  
      
    @JsonCreator   //指定json反序列化创建Department对象时调用此构造函数  
    public Department(@JsonProperty("name") String name){  
        this.name = name;  
    }  
      
    @JsonProperty("projectManager")  //将company.json中projectManager字段关联到getPm方法  
    public String getPm() {  
        return pm;  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public Object get(String key) {  
        return otherProperties.get(key);  
    }  
  
    @JsonAnyGetter    //得到所有Department中未定义的json字段的  
    public Map<String, Object> any() {  
        return otherProperties;  
    }  
  
    @JsonAnySetter  
    public void set(String key, Object value) {  
        otherProperties.put(key, value);  
    }  
  
}  
