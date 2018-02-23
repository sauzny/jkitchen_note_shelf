package com.sauzny.sparkbaby.demolog;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import org.apache.commons.lang3.RandomUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class Student {

    private String name;
    private int age;
    private String gender;
    private String hometown;
    private String phone;
    private String email;
    private double balance;
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public static Student getOne(){
        
        int gender = RandomPersonUtils.getGender_int();
        
        Student student = new Student();
        student.setAge(RandomUtils.nextInt(15, 55));
        student.setGender(RandomPersonUtils.getGender_zh(gender));
        student.setHometown(RandomPersonUtils.getRoad());
        student.setName(RandomPersonUtils.getChineseName(gender));
        student.setPhone(RandomPersonUtils.getTel());
        student.setEmail(RandomPersonUtils.getEmail(6, 10));
        student.setBalance(RandomUtils.nextDouble(2.5, 8999.99));
        return student;
    }
    
    public static Student fromJson(String json){
        Student student = new Student();
        try {
            student = mapper.readValue(json, Student.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return student;
    }
    
    public String toJSon(){
        String json = "{}";
        try {
            json = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json;
    }
    
    public static void main(String[] args) {
        
        for(int i=0;i<100;i++){
            //System.out.println(Student.getOne().toJSon());
        }
    }
}
