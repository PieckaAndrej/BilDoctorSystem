package controller;

import model.Service;

public class ServiceController {
	
	public ServiceController() {
		
	}
	
	public Service createService(double cost, double time, String dsc) {
		return new Service(cost, time, dsc);
	}
}
