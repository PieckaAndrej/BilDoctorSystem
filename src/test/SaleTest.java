package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import controller.SaleController;
import dal.ProductDB;
import dal.SaleDB;
import exceptions.DatabaseAccessException;
import exceptions.OutOfStockException;
import exceptions.QuantityUnderrunException;
import model.Product;

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
		try {
			assertEquals(saleController.addProduct(productId, product.getName(), 10), true);
		} catch (QuantityUnderrunException | OutOfStockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(saleController.finishSale(), true);
	}
	
	@Test
	public void registerSaleFalsePlateNumberTest() {
		String plateNumber = "AAB";
		int productId = 1;
		
		Product product = productDB.searchProduct(productId);
		
		assertEquals(saleController.createSale(plateNumber), false);
	}
	@Test
	public void registerSaleFalseProductIdTest() {
		String plateNumber = "AAA";
		int productId = 20;
		
		Product product = productDB.searchProduct(productId);
	
		try {
			assertEquals(saleController.addProduct(productId, "name", 10), false);
		} catch (QuantityUnderrunException | OutOfStockException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
