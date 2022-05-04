package controller;
//Barnabas doing this do not touch

import java.time.LocalDateTime;

import dal.SaleDB;
import dal.SaleDBIF;
import exceptions.DatabaseAccessException;
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
	
	public boolean createSale(String plateNumber) {
		boolean retVal = false;
		Vehicle vehicle = vehicleController.searchVehicle(plateNumber);
		
		if (vehicle != null) {
			sale = new Sale(vehicle);
			retVal = true;
		}
		return retVal;
	}
	
	public boolean addService(double cost, double time, String description) {
		boolean retVal = false;
		Service service = serviceController.createService(cost, time, description);
		if(service != null) {
			sale.addService(service);
			retVal = true;
		}
		return retVal;
	}
	
	public boolean addProduct(int productId, String name, int quantity) {
		boolean retVal = false;
		Product product = productController.searchProduct(productId);
		if(product != null) {
			OrderLine orderLine = new OrderLine(quantity, product);
			sale.addOrderLine(orderLine);
			retVal = true;
		}
		return retVal;
	}
	
	public void removeService(int[] array) {
		sale.removeService(array);

	}
	
	public void removeProduct(int[] array) {
		sale.removeProduct(array);
		
	}
	
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
}
