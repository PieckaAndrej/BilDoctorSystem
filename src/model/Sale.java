package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

//Barnabas doing this do not touch
public class Sale {
	private String description;
	private LocalDateTime date;
	private ArrayList<OrderLine> orderlines;
	private Vehicle vehicle;
	private int id;
	
	/**
	 * Constructor for the Sale class
	 * @param Vehicle
	 */
	public Sale(Vehicle vehicle) {
		orderlines = new ArrayList<>();
		this.setVehicle(vehicle);
	}
	/**
	 * Add service to the sale
	 * @param Service
	 * @return
	 */
	public boolean addService(Service service) {
		return true;
	}
	/**
	 * Add orderline to the sale
	 * @param OrderLine
	 * @return
	 */
	public boolean addOrderLine(OrderLine orderline) {
		return orderlines.add(orderline);
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
