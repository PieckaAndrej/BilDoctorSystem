package model;

public class Employee extends Person{
	
	private double salary;

	public Employee(String name, String address, String city, String zipcode, String phoneNumber) {
		super(name, address, city, zipcode, phoneNumber);
		
	}
	
	public Employee(String name, String address, String city, String zipcode, String phoneNumber, double salary) {
		super(name, address, city, zipcode, phoneNumber);
		this.salary = salary;
	}

	/**
	 * Get salary
	 * 
	 * @return salary as double
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * Set salary
	 * 
	 * @param salary as double
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

	
}
