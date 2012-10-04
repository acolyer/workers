package org.springsource.samples.workers.autoscale.vcap;

public class MockProcessManager implements ProcessManager {
	
	public MockProcessManager() {
	}
	
	public int getNumWorkers() {
		return new java.util.Random().nextInt(5);
	}
	
	public void addWorkerProcess() {
		System.out.println("Add worker please");
	}
	
	public void removeWorkerProcess() {
		System.out.println("Remove worker please");
	}
	
	public void scaleTo(int numWorkers) {
		System.out.println("Scale to " + numWorkers + " please");
	}
	
}
