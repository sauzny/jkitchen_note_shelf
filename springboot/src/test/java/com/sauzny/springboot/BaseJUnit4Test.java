package com.sauzny.springboot;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)//表示整合JUnit4进行测试
@SpringBootTest
@Slf4j
public class BaseJUnit4Test{
    
    // @BeforeClass ==> @Before ==> @Test ==> @After ==> @AfterClass 
    
    /*
        @BeforeClass：针对所有测试，只执行一次，且必须为static void
        @Before：初始化方法
        @Test：测试方法，在这里可以测试期望异常和超时时间
        @Ignore：忽略的测试方法
        @After：释放资源
        @AfterClass：针对所有测试，只执行一次，且必须为static void
     */
    
    @BeforeClass
    public static void beforeClass(){
        log.info("这是测试基础类BaseJUnit4Test中的{}", "beforeClass");
    }
}
