package com.sauzny.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JacksonTreeDemo {

    public static void foo01() throws IOException{
        String jsonstr = "{\"msg\":{\"head\":{\"version\":\"1.0\",\"bizcode\":\"1006\",\"senddate\":\"20140827\",\"sendtime\":\"110325\",\"seqid\":\"1\"},\"body\":{\"datalist\":\"wahaha\",\"rstcode\":\"000000\",\"rstmsg\":\"成功\"}}}";
        ObjectMapper mapper = new ObjectMapper(); 
         
        //允许出现特殊字符和转义符
        mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true) ;
        JsonNode root = mapper.readTree(jsonstr);
        //path与get作用相同,但是当找不到该节点的时候,返回missing node而不是Null. 
        JsonNode msg = root.path("msg"); 
        JsonNode head = msg.path("head"); 
        JsonNode body = msg.path("body"); 
        
        String bizcode = head.path("bizcode").asText(); 
        String datalist = body.path("datalist").asText(); 
         
        System.err.println(bizcode);
        System.err.println(datalist);
         
        System.err.println(root.path("msg").path("body").path("datalist").asText());
        
        //JsonNode 转换成 java 对象 
        // Person person = mapper.treeToValue(personNode, Person.class); 
        //java 对象转换成 JsonNode 
        // JsonNode node = mapper.valueToTree(person);
    }

    public static void foo02(){
        try {
            ObjectMapper mapper = new ObjectMapper();
 
            // 允许出现特殊字符和转义符
            mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
 
            // String jsonstr =
            // "{\"msg\":{\"head\":{\"version\":\"1.0\",\"bizcode\":\"1006\",\"senddate\":\"20140827\",\"sendtime\":\"110325\",\"seqid\":\"1\"},\"body\":{\"datalist\":\"wahaha\",\"rstcode\":\"000000\",\"rstmsg\":\"成功\"}}}";
 
            ObjectNode root = mapper.createObjectNode();
            ObjectNode msg = mapper.createObjectNode();
 
            ObjectNode head = mapper.createObjectNode();
            head.put("version", "1.0");
            head.put("bizcode", "1006");
            head.put("senddate", "20140827");
            head.put("sendtime", "110325");
            head.put("seqid", "1");
 
            ObjectNode body = mapper.createObjectNode();
            body.put("datalist", "wahaha");
            body.put("rstcode", "000000");
            body.put("rstmsg", "成功");
 
            msg.set("head", head);
            msg.set("body", body);
            root.set("msg", msg);
 
            System.out.println(mapper.writeValueAsString(root));
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) throws IOException {
        JacksonTreeDemo.foo01();
        JacksonTreeDemo.foo02();
    }
}

