package dal;

import java.util.ArrayList;
import java.util.List;

import exceptions.DatabaseAccessException;
import model.Employee;

public interface PersonDBIF {
	List<Employee> getAllEmployees() throws DatabaseAccessException;
}
