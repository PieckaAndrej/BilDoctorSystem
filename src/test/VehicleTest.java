package test;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import dal.DbConnection;
import dal.VehicleDB;
import exceptions.DatabaseAccessException;
import model.Vehicle;

public class VehicleTest {
	
	private VehicleDB vehicleDB;
	
	public VehicleTest() throws DatabaseAccessException {
		vehicleDB = new VehicleDB();
	}
	
	@Test
	public void searchVehicleTest() {
		String plateNumber = "AAA";
		
		String query = "SELECT * FROM Vehicle WHERE plateNumber = ?";
		
		Vehicle vehicle = vehicleDB.searchVehicle(plateNumber);
		
		PreparedStatement prst;
		
		try {
			prst = DbConnection.getInstance().getConnection().prepareStatement(query);
			
			prst.setString(1, plateNumber);
			
			ResultSet rs = prst.executeQuery();
			
			if (rs.next()) {
				assertEquals(vehicle.getPlateNumber(), rs.getString("plateNumber"));
				assertEquals(vehicle.getYear(), rs.getInt("[year]"));
				assertEquals(vehicle.getBrand(), rs.getString("brand"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
