package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dal.AppointmentDB;
import dal.OrderLineDB;
import dal.PersonDB;
import dal.ProductDB;
import dal.SaleDB;
import dal.ServiceDB;
import dal.VehicleDB;

public class Cleaners {
	public static CleanDatabase getProductCleaner() {
		return new CleanDatabase("Product", ProductDB.getInsertStatement(), true) {

			@Override
			public void insertPreparedStatement(PreparedStatement stmt, ResultSet product) {
				try {
					stmt.setString(1, product.getString("name"));
					stmt.setInt(2, product.getInt("currentStock"));
					stmt.setBigDecimal(3, product.getBigDecimal("price"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
		};
	}
	
	public static CleanDatabase getSaleCleaner() {
		return new CleanDatabase("Sale", SaleDB.getCreateStatement()) {
			
			@Override
			public void insertPreparedStatement(PreparedStatement stmt, ResultSet sale) {
				try {
					stmt.setString(1, sale.getString("plateNo"));
					stmt.setTimestamp(2, sale.getTimestamp("date"));
					stmt.setString(3, sale.getString("description"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public static CleanDatabase getOrderLineCleaner() {
		return new CleanDatabase("OrderLine", OrderLineDB.getCreateStatement(), true) {
			
			@Override
			public void insertPreparedStatement(PreparedStatement stmt, ResultSet orderLine) {
				try {
					stmt.setInt(1, orderLine.getInt("quantity"));
					stmt.setString(2, orderLine.getString("plateNr"));
					stmt.setTimestamp(3, orderLine.getTimestamp("date"));
					stmt.setInt(4, orderLine.getInt("productId"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public static CleanDatabase getServiceCleaner() {
		return new CleanDatabase("Service", ServiceDB.getCreateStatement(), true) {
			
			@Override
			public void insertPreparedStatement(PreparedStatement stmt, ResultSet service) {
				try {
					stmt.setString(1, service.getString("description"));
					stmt.setDouble(2, service.getDouble("time"));
					stmt.setBigDecimal(3, service.getBigDecimal("price"));
					stmt.setString(4, service.getString("plateNr"));
					stmt.setTimestamp(5, service.getTimestamp("date"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public static CleanDatabase getVehicleCleaner() {
		return new CleanDatabase("Vehicle", VehicleDB.getInsertVehicleStatement()) {
			
			@Override
			public void insertPreparedStatement(PreparedStatement stmt, ResultSet vehicle) {
				try {
					stmt.setString(1, vehicle.getString("plateNumber"));
					stmt.setInt(2, vehicle.getInt("year"));
					stmt.setString(3, vehicle.getString("brand"));
					stmt.setString(4, vehicle.getString("customerPhone"));
					stmt.setString(5, vehicle.getString("countryCode"));
					stmt.setDate(6, vehicle.getDate("checkUpDate"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public static CleanDatabase getAppointmentCleaner() {
		return new CleanDatabase("Appointment", AppointmentDB.getCreateStatement(), true) {
			
			@Override
			public void insertPreparedStatement(PreparedStatement stmt, ResultSet appointment) {
				try {
					stmt.setTimestamp(1, appointment.getTimestamp("creationDate"));
					stmt.setInt(2, appointment.getInt("length"));
					stmt.setTimestamp(3, appointment.getTimestamp("date"));
					stmt.setString(4, appointment.getString("description"));
					stmt.setString(5, appointment.getString("employeeCpr"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public static CleanDatabase getEmployeeCleaner() {
		return new CleanDatabase("Employee", PersonDB.getInsertEmployee()) {
			
			@Override
			public void insertPreparedStatement(PreparedStatement stmt, ResultSet employee) {
				try {
					stmt.setString(1, employee.getString("cpr"));
					stmt.setBigDecimal(2, employee.getBigDecimal("salary"));
					stmt.setString(3, employee.getString("phoneNo"));
					stmt.setString(4, employee.getString("countryCode"));
					stmt.setString(5, employee.getString("position"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public static CleanDatabase getPersonCleaner() {
		return new CleanDatabase("Person", PersonDB.getInsertPerson()) {
			
			@Override
			public void insertPreparedStatement(PreparedStatement stmt, ResultSet person) {
				try {
					stmt.setString(1, person.getString("name"));
					stmt.setString(2, person.getString("surname"));
					stmt.setString(3, person.getString("zipcode"));
					stmt.setString(4, person.getString("address"));
					stmt.setString(5, person.getString("association"));
					stmt.setString(6, person.getString("phoneNumber"));
					stmt.setString(7, person.getString("countryCode"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
	}
	
	public static CleanDatabase getCustomerCleaner() {
		return new CleanDatabase("Customer", PersonDB.getInsertCustomer()) {
			
			@Override
			public void insertPreparedStatement(PreparedStatement stmt, ResultSet customer) {
				try {
					stmt.setString(1, customer.getString("discountCategory"));
					stmt.setString(2, customer.getString("phoneNo"));
					stmt.setString(3, customer.getString("countryCode"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
