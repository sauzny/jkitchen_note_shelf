package com.sauzny.jackson.abstracttypes;

import com.fasterxml.jackson.annotation.JsonSubTypes;  
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;  
import com.fasterxml.jackson.annotation.JsonTypeInfo;  
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;  
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;  
  
@JsonTypeInfo(use=Id.CLASS,include=As.PROPERTY,property="@class")  
@JsonSubTypes({@Type(value=Lion.class,name="lion"),@Type(value=Elephant.class,name="elephant")})  
public abstract class Animal {  
    String name;  
    String type;  
}  
