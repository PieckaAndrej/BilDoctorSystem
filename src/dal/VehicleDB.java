package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Vehicle;

public class VehicleDB implements VehicleDBIF {
	
	private static final String SELECT_VEHICLE_STATEMENT = "SELECT * FROM Vehicle WHERE plateNumber = ?";
	private PreparedStatement selectVehicleStatement;
	
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
	 * Build object from the result set
	 * @param rs Result set containing the vehicle
	 * @return Vehicle built from the result set
	 * @throws SQLException
	 */
	private Vehicle buildObject(ResultSet rs) throws SQLException {
		return new Vehicle(rs.getString("plateNumber"), rs.getInt("year"), rs.getString("brand"));
	}
}
