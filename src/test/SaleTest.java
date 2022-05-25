package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.SaleController;
import dal.DbConnection;
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
	
	private Product testProduct;
	
	private CleanDatabase productCleaner;
	private CleanDatabase saleCleaner;
	private CleanDatabase orderLineCleaner;
	private CleanDatabase serviceCleaner;
	
	void initCleaners() {
		productCleaner = Cleaners.getProductCleaner();

		saleCleaner = Cleaners.getSaleCleaner();
		
		orderLineCleaner = Cleaners.getOrderLineCleaner();
		
		serviceCleaner = Cleaners.getServiceCleaner();
	}
	
	@BeforeEach
	void setUp() throws DatabaseAccessException {
		initCleaners();
		saleController = new SaleController();
		productDB = new ProductDB();
		saleDB = new SaleDB();
		
		try {
			serviceCleaner.setUp();
			orderLineCleaner.setUp();
			productCleaner.setUp();
			saleCleaner.setUp();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		testProduct = new Product(10, 10, 0, "test product");
		
		ProductDB productDb = new ProductDB();
		productDb.insertProduct(testProduct);
	}
	
	@AfterEach
	void cleanUp() {
		try {
			productCleaner.retrieveDatabase();
			saleCleaner.retrieveDatabase();
			serviceCleaner.retrieveDatabase();
			orderLineCleaner.retrieveDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testRegisterSaleShouldReturnTrue() {
		String plateNumber = "AAA";
		int productId = 1;
		int productQuantity = 2;
		String description = "test";
		
		boolean result = false;
		String resultPlate = "";
		int resultQuantity = 0;
		String resultDescription = "";
		int resultProductId = 0;
		int resultProductQuantity = 0;
		
		
		Product product = productDB.searchProduct(productId);
		
		assertTrue(saleController.createSale(plateNumber));
		assertTrue(saleController.addService(200, 10, description));
		
		try {
			result = saleController.addProduct(productId, product.getName(), productQuantity);
			
		} catch (QuantityUnderrunException | OutOfStockException e) {
			e.printStackTrace();
		}
		
		assertTrue(result);
		assertTrue(saleController.finishSale()); 
		
		try {
			ResultSet rs = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Sale").executeQuery();
			
			if (rs.next()) {
				resultPlate = rs.getString("plateNo");
			}
			
			rs = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM OrderLine").executeQuery();
			
			if (rs.next()) {
				resultQuantity = rs.getInt("quantity");
			}
			
			rs = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Service").executeQuery();
			
			if (rs.next()) {
				resultDescription = rs.getString("description");
			}
			
			rs = DbConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Product").executeQuery();
			
			if (rs.next()) {
				resultProductId = rs.getInt("id");
				resultProductQuantity = rs.getInt("currentStock");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		assertEquals(plateNumber, resultPlate);
		assertEquals(productQuantity, resultQuantity);
		assertEquals(description, resultDescription);
		assertEquals(testProduct.getId(), resultProductId);
		assertEquals(testProduct.getCurrentStock() - productQuantity, resultProductQuantity);
	}
	
	@Test
	void testInvalidPlateNumberShouldReturnFalse() {
		String plateNumber = "AAB";
		
		assertFalse(saleController.createSale(plateNumber));
	}
	
	@Test
	void testInvalidProductIdShouldReturnFalse() {
		String plateNumber = "AAA";
		int productId = 2045;
		int quantity = 1;
		boolean result = true;
		
		saleController.createSale(plateNumber);
	
		try {
			result = saleController.addProduct(productId, "name", quantity);
		} catch (QuantityUnderrunException | OutOfStockException e) {
			e.printStackTrace();
		}
		
		assertFalse(result);
	}
	
	@Test
	void testAddProductNegative1ShouldReturnException() {
		String plateNumber = "AAA";
		int productId = 1; 
		int quantity = -1;
		
		saleController.createSale(plateNumber);
		
		assertThrows(QuantityUnderrunException.class,
				() -> saleController.addProduct(productId, "name", quantity));
	}
	
	@Test
	void testAddProduct0from10QuantityShouldReturnException() {
		String plateNumber = "AAA";
		int productId = 1; 
		int quantity = 0;
		
		saleController.createSale(plateNumber);
		
		assertThrows(QuantityUnderrunException.class,
				() -> saleController.addProduct(productId, "name", quantity));
	}
	
	@Test
	void testAddProductQuantity1from10ShouldReturnTrue() {
		String plateNumber = "AAA";
		int productId = 1; 
		int quantity = 1;
		boolean result = false;
		
		saleController.createSale(plateNumber);
		
		try {
			result = saleController.addProduct(productId, "name", quantity);
		} catch (QuantityUnderrunException | OutOfStockException e) {
			e.printStackTrace();
		}
		
		assertTrue(result);
	}
	
	@Test
	void testAddProductQuantity9from10ShouldReturnTrue() {
		String plateNumber = "AAA";
		int productId = 1; 
		int quantity = 9;
		boolean result = false;
		
		saleController.createSale(plateNumber);
		
		try {
			result = saleController.addProduct(productId, "name", quantity);
		} catch (QuantityUnderrunException | OutOfStockException e) {
			e.printStackTrace();
		}
		
		assertTrue(result);
	}
	
	@Test
	void testAddProductQuantity10from10ShouldReturnTrue() {
		String plateNumber = "AAA";
		int productId = 1; 
		int quantity = 10;
		boolean result = false;
		
		saleController.createSale(plateNumber);
		
		try {
			result = saleController.addProduct(productId, "name", quantity);
		} catch (QuantityUnderrunException | OutOfStockException e) {
			e.printStackTrace();
		}
		
		assertTrue(result);
	}
	
	@Test
	void testAddProductQuantity11from10ShouldThrowException() {
		String plateNumber = "AAA";
		int productId = 1; 
		int quantity = 11;
		
		saleController.createSale(plateNumber);
		
		assertThrows(OutOfStockException.class,
				() -> saleController.addProduct(productId, "name", quantity));
	}
	
	@Test
	void testAddProductQuantity0from0ShouldThrowException() {
		Product testProduct = new Product(0, 10, 0, "test product");
		
		ProductDB productDb = new ProductDB();
		productDb.insertProduct(testProduct);
		
		String plateNumber = "AAA";
		int productId = 2; 
		int quantity = 0;
		
		saleController.createSale(plateNumber);
		
		assertThrows(QuantityUnderrunException.class,
				() -> saleController.addProduct(productId, "name", quantity));
	}
	
	@Test
	void testAddProductQuantity1from0ShouldThrowException() {
		Product testProduct = new Product(0, 10, 0, "test product");
		
		ProductDB productDb = new ProductDB();
		productDb.insertProduct(testProduct);
		
		String plateNumber = "AAA";
		int productId = 2; 
		int quantity = 1;
		
		saleController.createSale(plateNumber);
		
		assertThrows(OutOfStockException.class,
				() -> saleController.addProduct(productId, "name", quantity));
	}
	
	@Test
	void testAddProductQuantityNegative1from0ShouldThrowException() {
		Product testProduct = new Product(0, 10, 0, "test product");
		
		ProductDB productDb = new ProductDB();
		productDb.insertProduct(testProduct);
		
		String plateNumber = "AAA";
		int productId = 2; 
		int quantity = -1;
		
		saleController.createSale(plateNumber);
		
		assertThrows(QuantityUnderrunException.class,
				() -> saleController.addProduct(productId, "name", quantity));
	}
	
	@Test
	void testRemoveProductShouldRemoveProduct() {
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
	void testCancelSaleShouldMakeCurrentSaleNull() {
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
