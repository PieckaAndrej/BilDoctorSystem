package model;

import java.time.LocalDateTime;

//Barnabas doing this do not touch
public class Sale {
	private String description;
	private LocalDateTime date;
	private List<OrderLine> orderlines;
	
	/**
	 * Constructor for the Sale class
	 * @param Vehicle
	 */
	public Sale(vehicle Vehicle) {
		orderLines = ArrayList<>();
		this.vehicle = vehicle;
	}
	/**
	 * Add service to the sale
	 * @param Service
	 * @return
	 */
	public boolean addService(service Service) {
		
	}
	/**
	 * Add orderline to the sale
	 * @param OrderLine
	 * @return
	 */
	public boolean addOrderLine(orderLine OrderLine) {
		return orderLines.add(orderLine);
	}
}
