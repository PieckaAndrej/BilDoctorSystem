package model;

import java.math.BigDecimal;

public class Employee extends Person{
	
	private BigDecimal salary;

	public Employee(String name, String surname, String address, String city, String zipcode, String phoneNumber) {
		super(name, surname, address, city, zipcode, phoneNumber);
		
	}
	
	public Employee(String name, String surname, String address, String city, String zipcode, String phoneNumber, BigDecimal salary) {
		super(name, surname, address, city, zipcode, phoneNumber);
		this.salary = salary;
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

	
}
