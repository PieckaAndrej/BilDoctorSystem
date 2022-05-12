package dal;

import java.sql.SQLException;
import java.util.List;

import model.Product;

public interface ProductDBIF {
	public Product searchProduct(int productId);
	
	public boolean updateProduct(Product product) throws SQLException;

	public List<Product> getAllProducts();
}
