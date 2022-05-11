package controller;
import dal.PersonDB;
import dal.PersonDBIF;
import model.Employee;

public class PersonController {
	

private PersonDBIF personDB;

	public PersonController() {
		personDB = new PersonDB();
	}
	public Employee getAllEmployees() {
		return personDB.getAllEmployees();
	}
	
	
}
