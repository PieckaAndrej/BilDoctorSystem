package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Product;

public class ProductDB implements ProductDBIF {
	
	private static final String SELECT_PRODUCT_STATEMENT = "SELECT * FROM Product WHERE id = ?";
	private PreparedStatement selectProductStatement;
	
	private static final String UPDATE_STATEMENT = "UPDATE Product SET name = ?, currentStock = ?, price = ? WHERE id = ?";
	
	public ProductDB() {
		
	}
	
	public Product searchProduct(int productId) {
		
		Product product = null;

		try {
			try {
				selectProductStatement = DbConnection.getInstance().getConnection()
						.prepareStatement(SELECT_PRODUCT_STATEMENT);
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
	
	public boolean updateProduct(Product product) {
		boolean retVal = false;
		PreparedStatement updateProductStatement = null;
		
		try {
			try {
				updateProductStatement = DbConnection.getInstance().getConnection()
						.prepareStatement(UPDATE_STATEMENT);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// Product
			updateProductStatement.setString(1, product.getName());
			updateProductStatement.setInt(2, product.getCurrentStock());
			updateProductStatement.setBigDecimal(3, product.getPrice());
			
			// Where id
			updateProductStatement.setInt(4, product.getId());
			
			System.out.println("Update " + product.getCurrentStock() + " " + product.getId());

			updateProductStatement.executeUpdate();
			
			retVal = true;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return retVal;
	}
	
	private Product buildObject(ResultSet rs) throws SQLException {
		return new Product(rs.getInt("currentStock"), rs.getDouble("price"), rs.getInt("id"), rs.getString("name"));
	}
}
