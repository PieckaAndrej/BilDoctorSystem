package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import exceptions.DatabaseAccessException;
import model.Sale;
import model.Service;

public class ServiceDB implements ServiceDBIF {
	
		private static final String CREATE_STATEMENT = "INSERT INTO Service(price, time, description, date, plateNr)"
				+ " VALUES(?, ?, ?, ?, ?)";
	private PreparedStatement createStatement;
	
	public ServiceDB() {
		
	}

	public boolean insertService(Service service, Sale sale) throws DatabaseAccessException {
		boolean retVal = false;
		try {
			try {
				createStatement = DbConnection.getInstance().getConnection()
						.prepareStatement(CREATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseAccessException(DatabaseAccessException.CONNECTION_MESSAGE);
			}
			
			createStatement.setBigDecimal(1, service.getPrice());
			createStatement.setDouble(2, service.getTime());
			createStatement.setString(3, service.getDescription());
			createStatement.setTimestamp(4, Timestamp.valueOf(sale.getDate()));
			createStatement.setString(5, sale.getVehicle().getPlateNumber());
			
			DbConnection.getInstance().executeSqlInsertWithIdentity(createStatement);
			retVal = true;
			
		} catch(SQLException e) {
			throw new DatabaseAccessException(e.getMessage());
		}
		return retVal;
	}

}
