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
    public void select(){
        List<Student> studentList = studentService.getList();
        studentList.forEach(student -> log.info(student.toString()) );
    }
    
    @Test
    public void transactional(){
        // 将此方法的事务注解，打开或者关闭，可以看见不一样的效果
        studentService.testTransactional();
    }
}
