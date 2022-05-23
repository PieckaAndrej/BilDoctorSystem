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
	
	/**
	 * Get all vehicles from the database with check up date before
	 * the current day plus 7 days
	 * @return ArrayList of vehicles with check up date earlier than next week
	 */
	public ArrayList<Vehicle> getAllVehicles() {
		return vehicleDB.getAllVehiclesWithCheckUpDate(LocalDate.now().plusDays(7));
	}
	
	/**
	 * Update check up date of the vehicle
	 * @param plateNumber Plate number of the vehicle to be updated
	 * @param newCheckUpDate New check up date of the vehicle
	 */
	public void updateCheckUpDate(String plateNumber, LocalDate newCheckUpDate) {
		vehicleDB.changeCheckUpTime(plateNumber, newCheckUpDate);
	}
}
