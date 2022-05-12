package controller;

import java.time.LocalDateTime;
import java.util.List;

import dal.SaleDB;
import dal.SaleDBIF;
import exceptions.DatabaseAccessException;
import exceptions.OutOfStockException;
import exceptions.QuantityUnderrunException;
import model.OrderLine;
import model.Product;
import model.Sale;
import model.Service;
import model.Vehicle;

public class SaleController {
	private ProductController productController;
	private ServiceController serviceController;
	private VehicleController vehicleController;
	private SaleDBIF saleDb;
	private Sale sale;
	
	public SaleController() throws DatabaseAccessException {
		saleDb = new SaleDB();
		productController = new ProductController();
		serviceController = new ServiceController();
		vehicleController = new VehicleController();
		
	}
	
	/**
	 * Create new sale with the plate number
	 * @param plateNumber A plate number of vehicle that is used in the sale
	 * @return True if the creation was successful
	 */
	public boolean createSale(String plateNumber) {
		boolean retVal = false;
		Vehicle vehicle = vehicleController.searchVehicle(plateNumber);
		
		if (vehicle != null) {
			sale = new Sale(vehicle);
			retVal = true;
		}
		return retVal;
	}
	
	/**
	 * Add service to the sale
	 * @param cost Cost of the service
	 * @param time Time that the service takes
	 * @param description Description of the service
	 * @return True if the service was added successfully 
	 */
	public boolean addService(double cost, double time, String description) {
		boolean retVal = false;
		Service service = serviceController.createService(cost, time, description);
		if (service != null) {
			sale.addService(service);
			retVal = true;
		}
		return retVal;
	}
	
	/**
	 * Add product to the sale
	 * @param productId The id of the product
	 * @param name The name of the product
	 * @param quantity The quantity of the product
	 * @return True if the product was added successfully 
	 * @throws QuantityUnderrunException
	 * @throws OutOfStockException
	 */
	public boolean addProduct(int productId, String name, int quantity)
			throws QuantityUnderrunException, OutOfStockException {
		boolean retVal = false;
		Product product = productController.searchProduct(productId);
		
		if (product != null) {
			if (product.getCurrentStock() - quantity <= 0) {
				throw new OutOfStockException(product.getCurrentStock(), quantity);
			}
			
			OrderLine orderLine = new OrderLine(quantity, product);
			sale.addOrderLine(orderLine);
			retVal = true;
		}
		return retVal;
	}
	
	/**
	 * Remove services from the sale
	 * @param array Array containing the indexes that are being removed
	 * where they are sorted from biggest to smallest
	 */
	public void removeService(int[] array) {
		sale.removeService(array);

	}
	
	/**
	 * Remove products from the sale
	 * @param array Array containing the indexes that are being removed
	 * where they are sorted from biggest to smallest
	 */
	public void removeProduct(int[] array) {
		sale.removeProduct(array);
		
	}
	
	/**
	 * Finish sale and current one to null
	 * @return True if the operation was performed successfully
	 */
	public boolean finishSale() {
		boolean retVal = false;
		sale.setDate(LocalDateTime.now());
		
		try {
			saleDb.insertSale(sale);

			sale = null;
			retVal = true;
			
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	/**
	 * Get all products from the database
	 * @return List of all products from the database
	 */
	public List<Product> getAllProducts() {
		return productController.getAllProducts();
	}
	
	/**
	 * Get all products in the current sale
	 * @return List of all products in the current sale
	 */
	public List<Product> getProducts() {
		return sale.getOrderLines()
				.parallelStream().map(o -> o.getProduct())
				.toList();
	}
	
	/**
	 * Cancel sale
	 */
	public void cancelSale() {
		sale = null;
	}
	
	/**
	 * Get current sale
	 * @return current sale
	 */
	public Sale getSale() {
		return sale;
	}
}
