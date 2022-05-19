package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import exceptions.DatabaseAccessException;
import model.Person;
import model.Vehicle;

public class VehicleDB implements VehicleDBIF {
	
	private static final String SELECT_VEHICLE_STATEMENT = "SELECT * FROM "
			+ "Vehicle WHERE plateNumber = ?";
	private PreparedStatement selectVehicleStatement;
	
	private static final String INSERT_VEHICLE_STATEMENT = "INSERT INTO "
			+ "Vehicle(plateNumber, year, brand, customerPhone, countryCode) VALUES(?, ?, ?, ?, ?)";
	private PreparedStatement insertVehicleStatement;
	
	private static final String GET_ALL_VEHICLES_STATEMENT = "SELECT * FROM Vehicle";
	private PreparedStatement getAllVehiclesStatement;
	
	public VehicleDB() {
	}
	
	/**
	 * Search vehicle in the database
	 * @param plateNumber The plate number of the vehicle
	 * @return Vehicle with the plateNumber in the database
	 */
	public Vehicle searchVehicle(String plateNumber) {
		
		Vehicle vehicle = null;

		try {
			try {
				selectVehicleStatement = DbConnection.getInstance().getConnection()
						.prepareStatement(SELECT_VEHICLE_STATEMENT);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			selectVehicleStatement.setString(1, plateNumber);
			ResultSet rs = selectVehicleStatement.executeQuery();
			if (rs.next()) {
					vehicle = buildObject(rs);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return vehicle;
	}
	
	/**
	 * Insert vehicle to the database
	 * @param vehicle Vehicle that is being inserted
	 * @param customer The customer that own the vehicle
	 * @return True if the vehicle was added successfully
	 */
	public boolean insertVehicle(Vehicle vehicle, Person customer) throws DatabaseAccessException {
		boolean retVal = false;
		
		try {
			try {
				insertVehicleStatement = DbConnection.getInstance().getConnection()
						.prepareStatement(INSERT_VEHICLE_STATEMENT);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseAccessException(DatabaseAccessException.CONNECTION_MESSAGE);
			}
			
			String phoneNumber = null;
			String countryCode = null;
			
			if (customer != null) {
				phoneNumber = customer.getPhoneNumber();
				countryCode = customer.getCountryCode();
				
			}
			
			insertVehicleStatement.setString(1, vehicle.getPlateNumber());
			insertVehicleStatement.setInt(2, vehicle.getYear());
			insertVehicleStatement.setString(3, vehicle.getBrand());
			insertVehicleStatement.setString(4, phoneNumber);
			insertVehicleStatement.setString(5, countryCode);
			
			insertVehicleStatement.executeUpdate();
			retVal = true;
			
		} catch(SQLException e) {
			throw new DatabaseAccessException(e.getMessage());
		}
		return retVal;
	}
	
	/**
	 * Build object from the result set
	 * @param rs Result set containing the vehicle
	 * @return Vehicle built from the result set
	 * @throws SQLException
	 */
	private Vehicle buildObject(ResultSet rs) throws SQLException {
		return new Vehicle(rs.getString("plateNumber"), rs.getInt("year"), rs.getString("brand"));
	}

	/**
	 * @return the insertVehicleStatement
	 */
	public static String getInsertVehicleStatement() {
		return INSERT_VEHICLE_STATEMENT;
	}

	public ArrayList<Vehicle> getAllVehicles() {
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		
		Vehicle currentVehicle = null;
		
		try {
			getAllVehiclesStatement = DbConnection.getInstance().getConnection().prepareStatement(GET_ALL_VEHICLES_STATEMENT);
			ResultSet rs = getAllVehiclesStatement.executeQuery();
			while(rs.next())
			{
				currentVehicle = buildObject(rs);
				vehicles.add(currentVehicle);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return vehicles;
	}
}
