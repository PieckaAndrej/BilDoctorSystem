package controller;
import java.util.List;

import dal.PersonDB;
import dal.PersonDBIF;
import exceptions.DatabaseAccessException;
import model.Employee;

public class PersonController {
	private PersonDBIF personDB;

	public PersonController() {
		personDB = new PersonDB();
	}
	
	/**
	 * Get all employees from the database
	 * @return ArrayList of all employees
	 * @throws DatabaseAccessException
	 */
	public List<Employee> getAllEmployees() throws DatabaseAccessException {
		return personDB.getAllEmployees();
	}
	
	
}
