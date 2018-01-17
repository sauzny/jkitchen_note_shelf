package com.sauzny.springboot.scheduling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sauzny.springboot.tools.MyTools;

@RunWith(SpringJUnit4ClassRunner.class)//表示整合JUnit4进行测试
@SpringBootApplication
public class JunitSchedule{
    
    @Autowired
    private DynamicScheduling dynamicScheduling;
    
    @Test
    public void SampleScheduling(){
        while(true){
            
        }
    }
    
    @Test
    public void dynamicScheduling(){
        
        
        String futureId = dynamicScheduling.startCron(() -> {System.out.println("dynamicScheduling执行你的业务逻辑代码");}, "*/3 * * * * ?");
        MyTools.sleep8s();
        dynamicScheduling.stopCron(futureId);

        while(true){
            
        }
    }
}
