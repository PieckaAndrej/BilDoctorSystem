package controller;

import java.util.ArrayList;

import dal.AppointmentDB;
import exceptions.DatabaseAccessException;
import model.Employee;

public class AppointmentController {
	private PersonController personctrl;
	private AppointmentDB appointmentdb;
	
	public AppointmentController() {
		super();
		personctrl = new PersonController();
		appointmentdb = new AppointmentDB();
	}
	
	public ArrayList<Employee> getAllEmployees() throws DatabaseAccessException{
		return personctrl.getAllEmployees();
	}
}
