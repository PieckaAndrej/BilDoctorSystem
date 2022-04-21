package model;

public class Service {
	
	private double cost;
	private double time;
	private String description;
	
	public Service(double cost, double time, String dsc) {
		this.cost = cost;
		this.time = time;
		this.description = description;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
