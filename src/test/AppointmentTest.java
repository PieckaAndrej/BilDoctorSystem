package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.AppointmentController;
import dal.AppointmentDB;
import dal.DbConnection;
import dal.PersonDB;
import exceptions.DatabaseAccessException;
import exceptions.LengthUnderrunException;
import model.Appointment;
import model.Employee;

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
	void testLength0ShouldThrowException() {
		LocalDateTime date = LocalDateTime.now();
		int length = 0;
		
		assertThrows(LengthUnderrunException.class,
				() -> appointmentController.createAppointment(date, length, ""));
	}
	
	@Test
	void testLengthMinus1ShouldThrowException() {
		LocalDateTime date = LocalDateTime.now();
		int length = -1;
		
		assertThrows(LengthUnderrunException.class,
				() -> appointmentController.createAppointment(date, length, ""));
	}
	
	@Test
	void testCreateAppointmentStart12Length59ShouldBeTrue()
			throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		int length = 60;
		int newLength = 59;
		
		LocalDateTime existingAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime newAppointment = LocalDateTime.of(2000, 1, 1, 12, 0);
		LocalDateTime date = LocalDateTime.now();
		
		Appointment a = new Appointment(existingAppointment, length, "");
		a.setCreationDate(date);
		a.setCustomerName(customerName);
		a.setCustomerPhoneNo(customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertTrue(appointmentController.createAppointment(newAppointment, newLength, ""));
	}
	
	@Test
	void testCreateAppointmentStart12Length60ShouldBeTrue() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		int length = 60;
		int newLength = 60;
		
		LocalDateTime existingAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime newAppointment = LocalDateTime.of(2000, 1, 1, 12, 0);
		LocalDateTime date = LocalDateTime.now();
		
		Appointment a = new Appointment(existingAppointment, length, "");
		a.setCreationDate(date);
		a.setCustomerName(customerName);
		a.setCustomerPhoneNo(customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertTrue(appointmentController.createAppointment(newAppointment, newLength, ""));
	}
	
	@Test
	void testCreateAppointmentStart12Length61ShouldBeFalse() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		int length = 60;
		int newLength = 61;
		
		LocalDateTime existingAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime newAppointment = LocalDateTime.of(2000, 1, 1, 12, 0);
		LocalDateTime date = LocalDateTime.now();
		
		Appointment a = new Appointment(existingAppointment, length, "");
		a.setCreationDate(date);
		a.setCustomerName(customerName);
		a.setCustomerPhoneNo(customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertFalse(appointmentController.createAppointment(newAppointment, newLength, ""));
	}
	
	@Test
	void testCreateAppointmentStart12Length119ShouldBeFalse() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		int length = 60;
		int newLength = 119;
		
		LocalDateTime existingAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime newAppointment = LocalDateTime.of(2000, 1, 1, 12, 0);
		LocalDateTime date = LocalDateTime.now();
		
		Appointment a = new Appointment(existingAppointment, length, "");
		a.setCreationDate(date);
		a.setCustomerName(customerName);
		a.setCustomerPhoneNo(customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertFalse(appointmentController.createAppointment(newAppointment, newLength, ""));
	}
	
	@Test
	void testCreateAppointmentStart12Length120ShouldBeFalse() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		int length = 60;
		int newLength = 120;
		
		LocalDateTime existingAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime newAppointment = LocalDateTime.of(2000, 1, 1, 12, 0);
		LocalDateTime date = LocalDateTime.now();
		
		Appointment a = new Appointment(existingAppointment, length, "");
		a.setCreationDate(date);
		a.setCustomerName(customerName);
		a.setCustomerPhoneNo(customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertFalse(appointmentController.createAppointment(newAppointment, newLength, ""));
	}
	
	@Test
	void testCreateAppointmentStart12Length121ShouldBeFalse() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		int length = 60;
		int newLength = 121;
		
		LocalDateTime existingAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime newAppointment = LocalDateTime.of(2000, 1, 1, 12, 0);
		LocalDateTime date = LocalDateTime.now();
		
		Appointment a = new Appointment(existingAppointment, length, "");
		a.setCreationDate(date);
		a.setCustomerName(customerName);
		a.setCustomerPhoneNo(customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertFalse(appointmentController.createAppointment(newAppointment, newLength, ""));
	}
	
	@Test
	void testCreateAppointmentStart13Length59ShouldBeFalse() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		int length = 60;
		int newLength = 59;
		
		LocalDateTime existingAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime newAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime date = LocalDateTime.now();
		
		Appointment a = new Appointment(existingAppointment, length, "");
		a.setCreationDate(date);
		a.setCustomerName(customerName);
		a.setCustomerPhoneNo(customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertFalse(appointmentController.createAppointment(newAppointment, newLength, ""));
	}
	
	@Test
	void testCreateAppointmentStart13Length60ShouldBeFalse() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		int length = 60;
		int newLength = 60;
		
		LocalDateTime existingAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime newAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime date = LocalDateTime.now();
		
		Appointment a = new Appointment(existingAppointment, length, "");
		a.setCreationDate(date);
		a.setCustomerName(customerName);
		a.setCustomerPhoneNo(customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertFalse(appointmentController.createAppointment(newAppointment, newLength, ""));
	}
	
	@Test
	void testCreateAppointmentStart13Length61ShouldBeFalse() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		int length = 60;
		int newLength = 61;
		
		LocalDateTime existingAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime newAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime date = LocalDateTime.now();
		
		Appointment a = new Appointment(existingAppointment, length, "");
		a.setCreationDate(date);
		a.setCustomerName(customerName);
		a.setCustomerPhoneNo(customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertFalse(appointmentController.createAppointment(newAppointment, newLength, ""));
	}
	
	@Test
	void testCreateAppointmentStart13and59Length1ShouldBeFalse() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		int length = 60;
		int newLength = 1;
		
		LocalDateTime existingAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime newAppointment = LocalDateTime.of(2000, 1, 1, 13, 59);
		LocalDateTime date = LocalDateTime.now();
		
		Appointment a = new Appointment(existingAppointment, length, "");
		a.setCreationDate(date);
		a.setCustomerName(customerName);
		a.setCustomerPhoneNo(customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertFalse(appointmentController.createAppointment(newAppointment, newLength, ""));
	}
	
	@Test
	void testCreateAppointmentStart13and59Length2ShouldBeFalse() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		int length = 60;
		int newLength = 2;
		
		LocalDateTime existingAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime newAppointment = LocalDateTime.of(2000, 1, 1, 13, 59);
		LocalDateTime date = LocalDateTime.now();
		
		Appointment a = new Appointment(existingAppointment, length, "");
		a.setCreationDate(date);
		a.setCustomerName(customerName);
		a.setCustomerPhoneNo(customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertFalse(appointmentController.createAppointment(newAppointment, newLength, ""));
	}
	
	@Test
	void testCreateAppointmentStart14Length1ShouldBeFalse() throws DatabaseAccessException, LengthUnderrunException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		int length = 60;
		int newLength = 1;
		
		LocalDateTime existingAppointment = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime newAppointment = LocalDateTime.of(2000, 1, 1, 14, 0);
		LocalDateTime date = LocalDateTime.now();
		
		Appointment a = new Appointment(existingAppointment, length, "");
		a.setCreationDate(date);
		a.setCustomerName(customerName);
		a.setCustomerPhoneNo(customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertTrue(appointmentController.createAppointment(newAppointment, newLength, ""));
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
