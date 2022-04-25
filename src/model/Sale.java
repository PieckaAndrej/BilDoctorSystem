package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

//Barnabas doing this do not touch
public class Sale {
	private String description;
	private LocalDateTime date;
	private ArrayList<OrderLine> orderlines;
	private Vehicle vehicle;
	
	/**
	 * Constructor for the Sale class
	 * @param Vehicle
	 */
	public Sale(Vehicle vehicle) {
		orderlines = new ArrayList<>();
		this.vehicle = vehicle;
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
}
