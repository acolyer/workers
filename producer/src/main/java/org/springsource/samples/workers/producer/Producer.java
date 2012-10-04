package org.springsource.samples.workers.producer;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.cloudfoundry.runtime.env.CloudEnvironment;

public class Producer {

	public static void main(String[] args) {
	  boolean runningInCloudFoundry = new CloudEnvironment().isCloudFoundry();

	  String runningIn = runningInCloudFoundry ? "in Cloud Foundry" : "Locally";	
	  System.out.println("Hello from producer running " + runningIn);

	  if (runningInCloudFoundry) {
		// activate the cloud profile - we do this explicitly because we are 
		// deploying as a standalone app (not a spring web app)
		System.setProperty("spring.profiles.active","cloud");
	  }
	
	  AbstractApplicationContext integrationContext = 
		new ClassPathXmlApplicationContext("classpath:META-INF/spring/*-context.xml");
	  integrationContext.registerShutdownHook();	
	}


}
