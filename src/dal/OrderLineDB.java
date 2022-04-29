package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import exceptions.DatabaseAccessException;
import model.OrderLine;
import model.Sale;

public class OrderLineDB implements OrderLineDBIF {
	
	private static final String CREATE_STATEMENT = "INSERT INTO OrderLine(quantity, plateNr, date, productId)"
			+ " VALUES(?, ?, ?, ?)";
	
	public OrderLineDB() {
	}

	@Override
	public boolean insertOrderLine(OrderLine orderline, Sale sale) throws DatabaseAccessException {
		boolean retVal = false;
		PreparedStatement createStatement = null;
		try {
			try {
				createStatement = DbConnection.getInstance().getConnection()
						.prepareStatement(CREATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseAccessException(DatabaseAccessException.CONNECTION_MESSAGE);
			}
			
			createStatement.setInt(1, orderline.getQuantity());
			createStatement.setString(2, sale.getVehicle().getPlateNumber());
			createStatement.setTimestamp(3, Timestamp.valueOf(sale.getDate()));
			createStatement.setInt(4, orderline.getProduct().getId());
			
			DbConnection.getInstance().executeSqlInsertWithIdentity(createStatement);
			retVal = true;
			
		} catch(SQLException e) {
			throw new DatabaseAccessException(e.getMessage());
		}
		return retVal;
	}

}
