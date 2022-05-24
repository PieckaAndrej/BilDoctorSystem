package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dal.DbConnection;
import dal.PersonDB;
import dal.VehicleDB;
import exceptions.DatabaseAccessException;
import model.Customer;
import model.Vehicle;

class VehicleTest {
	
	private VehicleDB vehicleDB;
	private CleanDatabase vehicleCleaner;
	private CleanDatabase customerCleaner;
	private CleanDatabase personCleaner;
	private CleanDatabase employeeCleaner;
	private CleanDatabase orderLineCleaner;
	private CleanDatabase saleCleaner;
	private CleanDatabase serviceCleaner;
	private CleanDatabase appointmentCleaner;
	
	private Vehicle testVehicle;
	private Customer testCustomer;
	
	@BeforeEach
	void setUp() {
		vehicleDB = new VehicleDB();
		vehicleCleaner = Cleaners.getVehicleCleaner();
		customerCleaner = Cleaners.getCustomerCleaner();
		personCleaner = Cleaners.getPersonCleaner();
		employeeCleaner = Cleaners.getEmployeeCleaner();
		orderLineCleaner = Cleaners.getOrderLineCleaner();
		saleCleaner = Cleaners.getSaleCleaner();
		serviceCleaner = Cleaners.getServiceCleaner();
		appointmentCleaner = Cleaners.getAppointmentCleaner();
		
		try {
			serviceCleaner.setUp();
			orderLineCleaner.setUp();
			saleCleaner.setUp();
			appointmentCleaner.setUp();
			employeeCleaner.setUp();
			vehicleCleaner.setUp();
			customerCleaner.setUp();
			personCleaner.setUp();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		testCustomer = new Customer("Banana", "Joe", "Gutenbergvej 2D", "Sindal",
				"9870", "97845625", "+45", "VIP");
		
		testVehicle = new Vehicle("plate", 2000, "brand",
				LocalDate.of(2000, 1, 1), testCustomer);
		
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
			employeeCleaner.retrieveDatabase();
			appointmentCleaner.retrieveDatabase();
			customerCleaner.retrieveDatabase();
			vehicleCleaner.retrieveDatabase();
			saleCleaner.retrieveDatabase();
			serviceCleaner.retrieveDatabase();
			orderLineCleaner.retrieveDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void searchVehicleTest() {
		String plateNumber = testVehicle.getPlateNumber();
		
		Vehicle vehicle = vehicleDB.searchVehicle(plateNumber);
		
		assertEquals(testVehicle.getPlateNumber(), vehicle.getPlateNumber());
		assertEquals(testVehicle.getYear(), vehicle.getYear());
		assertEquals(testVehicle.getBrand(), vehicle.getBrand());
		assertEquals(testVehicle.getCheckUpDate(), vehicle.getCheckUpDate());
		assertEquals(testVehicle.getOwner().getPhoneNumber(),
				vehicle.getOwner().getPhoneNumber());

	}
	
	@Test
	void updateCheckUpTest() {
		LocalDate newCheckUpDate = testVehicle.getCheckUpDate().plusDays(30);
		LocalDate retrievedCheckUpDate = null;
		
		vehicleDB.changeCheckUpTime(testVehicle.getPlateNumber(), newCheckUpDate);
		
		try {
			ResultSet rs = DbConnection.getInstance().getConnection()
					.prepareStatement("SELECT * FROM Vehicle").executeQuery();
			
			if (rs.next()) {
				retrievedCheckUpDate = rs.getDate("checkUpDate").toLocalDate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertEquals(newCheckUpDate, retrievedCheckUpDate);
	}
	
	@Test
	void getAllVehiclesTest() {
		LocalDate date = LocalDate.of(2000, 1, 2);
		
		Vehicle v1 = new Vehicle("plate1", 2000, "brand",
				LocalDate.of(2000, 1, 2), testCustomer);
		Vehicle v2 = new Vehicle("plate2", 2000, "brand",
				LocalDate.of(2000, 1, 3), testCustomer);
		
		try {
			vehicleDB.insertVehicle(v1, testCustomer);
			vehicleDB.insertVehicle(v2, testCustomer);
			
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
		}
		
		List<String> plates = vehicleDB.getAllVehiclesWithCheckUpDate(date)
				.stream().map(Vehicle::getPlateNumber).toList();
		
		assertTrue(plates.contains(testVehicle.getPlateNumber()));
		assertTrue(plates.contains(v1.getPlateNumber()));
		assertFalse(plates.contains(v2.getPlateNumber()));
	}
}
