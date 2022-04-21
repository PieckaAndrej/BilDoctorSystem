package dal;

import model.Vehicle;

public interface VehicleDBIF {
	public Vehicle searchVehicle(String plateNumber);
}
