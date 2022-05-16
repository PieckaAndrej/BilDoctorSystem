package test;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dal.DbConnection;
import dal.ProductDB;
import dal.VehicleDB;
import exceptions.DatabaseAccessException;
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
		
		try {
			vehicleDB.insertVehicle(testVehicle, null);
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
