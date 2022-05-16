package test;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dal.DbConnection;
import dal.ProductDB;
import model.Product;

class ProductTest {

	private ProductDB productDB;
	private CleanDatabase productCleaner;
	private Product testProduct;
	
	@BeforeEach
	void setUp() {
		productDB = new ProductDB();
		productCleaner = Cleaners.getProductCleaner();
		
		try {
			productCleaner.setUp();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		testProduct = new Product(10, 10, 0, "test product");
		
		productDB = new ProductDB();
		testProduct = productDB.insertProduct(testProduct);
	}
	
	@AfterEach
	void cleanUp() {
		try {
			productCleaner.retrieveDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void searchProductTest() {
		int productId = 1;
		
		String query = "SELECT * FROM Product WHERE id = ?";
		
		Product product = productDB.searchProduct(productId);

		assertEquals(testProduct.getCurrentStock(), product.getCurrentStock());
		assertEquals(testProduct.getPrice().doubleValue(), product.getPrice().doubleValue(), 0);
		assertEquals(testProduct.getId(), product.getId());

	}	
}
