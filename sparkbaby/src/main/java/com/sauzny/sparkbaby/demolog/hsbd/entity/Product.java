package com.sauzny.sparkbaby.demolog.hsbd.entity;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class Product {

    private int id;
    
    private String state;
    
    private int num;
    
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static void main(String[] args) {
        
        for(int i=0;i<24;i++){
            
            String state = RandomStringUtils.randomAlphabetic(1).toUpperCase();
            int sale = RandomUtils.nextInt(10, 100);
            String name = RandomStringUtils.randomAlphabetic(6).toUpperCase();
            
            System.out.println(state + "\t" + sale+ "\t" + name);
        }
    }
}
