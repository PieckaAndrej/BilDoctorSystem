package test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dal.VehicleDB;
import exceptions.DatabaseAccessException;
import model.Person;
import model.Vehicle;

class VehicleTest {
	
	private VehicleDB vehicleDB;
	private CleanDatabase vehicleCleaner;
	private Vehicle testVehicle;
	
	@BeforeEach
	void setUp() {
		vehicleDB = new VehicleDB();
		vehicleCleaner = Cleaners.getVehicleCleaner();
		
		try {
			vehicleCleaner.setUp();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		testVehicle = new Vehicle("plate", 2000, "brand");
		Person testCustomer = new Person("Banana", "Joe", "Gutenbergvej 2D", "Sindal",
				"9870", "97845625", "+45");
		
		try {
			vehicleDB.insertVehicle(testVehicle, testCustomer);
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
		}
	}
	
	@AfterEach
	void cleanUp() {
		try {
			vehicleCleaner.retrieveDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void searchVehicleTest() {
		String plateNumber = "plate";
		
		Vehicle vehicle = vehicleDB.searchVehicle(plateNumber);
		
		assertEquals(testVehicle.getPlateNumber(), vehicle.getPlateNumber());
		assertEquals(testVehicle.getYear(), vehicle.getYear());
		assertEquals(testVehicle.getBrand(), vehicle.getBrand());

	}
}
