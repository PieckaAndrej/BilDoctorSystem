package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.DatabaseAccessException;
import model.Customer;
import model.Employee;

public class PersonDB implements PersonDBIF {

	private static final String SELECT_EMPLOYEES = "SELECT * FROM Person "
			+ "	join City on Person.Zipcode = City.Zipcode "
			+ "	join Employee on Person.phoneNumber = Employee.phoneNo "
			+ "and Person.countryCode = Employee.countryCode;";
	
	private static final String SELECT_EMPLOYEE = "SELECT * FROM Employee "
			+ "join Person on Employee.phoneNo = Person.phoneNumber "
			+ "and Employee.countryCode = Person.countryCode "
			+ "join City on Person.Zipcode = City.Zipcode "
			+ "where cpr = ?";
	
	private static final String SELECT_CUSTOMER = "SELECT * FROM Customer "
			+ "join Person on Customer.phoneNo = Person.phoneNumber "
			+ "and Customer.countryCode = Person.countryCode "
			+ "join City on Person.Zipcode = City.Zipcode "
			+ "where Customer.phoneNo = ? and Customer.countryCode = ?";
	
	private static final String SELECT_PERSON = "SELECT * FROM "
			+ "Person join City on Person.Zipcode = City.Zipcode WHERE phoneNumber = ?";
	
	private static final String INSERT_PERSON = "INSERT INTO "
			+ "Person(name, surname, zipcode, address, association, phoneNumber, countryCode) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
	
	private static final String INSERT_EMPLOYEE = "INSERT INTO "
			+ "Employee(cpr, salary, phoneNo, countryCode, position) VALUES (?, ?, ?, ?, ?)";
	
	private static final String INSERT_CUSTOMER = "INSERT INTO "
			+ "Customer(discountCategory, phoneNo, countryCode) VALUES (?, ?, ?)";
	
	private PreparedStatement selectAllEmployees;
	private PreparedStatement selectPersonWithPhoneNo;
	private PreparedStatement selectCustomer;
	private PreparedStatement insertPerson;
	private PreparedStatement insertEmployee;
	
	
	public PersonDB() {
	}
	
	public boolean insertPerson(Customer customer) throws DatabaseAccessException {
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

			insertPerson.setString(1, customer.getName());
			insertPerson.setString(2, customer.getSurname());
			insertPerson.setString(3, customer.getZipcode());
			insertPerson.setString(4, customer.getAddress());
			insertPerson.setString(5, "E");
			insertPerson.setString(6, customer.getPhoneNumber());
			insertPerson.setString(7, customer.getCountryCode());

			insertPerson.executeUpdate();
			
			insertCustomer(customer);
			
			DbConnection.getInstance().commitTransaction();
			retVal = true;
			
		} catch(SQLException e) {
			DbConnection.getInstance().rollbackTransaction();
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return retVal;
	}
	
	public boolean insertCustomer(Customer customer) throws DatabaseAccessException {
		boolean retVal = false;
		
		try {
			try {
				insertEmployee = DbConnection.getInstance().getConnection()
						.prepareStatement(INSERT_CUSTOMER);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseAccessException(DatabaseAccessException.CONNECTION_MESSAGE);
			}

			insertEmployee.setString(1, customer.getDiscountCategory());
			insertEmployee.setString(2, customer.getPhoneNumber());
			insertEmployee.setString(3, customer.getCountryCode());
			
			insertEmployee.executeUpdate();
			retVal = true;
			
		} catch(SQLException e) {
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return retVal;
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
			insertPerson.setString(7, employee.getCountryCode());

			insertPerson.executeUpdate();
			
			insertEmployee(employee);
			
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

			insertEmployee.setString(1, employee.getCpr());
			insertEmployee.setBigDecimal(2, employee.getSalary());
			insertEmployee.setString(3, employee.getPhoneNumber());
			insertEmployee.setString(4, employee.getCountryCode());
			insertEmployee.setString(5, employee.getPosition());
			
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
	public List<Employee> getAllEmployees() throws DatabaseAccessException {
		List<Employee> employees = new ArrayList<>();
		
		try {
			selectAllEmployees = DbConnection.getInstance().getConnection()
					.prepareStatement(SELECT_EMPLOYEES);
			ResultSet rs = selectAllEmployees.executeQuery();
			
			
			employees = buildObjects(rs);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return employees;
	}
	
	public Employee getEmployee(String cpr) {
		Employee result = null;
		try {
			selectPersonWithPhoneNo = DbConnection.getInstance().getConnection()
					.prepareStatement(SELECT_EMPLOYEE);
			
			selectPersonWithPhoneNo.setString(1, cpr);
			
			ResultSet rs = selectPersonWithPhoneNo.executeQuery();
			
			if (rs.next()) {
				result = buildEmployee(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Customer getCustomer(String phoneNo, String countryCode) {
		Customer result = null;
		try {
			selectCustomer = DbConnection.getInstance().getConnection()
					.prepareStatement(SELECT_CUSTOMER);
			
			selectCustomer.setString(1, phoneNo);
			selectCustomer.setString(2, countryCode);
			
			ResultSet rs = selectCustomer.executeQuery();
			
			if (rs.next()) {
				result = buildCustomer(rs);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Find person by phone
	 * @param phone Phone of the person
	 * @return ResultSet that the database returns
	 * @throws SQLException
	 */
	private ResultSet findPerson(String phone) throws SQLException {
		ResultSet result = null;
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
	private Employee buildEmployee(ResultSet rs) throws SQLException {
		return new Employee(rs.getString("name"), rs.getString("surname"),
				rs.getString("address"), rs.getString("city"),
				rs.getString("zipcode"), rs.getString("phoneNumber"),
				rs.getString("countryCode"), rs.getBigDecimal("salary"),
				rs.getString("cpr"), rs.getString("position"));
	}
	
	/**
	 * Build object of customer
	 * @param rs Result set from the database
	 * @return Object customer built from the result set
	 * @throws SQLException
	 */
	private Customer buildCustomer(ResultSet rs) throws SQLException {
		return new Customer(rs.getString("name"), rs.getString("surname"),
				rs.getString("address"), rs.getString("city"),
				rs.getString("zipcode"), rs.getString("phoneNumber"),
				rs.getString("countryCode"), rs.getString("discountCategory"));
	}
	
	private List<Employee> buildObjects(ResultSet rs) throws SQLException {
		List<Employee> list = new ArrayList<>();
		while(rs.next()) {
			list.add(buildEmployee(rs));
		}
		return list;
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

	/**
	 * @return the insertCustomer
	 */
	public static String getInsertCustomer() {
		return INSERT_CUSTOMER;
	}
}
