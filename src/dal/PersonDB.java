package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.DatabaseAccessException;
import model.Employee;
import model.Person;
import model.Product;

public class PersonDB implements PersonDBIF {

	private static final String SELECT_EMPLOYEES = "SELECT * FROM Employee";
	private PreparedStatement selectAllEmployees;
	private PreparedStatement selectPersonWithPhoneNo;
	
	
	public PersonDB() {
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
		return new Employee(resultSet.getString("name"), resultSet.getString("address"), resultSet.getString("city"),
				resultSet.getString("zipcode"), resultSet.getString("phoneNumber"), rs.getDouble("salary"));
	}
}
