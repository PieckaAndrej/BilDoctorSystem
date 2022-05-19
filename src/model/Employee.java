package model;

import java.math.BigDecimal;

public class Employee extends Person{
	
	private BigDecimal salary;
	private String cpr;

	public Employee(String name, String surname, String address, String city,
			String zipcode, String phoneNumber) {
		
		super(name, surname, address, city, zipcode, phoneNumber);
	}
	
	public Employee(String name, String surname, String address, String city,
			String zipcode, String phoneNumber, BigDecimal salary, String cpr) {
		
		super(name, surname, address, city, zipcode, phoneNumber);
		this.salary = salary;
		this.cpr = cpr;
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
	 * @param cpr the cpr to set
	 */
	public void setCpr(String cpr) {
		this.cpr = cpr;
	}
}
