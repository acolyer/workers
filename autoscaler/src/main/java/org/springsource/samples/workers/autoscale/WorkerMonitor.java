package org.springsource.samples.workers.autoscale;

import org.springsource.samples.workers.autoscale.vcap.ProcessManager;

public class WorkerMonitor {
	
	private final ProcessManager processManager;
	
	public WorkerMonitor(ProcessManager processManager) {
		this.processManager = processManager;
	}
	
	public int getWorkerStatistics() {
		return this.processManager.getNumWorkers();
	}
	
}
