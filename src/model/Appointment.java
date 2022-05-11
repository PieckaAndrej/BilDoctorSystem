package model;

import java.time.LocalDateTime;

public class Appointment {
	private int id;
	private LocalDateTime creationDate;
	private double length;
	private LocalDateTime appointmentDate;
	private String description;
	private String customerPhoneNo;
	private String customerName;
	private Employee employee;
	
	public Appointment(double length, LocalDateTime appointmentDate) {
		super();
		this.creationDate = LocalDateTime.now();
		this.length = length;
		this.appointmentDate = appointmentDate;
	}

	
	

	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public LocalDateTime getCreationDate() {
		return creationDate;
	}




	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}




	public String getCustomerPhoneNo() {
		return customerPhoneNo;
	}




	public void setCustomerPhoneNo(String customerPhoneNo) {
		this.customerPhoneNo = customerPhoneNo;
	}




	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public LocalDateTime getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDateTime appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
