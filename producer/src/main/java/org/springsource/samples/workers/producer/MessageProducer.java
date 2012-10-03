package org.springsource.samples.workers.producer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.integration.annotation.Publisher;

public class MessageProducer {
	
	private int messageCounter = 0;
	
	public String createWorkPackage() {
		String payload = "This is message #" + ++messageCounter;
		System.out.println(payload);
		return payload;
	}
	
}