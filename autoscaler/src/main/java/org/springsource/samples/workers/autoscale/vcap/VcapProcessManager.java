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

		// login to Cloud Foundry
		CloudCredentials credentials = new CloudCredentials(username,password);
		this.cfOps = new CloudFoundryClient(credentials,targetUrl);
		this.cfOps.login();

		// ensure the process we are monitoring is started
		CloudApplication app = this.cfOps.getApplication(this.cfAppName);
		if (app.getState() == CloudApplication.AppState.STOPPED) {
			this.cfOps.startApplication(this.cfAppName);
		}
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
	
	public void scaleTo(int numWorkers) {
		this.cfOps.updateApplicationInstances(this.cfAppName,numWorkers);
	}
	
	@PreDestroy
	public void logout() {
		this.cfOps.logout();
	}

}
