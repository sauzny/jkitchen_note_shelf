package com.sauzny.springboot.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
 
@Aspect
@Component 
@Slf4j
public class AopAspect {
    
	/*

	现在来看看几个例子：  
	1）execution(* *(..))  											表示匹配所有方法  
	2）execution(public * com.savage.service.UserService.*(..))  	表示匹配com.savage.server.UserService中所有的公有方法  
	3）execution(* com.savage.server..*.*(..))  						表示匹配com.savage.server包及其子包下的所有方法  

 	Pointcut定义时，还可以使用&&、||、!

    execution(modifiers-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern)throws-pattern?) 
    
    returning type pattern,name pattern, and parameters pattern是必须的. 
    
    ret-type-pattern:可以为*表示任何返回值,全路径的类名等. 
    name-pattern:指定方法名, *代表所有 
    parameters pattern:指定方法参数(声明的类型),(..)代表所有参数,(*)代表一个参数 
    (*,String)代表第一个参数为任何值,第二个为String类型.
	 */
	
	@Pointcut("execution(* com.sauzny.springboot.aop.AopTarget.*(..))")  
	private void anyMethod() {
	}// 定义一个切入点

	@Before("anyMethod() && args(name)")
	public void doAccessCheck(String name) {
		log.info(name);
		log.info("前置通知");
	}

	@AfterReturning("anyMethod()")
	public void doAfter() {
		log.info("后置通知");
	}

	@After("anyMethod()")
	public void after() {
		log.info("最终通知");
	}

	@AfterThrowing("anyMethod()")
	public void doAfterThrow() {
		log.info("例外通知");
	}

	@Around("anyMethod()")
	public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
		log.info("进入环绕通知");
		Object object = pjp.proceed();// 执行该方法
		log.info("退出方法");
		return object;
	}
}
