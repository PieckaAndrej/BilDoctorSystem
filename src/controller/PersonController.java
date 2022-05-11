package controller;
import java.util.ArrayList;

import dal.PersonDB;
import dal.PersonDBIF;
import exceptions.DatabaseAccessException;
import model.Employee;

public class PersonController {
	

	private PersonDBIF personDB;

	public PersonController() {
		personDB = new PersonDB();
	}
	public ArrayList<Employee> getAllEmployees() throws DatabaseAccessException {
		return personDB.getAllEmployees();
	}
	
	
}
