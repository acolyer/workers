package org.springsource.samples.workers.autoscale;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.AMQP;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.ChannelCallback;

public class QueueMonitor {
	
	public static class QueueStats {
		public QueueStats(int s, int c) {
			this.size = s;
			this.consumers = c;
		}
		public int size;
		public int consumers;
	}
	
	private final String queueName;
	private final RabbitTemplate amqpTemplate;
	
	public QueueMonitor(String queueName, RabbitTemplate template) {
		this.queueName = queueName;
		this.amqpTemplate = template;
	}

	public QueueStats getQueueStatistics() {
	  	 return this.amqpTemplate.execute(new ChannelCallback<QueueStats>() {
			public QueueStats doInRabbit(Channel channel) throws java.io.IOException {
				AMQP.Queue.DeclareOk queueInfo = channel.queueDeclarePassive(queueName);
				return new QueueStats(queueInfo.getMessageCount(),queueInfo.getConsumerCount());				
			}
		});
	}
}
