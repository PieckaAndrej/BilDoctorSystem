package dal;

import java.sql.SQLException;

import model.Product;

public interface ProductDBIF {
	public Product searchProduct(int productId);
	
	public boolean updateProduct(Product product) throws SQLException;
}
