package controller;
//Barnabas doing this do not touch

import exceptions.DatabaseAccessException;
import model.OrderLine;
import model.Product;
import model.Sale;
import model.Service;
import model.Vehicle;

import java.time.LocalDateTime;

import dal.SaleDB;
import dal.SaleDBIF;
import dal.SaleOrderLineDB;
import dal.SaleOrderLineDBIF;

public class SaleController {
	private ProductController productController;
	private ServiceController serviceController;
	private VehicleController vehicleController;
	private SaleDBIF saleDb;
	private Sale sale;
	private SaleOrderLineDB saleOrderLineDb;
	private SaleOrderLineDBIF saleOrderLineDbif;
	
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
	
	public boolean addProduct(int productId, int quantity) {
		boolean retVal = false;
		Product product = productController.searchProduct(productId);
		if(product != null) {
			sale.addProduct(product);
			retVal = true;
		}
		return retVal;
	}
	
	public boolean finishSale() {
		boolean retVal = false;
		sale.setDate(LocalDateTime.now());
		
		try {
			saleDb.insertSale(sale);
			for(OrderLine element:sale.getOrderLines()) {
				saleOrderLineDb.insertOrderLine(element, sale);
			}
			sale = null;
			retVal = true;
			
		} catch (DatabaseAccessException e) {
			e.printStackTrace();
		}
		return retVal;
	}
}
