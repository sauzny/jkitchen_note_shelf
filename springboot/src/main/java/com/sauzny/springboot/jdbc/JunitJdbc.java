package com.sauzny.springboot.jdbc;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sauzny.springboot.BaseJUnit4Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JunitJdbc extends BaseJUnit4Test{
    
    @Autowired
    private StudentService studentService;
    
    @Test
    public void foo01(){
        List<Student> studentList = studentService.getList();
        
        studentList.forEach(student -> log.info(student.toString()) );
    }
}
