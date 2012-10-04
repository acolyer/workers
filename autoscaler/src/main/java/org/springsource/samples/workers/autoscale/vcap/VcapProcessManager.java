package org.springsource.samples.workers.autoscale.vcap;

import java.net.URL;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.CloudFoundryOperations;
import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import javax.annotation.PreDestroy;

public class VcapProcessManager implements ProcessManager {
	
	private final String cfAppName;
	private final CloudFoundryOperations cfOps;
	
	public VcapProcessManager(String appName, URL targetUrl, String username, String password) {
		System.out.println("VcapProcessManager connecting to " + targetUrl + " as " + username);
		this.cfAppName = appName;
		CloudCredentials credentials = new CloudCredentials(username,password);
		this.cfOps = new CloudFoundryClient(credentials,targetUrl);
		this.cfOps.login();
	}
	
	public int getNumWorkers() {
		return this.cfOps.getApplication(this.cfAppName).getRunningInstances();
	}
	
	public void addWorkerProcess() {
		int currentWorkers = getNumWorkers();
		this.cfOps.updateApplicationInstances(this.cfAppName,currentWorkers + 1);
	}
	
	public void removeWorkerProcess() {
		int currentWorkers = getNumWorkers();
		if (currentWorkers > 0) {
			this.cfOps.updateApplicationInstances(this.cfAppName,currentWorkers - 1);
		}
	}
	
	@PreDestroy
	public void logout() {
		this.cfOps.logout();
	}

}
