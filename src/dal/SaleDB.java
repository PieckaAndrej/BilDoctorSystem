package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import exceptions.DatabaseAccessException;
import model.OrderLine;
import model.Product;
import model.Sale;
import model.Service;

public class SaleDB implements SaleDBIF {
	
	private static final String CREATE_STATEMENT = "INSERT INTO "
			+ "Sale(plateNo, date, description) VALUES(?, ?, ?)";
	private PreparedStatement createStatement;
	
	public SaleDB() {
		
	}
	
	/**
	 * Insert sale to the database
	 * @param sale The sale that is inserted
	 * @Return Sale that is inserted with id from the database
	 */
	@Override
	public Sale insertSale(Sale sale) throws DatabaseAccessException {
		try {
			
			// Create statement
			try {
				createStatement = DbConnection.getInstance().getConnection()
						.prepareStatement(CREATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseAccessException(DatabaseAccessException.CONNECTION_MESSAGE);
			}
			
			// Start transaction
			DbConnection.getInstance().startTransaction();
			
			
			// Insert sale
			createStatement.setString(1, sale.getVehicle().getPlateNumber());
			createStatement.setTimestamp(2, Timestamp.valueOf(sale.getDate()));
			createStatement.setString(3, sale.getDescription());
			sale.setId(DbConnection.getInstance().executeSqlInsertWithIdentity(createStatement));
			
			// Insert order lines
			OrderLineDBIF orderLineDb = new OrderLineDB();
			ProductDBIF productDb = new ProductDB();
			
			for (OrderLine orderLine : sale.getOrderLines()) {
				orderLineDb.insertOrderLine(orderLine, sale);
				
				Product p = orderLine.getProduct();
				p.setCurrentStock(p.getCurrentStock() - orderLine.getQuantity());
				productDb.updateProduct(p);
			}
			
			// Insert services
			ServiceDBIF serviceDb = new ServiceDB();
			
			for (Service service : sale.getServices()) {
				serviceDb.insertService(service, sale);
			}
			
			// Commit
			DbConnection.getInstance().commitTransaction();
			
		} catch (SQLException e) {
			// Roll back
			System.out.println("Rolled back");
			DbConnection.getInstance().rollbackTransaction();
			
			throw new DatabaseAccessException(e.getMessage());
		}
		
		// Return sale with id
		return sale;
	}

	/**
	 * @return the createStatement
	 */
	public static String getCreateStatement() {
		return CREATE_STATEMENT;
	}
}
