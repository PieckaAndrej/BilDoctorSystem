package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import exceptions.DatabaseAccessException;
import model.OrderLine;
import model.Sale;

public class SaleOrderLineDB implements SaleOrderLineDBIF{

	private static final String CREATE_STATEMENT = "INSERT INTO OrderLine(id, quantity, plateNumber, date, productId) VALUES(?, ?, ?, ?, ?)";
	private PreparedStatement createStatement;
	
	public SaleOrderLineDB() throws DatabaseAccessException {
		try {
			createStatement = DbConnection.getInstance().getConnection().prepareStatement(CREATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseAccessException(DatabaseAccessException.CONNECTION_MESSAGE);
		}
	}
	
	@Override
	public boolean insertOrderLine(OrderLine orderLine, Sale sale) throws DatabaseAccessException {
		boolean retVal = false;
		try {
			createStatement.setInt(1, orderLine.getId());
			createStatement.setInt(2, orderLine.getQuantity());
			createStatement.setString(3, orderLine.getPlateNumber());
			createStatement.setTimestamp(4, Timestamp.valueOf(sale.getDate()));
			createStatement.setInt(5, orderLine.getProduct().getId());
			retVal = true;
		}catch(SQLException e) {
			//e.printStackTrace();
			throw new DatabaseAccessException(e.getMessage());
		}
		return retVal;
	}

}
