package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

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
		
		testEmployee = new Employee("Mihai", "Mihut", "Gutenbergvej 2D", "Sindal",
				"9870", "97845625", "+45", new BigDecimal(3500), "123", "manager");
		
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
		a.addCustomerInfo(customerName, customerPhoneNumber);
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
		a.addCustomerInfo(customerName, customerPhoneNumber);
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
		a.addCustomerInfo(customerName, customerPhoneNumber);
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
		a.addCustomerInfo(customerName, customerPhoneNumber);
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
		a.addCustomerInfo(customerName, customerPhoneNumber);
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
		a.addCustomerInfo(customerName, customerPhoneNumber);
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
		a.addCustomerInfo(customerName, customerPhoneNumber);
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
		a.addCustomerInfo(customerName, customerPhoneNumber);
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
		a.addCustomerInfo(customerName, customerPhoneNumber);
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
		a.addCustomerInfo(customerName, customerPhoneNumber);
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
		a.addCustomerInfo(customerName, customerPhoneNumber);
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
		a.addCustomerInfo(customerName, customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		
		assertTrue(appointmentController.createAppointment(newAppointment, newLength, ""));
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
	
	@Test
	void testInsertAppointment() throws DatabaseAccessException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		String description = "jo mama";
		int length = 60;
		
		LocalDateTime appointmentDate = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime date = LocalDateTime.of(2022, 1, 1, 11, 1);
		
		String retrievedEmployee = "";
		String retrievedDescription = "";
		int retrievedLength = 0;
		
		LocalDateTime retrievedAppointmentDate = null;
		LocalDateTime retrievedDate = null;
		
		Appointment a = new Appointment(appointmentDate, length, description);
		a.setCreationDate(date);
		a.addCustomerInfo(customerName, customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		
		try {
			ResultSet rs =  DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Appointment")
				.executeQuery();
			
			if (rs.next()) {
				retrievedDate = rs.getTimestamp("creationDate").toLocalDateTime();
				retrievedLength = (int) rs.getDouble("length");
				retrievedAppointmentDate = rs.getTimestamp("date").toLocalDateTime();
				retrievedDescription = rs.getString("description");
				retrievedEmployee = rs.getString("employeeCpr");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertEquals(appointmentDate, retrievedAppointmentDate);
		assertEquals(length, retrievedLength);
		assertEquals(date, retrievedDate);
		assertEquals(testEmployee.getCpr(), retrievedEmployee);
		assertEquals(description, retrievedDescription);
	}

	@Test
	void testGetAllAppointments() throws DatabaseAccessException {
		String customerPhoneNumber = "12345678";
		String customerName = "Joe";
		String description = "jo mama";
		int length = 60;
		
		LocalDateTime appointmentDate = LocalDateTime.of(2000, 1, 1, 13, 0);
		LocalDateTime date = LocalDateTime.of(2022, 1, 1, 11, 1);
		
		Appointment a = new Appointment(appointmentDate, length, description);
		a.setCreationDate(date);
		a.addCustomerInfo(customerName, customerPhoneNumber);
		a.setEmployee(testEmployee);
		
		AppointmentDB appointmentDb = new AppointmentDB();
		
		appointmentDb.insertAppointment(a);
		appointmentDb.insertAppointment(a);
		
		List<Appointment> returnList = appointmentDb.getAppointmentsOnDate(appointmentDate);
		
		assertEquals(2, returnList.size());

		assertEquals(a.getAppointmentDate(), returnList.get(0).getAppointmentDate());
		assertEquals(a.getLength(), returnList.get(0).getLength(), 0);
		assertEquals(a.getCreationDate(), returnList.get(0).getCreationDate());
		assertEquals(a.getDescription(), returnList.get(0).getDescription());
		assertEquals(a.getEmployee().getPhoneNumber(), returnList.get(0).getEmployee().getPhoneNumber());
		
		assertEquals(a.getAppointmentDate(), returnList.get(1).getAppointmentDate());
		assertEquals(a.getLength(), returnList.get(1).getLength(), 0);
		assertEquals(a.getCreationDate(), returnList.get(1).getCreationDate());
		assertEquals(a.getDescription(), returnList.get(1).getDescription());
		assertEquals(a.getEmployee().getPhoneNumber(), returnList.get(1).getEmployee().getPhoneNumber());
		
	}
	
	
}
