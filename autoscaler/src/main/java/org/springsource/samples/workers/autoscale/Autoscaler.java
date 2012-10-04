package org.springsource.samples.workers.autoscale;

import org.springsource.samples.workers.autoscale.vcap.ProcessManager;

public class Autoscaler {
	
	// Configuration
	private final int maxWorkers;    // max number of worker processes
	private final int minWorkers;    // min number of worker processes
	private final int queueHWM;      // if queue grows above this add workers
	private final int consumerHWM;   // if num available consumers grows above this, remove workers
	private final int workerDelay;   // grace period (seconds) after adding or removing a worker
	private final int windowSize;    // number of consecutive samples above HWM before action is taken
	private final ProcessManager processManager;
	
	// Working state
	private long lastWorkerAction = 0L;
	private int consecutiveQueueHWMSamples = 0;
	private int consecutiveConsumerHWMSamples = 0;
	private int numWorkers;
	
	public Autoscaler(int maxWorkers, int minWorkers,
	                  int queueHWM, int consumerHWM,
	                  int workerDelay, int windowSize,
	                  ProcessManager processManager) {
		this.maxWorkers = maxWorkers;
		this.minWorkers = minWorkers;
		this.queueHWM = queueHWM;
		this.consumerHWM = consumerHWM;
		this.workerDelay = workerDelay;
		this.windowSize = windowSize;
		this.processManager = processManager;
	}
	
	public void onQueueStats(QueueMonitor.QueueStats stats) {
		System.out.println("{ msgs : " + stats.size + " consumers : " + stats.consumers + "}");
		// don't take action on stats within grace period
		if (!inGracePeriod()) {
			sampleQueueSize(stats.size);
			sampleConsumers(stats.consumers);

			if (moreWorkersRequired()) {
				scaleUp();
			}
			if (lessWorkersRequired()) {
				scaleDown();
			}
		}
	}
	
	public void onWorkerStats(int numWorkers) {
		System.out.println("{ workers : " + numWorkers + "}");
		this.numWorkers = numWorkers;
	}
	
	private boolean inGracePeriod() {
		return (System.currentTimeMillis() - lastWorkerAction) < (this.workerDelay * 1000);
	}
	
	private void sampleQueueSize(int msgs) {
		if (msgs > this.queueHWM) {
			this.consecutiveQueueHWMSamples += 1;
		}
		else {
			this.consecutiveQueueHWMSamples = 0;
		}
	}

	private void sampleConsumers(int consumers) {
		if (consumers > this.consumerHWM) {
			this.consecutiveConsumerHWMSamples += 1;
		}
		else {
			this.consecutiveConsumerHWMSamples = 0;
		}
	}
	
	private boolean moreWorkersRequired() {
		return (numWorkers < this.maxWorkers) && 
			   (this.consecutiveQueueHWMSamples > windowSize);
	}
	
	private boolean lessWorkersRequired() {
		return (numWorkers > this.minWorkers) && 
		       (this.consecutiveConsumerHWMSamples > windowSize);
	}
	
	private void scaleUp() {
		this.processManager.addWorkerProcess();
		this.lastWorkerAction = System.currentTimeMillis();
		resetCounts();
	}
	
	private void scaleDown() {
		this.processManager.removeWorkerProcess();
		this.lastWorkerAction = System.currentTimeMillis();
		resetCounts();
	}
	
	private void resetCounts() {
		this.consecutiveQueueHWMSamples = 0;
		this.consecutiveConsumerHWMSamples = 0;
	}
}
