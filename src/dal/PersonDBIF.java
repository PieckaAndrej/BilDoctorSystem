package dal;

import java.util.ArrayList;

import exceptions.DatabaseAccessException;
import model.Employee;

public interface PersonDBIF {
	ArrayList<Employee> getAllEmployees() throws DatabaseAccessException;
}
