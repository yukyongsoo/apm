package com.vaadin.server;

import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;

public class CleanUpServlet extends VaadinServlet{
	 @Override
	    protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration)throws ServiceException {
	        VaadinServletService service = new CleanUpService(this,deploymentConfiguration, getCleanupPollingInterval(),alwaysCheckUITimeOuts());
	        service.init();
	        return service;
	}
	 
	protected int getCleanupPollingInterval() {
		return 60000;
	}

	/**
	 * Should the UI timeouts be checked even if session itself doesn't timeout
	 * yet. Default is {@code false}, override this method to change the value.
	 * <p>
	 * NOTE: updating this value after ServletService has been created won't
	 * have any effect on the functionality.
	 *
	 * @return {@code true} if the UI timeout check is to be performed on every
	 *         cleanup polling, {@code false} otherwise.
	 */
	protected boolean alwaysCheckUITimeOuts() {
		return false;
	}
}
