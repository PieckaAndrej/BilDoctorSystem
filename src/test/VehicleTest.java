package test;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dal.PersonDB;
import dal.VehicleDB;
import exceptions.DatabaseAccessException;
import model.Customer;
import model.Person;
import model.Vehicle;

class VehicleTest {
	
	private VehicleDB vehicleDB;
	private CleanDatabase vehicleCleaner;
	private CleanDatabase customerCleaner;
	private CleanDatabase personCleaner;
	private Vehicle testVehicle;
	
	@BeforeEach
	void setUp() {
		vehicleDB = new VehicleDB();
		vehicleCleaner = Cleaners.getVehicleCleaner();
		customerCleaner = Cleaners.getCustomerCleaner();
		personCleaner = Cleaners.getPersonCleaner();
		
		try {
			vehicleCleaner.setUp();
			customerCleaner.setUp();
			personCleaner.setUp();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		testVehicle = new Vehicle("plate", 2000, "brand");
		Customer testCustomer = new Customer("Banana", "Joe", "Gutenbergvej 2D", "Sindal",
				"9870", "97845625", "+45", "VIP");
		
		PersonDB personDb = new PersonDB();
		
		try {
			personDb.insertPerson(testCustomer);
		} catch (DatabaseAccessException e1) {
			e1.printStackTrace();
		}
		
		try {
			vehicleDB.insertVehicle(testVehicle, testCustomer);
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
		}
	}
	
	@AfterEach
	void cleanUp() {
		try {
			personCleaner.retrieveDatabase();
			customerCleaner.retrieveDatabase();
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
