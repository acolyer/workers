package org.springsource.samples.workers.worker;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Worker {

	public static void main(String[] args) {
	  System.out.println("Hello from worker");

	  AbstractApplicationContext integrationContext = 
	    new ClassPathXmlApplicationContext("classpath:META-INF/spring/*-context.xml");
	  integrationContext.registerShutdownHook();
	}
}
