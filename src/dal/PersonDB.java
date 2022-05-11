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

	private ResultSet findPerson(String phone) throws SQLException{
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
	
	private Employee buildObject(ResultSet rs) throws SQLException {
		ResultSet resultSet = findPerson(rs.getString("phoneNumber"));
		return new Employee(resultSet.getString("name"), resultSet.getString("address"), resultSet.getString("city"),
				resultSet.getString("zipcode"), resultSet.getString("phoneNumber"), rs.getDouble("salary"));
	}
}
