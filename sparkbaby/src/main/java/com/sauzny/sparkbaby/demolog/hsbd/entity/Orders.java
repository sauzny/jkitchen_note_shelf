package com.sauzny.sparkbaby.demolog.hsbd.entity;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class Orders {
    
    private int id;
    private int productId;
    private String address;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    public static void main(String[] args) {
        for(int i=0;i<24;i++){
            
            int productId = RandomUtils.nextInt(1, 25);
            
            String address1 = RandomStringUtils.randomAlphabetic(3).toUpperCase()+"区";
            String address2 = RandomStringUtils.randomAlphabetic(6).toLowerCase()+"街";
            String address3 = RandomUtils.nextInt(111, 999)+"号";
            
            String address = address1 + "-" + address2 + "-" + address3;
            
            System.out.println(productId+ "\t" + address);
        }
    }
}
