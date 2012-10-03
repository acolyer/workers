package org.springsource.samples.workers.producer;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Producer {

	public static void main(String[] args) {
	  System.out.println("Hello from producer!");
	
	  AbstractApplicationContext integrationContext = 
		new ClassPathXmlApplicationContext("classpath:META-INF/spring/*-context.xml");
	  integrationContext.registerShutdownHook();	
	}


}
