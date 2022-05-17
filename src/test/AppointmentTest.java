package test;


import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.AppointmentController;
import dal.PersonDB;
import exceptions.DatabaseAccessException;
import exceptions.LengthUnderrunException;

public class AppointmentTest {
	
	private AppointmentController appointmentController;
	private PersonDB personDB;
	
	@BeforeEach
	void setUp() throws DatabaseAccessException {
		appointmentController = new AppointmentController();
		personDB = new PersonDB();
	}
	
	@Test
	void scheduleAppointment() throws DatabaseAccessException, LengthUnderrunException {
		String phoneNumber = "97845625";
		
		assertEquals(appointmentController.createAppointment(null, 1, "Oil change"), true);
	}
	
}
