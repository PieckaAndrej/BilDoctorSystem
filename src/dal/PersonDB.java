package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.DatabaseAccessException;
import model.Employee;

public class PersonDB implements PersonDBIF {

	private static final String SELECT_EMPLOYEES = "SELECT * FROM Employee";
	private static final String INSERT_PERSON = "INSERT INTO Person(name, surname, zipcode, address, association, phoneNumber) VALUES (?, ?, ?, ?, ?, ?)";
	private static final String INSERT_EMPLOYEE = "INSERT INTO Employee(salary, phoneNo) VALUES (?, ?)";
	private PreparedStatement selectAllEmployees;
	private PreparedStatement selectPersonWithPhoneNo;
	private PreparedStatement insertPerson;
	private PreparedStatement insertEmployee;
	
	
	public PersonDB() {
	}
	
	public boolean insertPerson(Employee employee) throws DatabaseAccessException {
		boolean retVal = false;
		
		DbConnection.getInstance().startTransaction();
		
		try {
			try {
				insertPerson = DbConnection.getInstance().getConnection()
						.prepareStatement(INSERT_PERSON);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseAccessException(DatabaseAccessException.CONNECTION_MESSAGE);
			}

			insertPerson.setString(1, employee.getName());
			insertPerson.setString(2, employee.getSurname());
			insertPerson.setString(3, employee.getZipcode());
			insertPerson.setString(4, employee.getAddress());
			insertPerson.setString(5, "E");
			insertPerson.setString(6, employee.getPhoneNumber());
			
			insertEmployee(employee);
			
			insertPerson.executeUpdate();
			
			DbConnection.getInstance().commitTransaction();
			retVal = true;
			
		} catch(SQLException e) {
			DbConnection.getInstance().rollbackTransaction();
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return retVal;
	}
	
	public boolean insertEmployee(Employee employee) throws DatabaseAccessException {
		boolean retVal = false;
		
		try {
			try {
				insertEmployee = DbConnection.getInstance().getConnection()
						.prepareStatement(INSERT_EMPLOYEE);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseAccessException(DatabaseAccessException.CONNECTION_MESSAGE);
			}

			insertEmployee.setBigDecimal(1, employee.getSalary());
			insertEmployee.setString(2, employee.getPhoneNumber());
			
			insertEmployee.executeUpdate();
			retVal = true;
			
		} catch(SQLException e) {
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return retVal;
	}

	/**
	 * Get all employees from the database
	 * @return List of all employees
	 */
	@Override
	public ArrayList<Employee> getAllEmployees() throws DatabaseAccessException {
		ArrayList<Employee> employees = new ArrayList<>();
		
		try {
			selectAllEmployees = DbConnection.getInstance().getConnection()
					.prepareStatement(SELECT_EMPLOYEES);
			ResultSet rs = selectAllEmployees.executeQuery();
			
			while(rs.next()) {
				
				employees.add(buildObject(rs));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return employees;
	}

	/**
	 * Find person by phone
	 * @param phone Phone of the person
	 * @return ResultSet that the database returns
	 * @throws SQLException
	 */
	private ResultSet findPerson(String phone) throws SQLException {
		ResultSet result = null;
		String SELECT_PERSON = "SELECT * FROM Person WHERE phoneNumber = ?";
		try {
			selectPersonWithPhoneNo = DbConnection.getInstance().getConnection()
					.prepareStatement(SELECT_PERSON);
			selectPersonWithPhoneNo.setString(1, phone);
			result = selectPersonWithPhoneNo.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Build object of employee
	 * @param rs Result set from the database
	 * @return Object employee built from the result set
	 * @throws SQLException
	 */
	private Employee buildObject(ResultSet rs) throws SQLException {
		ResultSet resultSet = findPerson(rs.getString("phoneNumber"));
		return new Employee(resultSet.getString("name"), resultSet.getString("surname"), resultSet.getString("address"), resultSet.getString("city"),
				resultSet.getString("zipcode"), resultSet.getString("phoneNumber"), rs.getBigDecimal("salary"));
	}

	/**
	 * @return the insertPerson
	 */
	public static String getInsertPerson() {
		return INSERT_PERSON;
	}

	/**
	 * @return the insertEmployee
	 */
	public static String getInsertEmployee() {
		return INSERT_EMPLOYEE;
	}
}
