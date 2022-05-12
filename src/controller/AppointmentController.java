package controller;

import java.util.ArrayList;

import dal.AppointmentDB;
import exceptions.DatabaseAccessException;
import model.Employee;

public class AppointmentController {
	private PersonController personCtrl;
	private AppointmentDB appointmentdb;
	
	public AppointmentController() {
		personCtrl = new PersonController();
		appointmentdb = new AppointmentDB();
	}
	
	/**
	 * Get all employees from the person controller
	 * @return ArrayList of all employees
	 * @throws DatabaseAccessException
	 */
	public ArrayList<Employee> getAllEmployees() throws DatabaseAccessException{
		return personCtrl.getAllEmployees();
	}
}
