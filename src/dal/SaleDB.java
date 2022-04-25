package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import exceptions.DatabaseAccessException;
import model.Sale;

public class SaleDB implements SaleDBIF {
	
	private static final String CREATE_STATEMENT = "INSERT INTO Sale(date, description) VALUES(?, ?)";
	private PreparedStatement createStatement;
	
	public SaleDB() throws DatabaseAccessException {
		try {
			createStatement = DbConnection.getInstance().getConnection().prepareStatement(CREATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseAccessException(DatabaseAccessException.CONNECTION_MESSAGE);
		}
	}
	
	@Override
	public boolean insertSale(Sale sale) throws DatabaseAccessException {
		boolean retVal = false;
		try {
			createStatement.setTimestamp(1, Timestamp.valueOf(sale.getDate()));
			createStatement.setString(2, sale.getDescription());
			sale.setId(DbConnection.getInstance().executeSqlInsertWithIdentity(createStatement));
			retVal = true;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return retVal;
	}
		
}
