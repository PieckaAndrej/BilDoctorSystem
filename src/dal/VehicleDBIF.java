package dal;

import java.time.LocalDate;
import java.util.ArrayList;

import model.Vehicle;

public interface VehicleDBIF {
	public Vehicle searchVehicle(String plateNumber);
	public ArrayList<Vehicle> getAllVehiclesWithCheckUpDate(LocalDate date);
	public void changeCheckUpTime(String plateNumber, LocalDate newCheckUpDate);
}
