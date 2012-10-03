package org.springsource.samples.workers.autoscale.vcap;

public interface ProcessManager {
	
	public int getNumWorkers();

	public void addWorkerProcess();

	public void removeWorkerProcess();
	
}