package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import exceptions.DatabaseAccessException;
import model.Sale;
import model.Service;

public class ServiceDB implements ServiceDBIF {
	
	private static final String CREATE_STATEMENT = "INSERT INTO Service(description, time, price, plateNr, date)"
				+ " VALUES(?, ?, ?, ?, ?)";
	private PreparedStatement createStatement;
	
	public ServiceDB() {
		
	}

	/**
	 * Insert service to the database
	 * @param service Service that is being inserted
	 * @param sale The sale that contains the service
	 * @return True if the service was added successfully
	 */
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
			
			createStatement.setString(1, service.getDescription());
			createStatement.setDouble(2, service.getTime());
			createStatement.setBigDecimal(3, service.getPrice());
			createStatement.setString(4, sale.getVehicle().getPlateNumber());
			createStatement.setTimestamp(5, Timestamp.valueOf(sale.getDate()));
			
			
			DbConnection.getInstance().executeSqlInsertWithIdentity(createStatement);
			retVal = true;
			
		} catch(SQLException e) {
			throw new DatabaseAccessException(e.getMessage());
		}
		return retVal;
	}

	/**
	 * @return the createStatement
	 */
	public static String getCreateStatement() {
		return CREATE_STATEMENT;
	}

}
