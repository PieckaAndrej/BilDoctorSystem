package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Product;

public class ProductDB implements ProductDBIF {
	
	private static final String SELECT_PRODUCT_STATEMENT = "SELECT * FROM Product WHERE id = ?";
	private PreparedStatement selectProductStatement;
	
	public ProductDB() {
		try {
			selectProductStatement = DbConnection.getInstance().getConnection().prepareStatement(SELECT_PRODUCT_STATEMENT);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Product searchProduct(int productId) {
		
		Product product = null;

		try {
			selectProductStatement.setInt(1, productId);
			ResultSet rs = selectProductStatement.executeQuery();
			if (rs.next()) {
					product = buildObject(rs);
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return product;
	}
	
	private Product buildObject(ResultSet rs) throws SQLException {
		return new Product(rs.getInt("currentStock"), rs.getDouble("price"));
	}
}
