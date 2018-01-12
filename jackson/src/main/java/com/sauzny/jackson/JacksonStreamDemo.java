package com.sauzny.jackson;

import java.io.File;  
import java.io.IOException;  
  
import com.fasterxml.jackson.core.JsonEncoding;  
import com.fasterxml.jackson.core.JsonFactory;  
import com.fasterxml.jackson.core.JsonGenerator;  
import com.fasterxml.jackson.core.JsonParser;  
import com.fasterxml.jackson.core.JsonToken;  
  
public class JacksonStreamDemo {  
  
    public static void main(String[] args) throws IOException {  
        JsonFactory jfactory = new JsonFactory();  
           
        /*** write to file ***/  
        JsonGenerator jGenerator = jfactory.createGenerator(new File(  
                "c:\\user.json"),  JsonEncoding.UTF8);  
        jGenerator.writeStartObject(); // {  
       
        jGenerator.writeStringField("name", "mkyong"); // "name" : "mkyong"  
        jGenerator.writeNumberField("age", 29); // "age" : 29  
       
        jGenerator.writeFieldName("messages"); // "messages" :  
        jGenerator.writeStartArray(); // [  
       
        jGenerator.writeString("msg 1"); // "msg 1"  
        jGenerator.writeString("msg 2"); // "msg 2"  
        jGenerator.writeString("msg 3"); // "msg 3"  
       
        jGenerator.writeEndArray(); // ]  
       
        jGenerator.writeEndObject(); // }  
        jGenerator.close();  
          
        /*** read from file ***/  
        JsonParser jParser = jfactory.createParser(new File("c:\\user.json"));  
        // loop until token equal to "}"  
        while (jParser.nextToken() != JsonToken.END_OBJECT) {  
       
            String fieldname = jParser.getCurrentName();  
            if ("name".equals(fieldname)) {  
       
              // current token is "name",  
              // move to next, which is "name"'s value  
              jParser.nextToken();  
              System.out.println(jParser.getText()); // display mkyong  
       
            }  
       
            if ("age".equals(fieldname)) {  
       
              // current token is "age",   
              // move to next, which is "name"'s value  
              jParser.nextToken();  
              System.out.println(jParser.getIntValue()); // display 29  
       
            }  
       
            if ("messages".equals(fieldname)) {  
       
              jParser.nextToken(); // current token is "[", move next  
       
              // messages is array, loop until token equal to "]"  
              while (jParser.nextToken() != JsonToken.END_ARRAY) {  
       
                         // display msg1, msg2, msg3  
                 System.out.println(jParser.getText());   
       
              }  
       
            }  
       
          }  
          jParser.close();  
  
    }  
  
}  