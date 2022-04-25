package test;

import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import dal.DbConnection;
import dal.ProductDB;
import exceptions.DatabaseAccessException;
import model.Product;
import model.Vehicle;

public class ProductTest {

	private ProductDB productDB;
	
	public ProductTest() throws DatabaseAccessException
	{
		productDB = new ProductDB();
	}
	
	@Test
	public void searchProductTest() {
		int productId = 1;
		
		String query = "SELECT * FROM Product WHERE id = ?";
		
		Product product = productDB.searchProduct(productId);
		
		PreparedStatement prst;
		
		try {
			prst = DbConnection.getInstance().getConnection().prepareStatement(query);
			
			prst.setInt(1, productId);
			
			ResultSet rs = prst.executeQuery();
			
			if (rs.next()) {
				assertEquals(product.getCurrentStock(), rs.getInt("currentStock"));
				assertEquals(product.getPrice(), rs.getDouble("price"), 0);
				assertEquals(product.getProductId(), rs.getInt("id"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
}
