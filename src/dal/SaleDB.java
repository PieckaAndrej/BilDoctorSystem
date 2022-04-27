package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import exceptions.DatabaseAccessException;
import model.OrderLine;
import model.Sale;
import model.Service;

public class SaleDB implements SaleDBIF {
	
	private static final String CREATE_STATEMENT = "INSERT INTO Sale(plateNo, date, description) VALUES(?, ?, ?)";
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
			DbConnection.getInstance().startTransaction();
			
			createStatement.setString(1, sale.getVehicle().getPlateNumber());
			createStatement.setTimestamp(2, Timestamp.valueOf(sale.getDate()));
			createStatement.setString(3, sale.getDescription());
			sale.setId(DbConnection.getInstance().executeSqlInsertWithIdentity(createStatement));
			retVal = true;
			
			OrderLineDBIF orderLineDb = new OrderLineDB();
			
			for (OrderLine orderLine :sale.getOrderLines()) {
				orderLineDb.insertOrderLine(orderLine);
			}
			
			ServiceDBIF serviceDb = new ServiceDB();
			
			for (Service service :sale.getServices()) {
				serviceDb.insertService(service);
			}
			
			DbConnection.getInstance().commitTransaction();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			DbConnection.getInstance().rollbackTransaction();
			throw new DatabaseAccessException(e.getMessage());
		}
		
		return retVal;
	}
		
}
