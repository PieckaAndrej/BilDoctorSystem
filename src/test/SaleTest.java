package test;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import controller.SaleController;
import dal.DbConnection;
import dal.ProductDB;
import dal.SaleDB;
import exceptions.DatabaseAccessException;
import model.Product;
import model.Sale;

public class SaleTest {
	
	private SaleController saleController;
	private ProductDB productDB;
	private SaleDB saleDB;
	
	public SaleTest() throws DatabaseAccessException
	{
		saleController = new SaleController();
		productDB = new ProductDB();
		saleDB = new SaleDB();
	}
	
	@Test
	public void registerSaleTest()
	{
		String plateNumber = "AAA";
		
		int productId = 1;
		
		Product product = productDB.searchProduct(productId);
		
		assertEquals(saleController.createSale(plateNumber), true);
		assertEquals(saleController.addService(200, 10, "tire change"), true);
		assertEquals(saleController.addProduct(productId, product.getName(), 10), true);
		assertEquals(saleController.finishSale(), true);
	}
}
