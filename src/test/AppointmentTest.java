package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.AppointmentController;
import dal.PersonDB;
import dal.DbConnection;
import exceptions.DatabaseAccessException;
import exceptions.LengthUnderrunException;
import exceptions.OutOfStockException;
import exceptions.QuantityUnderrunException;
import model.Employee;
import model.Person;

public class AppointmentTest {
	
	private AppointmentController appointmentController;
	private PersonDB personDB;
	private Employee testEmployee;
	private DbConnection dbConnection;
	
	private CleanDatabase appointmentCleaner;
	private CleanDatabase personCleaner;
	private CleanDatabase employeeCleaner;
	
	void initCleaners() {
		appointmentCleaner = Cleaners.getAppointmentCleaner();
		
		personCleaner = Cleaners.getPersonCleaner();
		
		employeeCleaner = Cleaners.getEmployeeCleaner();
	}
	
	@BeforeEach
	void setUp() throws DatabaseAccessException {
		initCleaners();
		appointmentController = new AppointmentController();
		personDB = new PersonDB();
		
		try {
			appointmentCleaner.setUp();
			employeeCleaner.setUp();
			personCleaner.setUp();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		testEmployee = new Employee("Mihai", "Mihut", "Gutenbergvej 2D", "Sindal", "9870", "97845625", new BigDecimal(3500));
		
		PersonDB personDb = new PersonDB();
		personDb.insertPerson(testEmployee);
	}
	
	@AfterEach
	void cleanUp() {
		try {
			personCleaner.retrieveDatabase();
			employeeCleaner.retrieveDatabase();
			appointmentCleaner.retrieveDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void scheduleAppointment() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		LocalDateTime date = LocalDateTime.now();
		
		assertEquals(appointmentController.createAppointment(date, 60, "Oil change"), true);
		assertEquals(appointmentController.addCustomerInfo(customerName, customerPhoneNumber), true);
		assertEquals(appointmentController.addEmployee(testEmployee), true);	
		assertEquals(appointmentController.finishAppointment(), true);
		
	}
	
	@Test
	void incorrectLengthValue() {
		LocalDateTime date = LocalDateTime.now();
		
		assertThrows(LengthUnderrunException.class,
				() -> appointmentController.createAppointment(date, -60, "Oil change"));
	}
	
	@Test
	void overlappingAppointments() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		LocalDateTime date = LocalDateTime.now();
		
		assertEquals(appointmentController.createAppointment(date, 60, "Oil change"), true);
		assertEquals(appointmentController.addCustomerInfo(customerName, customerPhoneNumber), true);
		assertEquals(appointmentController.addEmployee(testEmployee), true);	
		assertEquals(appointmentController.finishAppointment(), true);
		assertEquals(appointmentController.createAppointment(date, 120, "Oil change"), false);
	}
	
	@Test
	void disconnectedDatabase() throws DatabaseAccessException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		LocalDateTime date = LocalDateTime.now();
		
		try {
			assertEquals(appointmentController.createAppointment(date, 60, "Oil change"), true);
		} catch (DatabaseAccessException | LengthUnderrunException e) {
			e.printStackTrace();
		}
		assertEquals(appointmentController.addCustomerInfo(customerName, customerPhoneNumber), true);
		Connection conn = dbConnection.getInstance().getConnection();
		/*try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		assertThrows(DatabaseAccessException.class,
				() -> appointmentController.addEmployee(testEmployee));
	}
	
	@Test
	void incorrectEmployee() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		LocalDateTime date = LocalDateTime.now();
		
		Employee employee = new Employee("Banana", "Joe", "Gutenbergvej 2D", "Sindal", "9870", "97845625", new BigDecimal(3500));
		
		assertEquals(appointmentController.createAppointment(date, 60, "Oil change"), true);
		assertEquals(appointmentController.addCustomerInfo(customerName, customerPhoneNumber), true);
		assertEquals(appointmentController.addEmployee(employee), false);	
	}
	
	@Test
	void employeeCancelsAppointment() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		LocalDateTime date = LocalDateTime.now();
		
		assertEquals(appointmentController.createAppointment(date, 60, "Oil change"), true);
		assertEquals(appointmentController.addCustomerInfo(customerName, customerPhoneNumber), true);
		assertEquals(appointmentController.addEmployee(testEmployee), true);
		appointmentController.cancelAppointment();
		assertEquals(appointmentController.finishAppointment(), false);
	}

}
