package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import dal.VehicleDB;
import dal.VehicleDBIF;
import model.Vehicle;

public class VehicleController {
	
	private VehicleDBIF vehicleDB;
	
	public VehicleController() {
		vehicleDB = new VehicleDB();
	}
	
	/**
	 * Search vehicle in the database
	 * @param plateNumber Plate number of the vehicle
	 * @return Found vehicle
	 */
	public Vehicle searchVehicle(String plateNumber) {
		return vehicleDB.searchVehicle(plateNumber);
	}
	
	public ArrayList<Vehicle> getAllVehicles() {
		return vehicleDB.getAllVehiclesWithCheckUpDate(LocalDate.now().plusDays(7));
	}
	
	public void updateCheckUpDate(String plateNumber, LocalDate newCheckUpDate) {
		vehicleDB.changeCheckUpTime(plateNumber, newCheckUpDate);
	}
}
