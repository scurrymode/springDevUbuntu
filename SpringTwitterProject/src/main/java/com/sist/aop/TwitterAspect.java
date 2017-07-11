package com.sist.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sist.main.MainClass;

@Aspect
@Component
public class TwitterAspect {
	@Autowired
	private MainClass mc;
	
	@Before("execution(* com.sist.main.MainClass.hadoopMapReduce())") //이 메서드가 실행하기 전에
	public void before(){
		mc.hadoopFileDelete();
		mc.hadoopFileInput();
	}
	
	@After("execution(* com.sist.main.MainClass.hadoopMapReduce())") //이 메서드가 실행하고 나서
	public void after(){
		mc.hadoopResultSend();
	}
}
