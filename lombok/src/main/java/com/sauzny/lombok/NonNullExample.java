package com.sauzny.lombok;

import lombok.NonNull;

public class NonNullExample {
    
    private String name;

    public NonNullExample(@NonNull Student student) {
        this.name = student.getName();
    }
}

class NonNullExample1 {
    
    private String name;  
    
    public NonNullExample1(Student student) {
        if (student == null) {
            throw new NullPointerException("student");
        }
        this.name = student.getName();
    }
}