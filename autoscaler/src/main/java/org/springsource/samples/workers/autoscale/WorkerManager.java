package org.springsource.samples.workers.autoscale;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WorkerManager {

	public static void main(String[] args) {
		System.out.println("Worker Manager process active");
		
		AbstractApplicationContext integrationContext = 
			new ClassPathXmlApplicationContext("classpath:META-INF/spring/*-context.xml");
	  	integrationContext.registerShutdownHook();
	}

}
