package controller;

import dal.VehicleDB;
import dal.VehicleDBIF;
import model.Vehicle;

public class VehicleController {
	
	private VehicleDBIF vehicleDB;
	
	public VehicleController() {
		vehicleDB = new VehicleDB();
	}
	
	public Vehicle searchVehicle(String plateNumber) {
		return vehicleDB.searchVehicle(plateNumber);
	}
}
