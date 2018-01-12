package com.sauzny.jackson.abstracttypes;

import com.fasterxml.jackson.annotation.JsonCreator;  
import com.fasterxml.jackson.annotation.JsonProperty;  
  
public class Lion extends Animal {  
      
    private String name;  
      
    @JsonCreator  
    public Lion(@JsonProperty("name") String name) {  
        this.name = name;  
    }  
  
    public String getName() {  
        return name;  
    }  
      
    public String getType() {  
        return "carnivorous";  
    }  
  
    @Override  
    public String toString() {  
        return "Lion [name=" + name + ", getName()=" + getName()  
                + ", getType()=" + getType() + "]";  
    }  
      
}  
