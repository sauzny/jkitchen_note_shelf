package com.sauzny.jackson.abstracttypes;

import com.fasterxml.jackson.annotation.JsonCreator;  
import com.fasterxml.jackson.annotation.JsonProperty;  
  
public class Elephant extends Animal {  
    private String name;  
  
    @JsonCreator  
    public Elephant(@JsonProperty("name") String name) {  
        this.name = name;  
    }  
      
    public String getName() {  
        return name;  
    }  
      
    public String getType() {  
        return "herbivorous";  
    }  
  
    @Override  
    public String toString() {  
        return "Elephant [getName()=" + getName() + ", getType()=" + getType()  
                + "]";  
    }  
      
}  