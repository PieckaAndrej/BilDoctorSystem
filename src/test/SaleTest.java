package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.SaleController;
import dal.ProductDB;
import dal.SaleDB;
import exceptions.DatabaseAccessException;
import exceptions.OutOfStockException;
import exceptions.QuantityUnderrunException;
import model.Product;

class SaleTest {
	
	private SaleController saleController;
	private ProductDB productDB;
	private SaleDB saleDB;
	
	@BeforeEach
	void setUp() throws DatabaseAccessException {
		saleController = new SaleController();
		productDB = new ProductDB();
		saleDB = new SaleDB();
	}
	
	@Test
	void registerSaleTest() {
		String plateNumber = "AAA";
		int productId = 1;
		
		boolean addProduct = false;
		
		Product product = productDB.searchProduct(productId);
		
		assertEquals(saleController.createSale(plateNumber), true);
		assertEquals(saleController.addService(200, 10, "tire change"), true);
		
		try {
			addProduct = saleController.addProduct(productId, product.getName(), 1);
			
		} catch (QuantityUnderrunException | OutOfStockException e) {
			e.printStackTrace();
		}
		
		assertEquals(addProduct, true);
		assertEquals(saleController.finishSale(), true);
	}
	
	@Test
	void registerSaleFalsePlateNumberTest() {
		String plateNumber = "AAB";
		
		assertEquals(saleController.createSale(plateNumber), false);
	}
	
	@Test
	void registerSaleFalseProductIdTest() {
		String plateNumber = "AAA";
		int productId = 2045;
		int quantity = 1;
		
		saleController.createSale(plateNumber);
	
		try {
			assertEquals(saleController.addProduct(productId, "name", quantity), false);
		} catch (QuantityUnderrunException | OutOfStockException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void registerSaleInvalidQuantity() {
		String plateNumber = "AAA";
		int productId = 1; 
		int quantity = -1;
		
		saleController.createSale(plateNumber);
		
		assertThrows(QuantityUnderrunException.class,
				() -> saleController.addProduct(productId, "name", quantity));
	}
	
	@Test
	void registerSaleRemoveProduct() {
		String plateNumber = "AAA";
		int productId = 1;
		int quantity = 1;
		
		saleController.createSale(plateNumber);
		
		try {
			saleController.addProduct(productId, "", quantity);
		} catch (QuantityUnderrunException | OutOfStockException e) {
			e.printStackTrace();
		}
		
		saleController.removeProduct(new int[] {0});
		
		assertEquals(0, saleController.getProducts().size());
	}
	
	@Test
	void registerSaleCancelSaleTest() {
		String plateNumber = "AAA";
		int productId = 1; 
		int quantity = 1;
		
		saleController.createSale(plateNumber);
		
		try {
			saleController.addProduct(productId, "", quantity);
		} catch (QuantityUnderrunException | OutOfStockException e) {
			e.printStackTrace();
		}
		
		saleController.cancelSale();
		
		assertNull(saleController.getSale());
	}

}
