package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

//Barnabas doing this do not touch
public class Sale {
	private String description;
	private LocalDateTime date;
	private ArrayList<OrderLine> orderLines;
	private Vehicle vehicle;
	private int id;
	private ArrayList<Service> services;
	
	/**
	 * Constructor for the Sale class
	 * @param Vehicle
	 */
	public Sale(Vehicle vehicle) {
		orderLines = new ArrayList<>();
		this.setVehicle(vehicle);
		services = new ArrayList<>();
	}
	
	/**
	 * Add service to the sale
	 * @param Service
	 * @return
	 */
	public boolean addService(Service service) {
		boolean retVal = false;
		
		if (services != null) {
			services.add(service);
			retVal = true;
		}
		return retVal;
	}
	
	public boolean isVehiclePresent(Vehicle vehicle) {
		boolean found = false;
		int index = 0;
		
		while(index < orderLines.size() && !found) {
			if(((OrderLine)orderLines.get(index)).getPlateNumber() == vehicle.getPlateNumber()) {
				found = true;
			}
			else {
				index++;
			}
		}
		return found;
	}
	
	public void removeService(int[] array) {
		for(int i = 0; i < array.length ; i++) {
			services.remove(array[i]);
		}
	}
	
	public void removeProduct(int[] array) {
		for(int i = 0; i < array.length ; i++) {
			orderLines.remove(array[i]);
		}
	}
		
	/**
	 * Add orderline to the sale
	 * @param OrderLine
	 * @return
	 */
	public boolean addOrderLine(OrderLine orderline) {
		return orderLines.add(orderline);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * @return the orderLines
	 */
	public ArrayList<OrderLine> getOrderLines() {
		return new ArrayList<>(orderLines);
	}

	/**
	 * @param orderLines the orderLines to set
	 */
	public void setOrderLines(ArrayList<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	/**
	 * @return the vehicle
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}

	/**
	 * @param vehicle the vehicle to set
	 */
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the services
	 */
	public ArrayList<Service> getServices() {
		return new ArrayList<>(services);
	}

	/**
	 * @param services the services to set
	 */
	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}

}
