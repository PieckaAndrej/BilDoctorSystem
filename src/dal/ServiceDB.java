package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import exceptions.DatabaseAccessException;
import model.Service;

public class ServiceDB implements ServiceDBIF {
	
	private static final String CREATE_STATEMENT = "INSERT INTO OrderLine(cost, time, description) VALUES(?, ?, ?)";
	private PreparedStatement createStatement;
	
	public ServiceDB() throws DatabaseAccessException {
		try {
			createStatement = DbConnection.getInstance().getConnection().prepareStatement(CREATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseAccessException(DatabaseAccessException.CONNECTION_MESSAGE);
		}
	}

	public boolean insertService(Service service) throws DatabaseAccessException {
		boolean retVal = false;
		try {
			createStatement.setDouble(1, service.getCost());
			createStatement.setDouble(2, service.getTime());
			createStatement.setString(3, service.getDescription());
			retVal = true;
			
		}catch(SQLException e) {
			throw new DatabaseAccessException(e.getMessage());
		}
		return retVal;
	}

}
