package com.sauzny.springboot.aop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sauzny.springboot.aop.AopTarget;


/*
AOP

配置文件中设置开关

如果需要CGLIB代理的话，还需要自己增加CGLIB的jar包

*/

@RunWith(SpringJUnit4ClassRunner.class)//表示整合JUnit4进行测试
@SpringBootApplication
public class JUnitAop{

	@Autowired
	private AopTarget aopTarget;
	
	@Test
	public void testint(){
		aopTarget.say();
	}
}
