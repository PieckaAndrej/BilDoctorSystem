package dal;

import java.util.ArrayList;

import model.Vehicle;

public interface VehicleDBIF {
	public Vehicle searchVehicle(String plateNumber);
	public ArrayList<Vehicle> getAllVehicles();
}
