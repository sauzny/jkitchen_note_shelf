package com.sauzny.springboot.scheduling;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sauzny.springboot.BaseJUnit4Test;
import com.sauzny.springboot.tools.MyTools;

public class JunitSchedule extends BaseJUnit4Test{
    
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
