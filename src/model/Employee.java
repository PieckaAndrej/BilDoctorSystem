package model;

import java.math.BigDecimal;

public class Employee extends Person{
	
	private BigDecimal salary;
	private String cpr;
	private String position;

	public Employee(String name, String surname, String address, String city,
			String zipcode, String phoneNumber, String countryCode) {
		
		super(name, surname, address, city, zipcode, phoneNumber, countryCode);
	}
	
	public Employee(String name, String surname, String address, String city,
			String zipcode, String phoneNumber, String countryCode, BigDecimal salary,
			String cpr, String position) {
		
		super(name, surname, address, city, zipcode, phoneNumber, countryCode);
		this.salary = salary;
		this.cpr = cpr;
		this.position = position;
	}

	/**
	 * Get salary
	 * 
	 * @return salary as double
	 */
	public BigDecimal getSalary() {
		return salary;
	}

	/**
	 * Set salary
	 * 
	 * @param salary as double
	 */
	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	/**
	 * @return the cpr
	 */
	public String getCpr() {
		return cpr;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @param cpr the cpr to set
	 */
	public void setCpr(String cpr) {
		this.cpr = cpr;
	}
}
