package controller;

import model.Service;

public class ServiceController {
	
	public ServiceController() {
		
	}
	
	/**
	 * Create new service
	 * @param cost The cost of the service
	 * @param time The time that the service took
	 * @param dsc The description of the service
	 * @return Created service
	 */
	public Service createService(double cost, double time, String dsc) {
		return new Service(cost, time, dsc);
	}
}
