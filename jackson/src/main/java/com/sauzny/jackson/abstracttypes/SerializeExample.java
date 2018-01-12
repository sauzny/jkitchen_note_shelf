package com.sauzny.jackson.abstracttypes;

import java.io.File;  
import java.io.IOException;  
import java.util.ArrayList;  
import java.util.List;  
  
import com.fasterxml.jackson.databind.ObjectMapper;  
import com.fasterxml.jackson.databind.SerializationFeature;  
  
public class SerializeExample {  
    public static void main(String[] args) throws Exception {  
        Zoo zoo = new Zoo("SH Wild Park", "ShangHai");  
        Lion lion = new Lion("Samba");  
        Elephant elephant = new Elephant("Manny");  
        List<Animal> animals = new ArrayList<Animal>();  
        animals.add(lion);  
        animals.add(elephant);  
        zoo.setAnimals(animals);  
          
        ObjectMapper mapper = new ObjectMapper();  
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);  
        mapper.writeValue(new File("zoo.json"), zoo);
        
        System.out.println(mapper.writeValueAsString(zoo));
    }  
  
}  
