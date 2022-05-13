package model;

import java.time.LocalDateTime;

/**
 * @author agore
 *
 */
/**
 * @author agore
 *
 */
public class Appointment {
	private int id;
	private LocalDateTime creationDate;
	private double length;
	private LocalDateTime appointmentDate;
	private String description;
	private String customerPhoneNo;
	private String customerName;
	private Employee employee;
	 
	public Appointment(LocalDateTime appointmentDatedouble, int length, String description) {
		this.creationDate = LocalDateTime.now();
		this.length = length;
		this.appointmentDate = appointmentDate;
		this.description = description;
	}
	
	
	/**
	 * Check if the appointment has been filled
	 */
	public boolean isFilled(){
		boolean retVal = false;
		if(customerName != null && customerPhoneNo!= null && employee!= null) {
			retVal = true;
		}
		return retVal;
	}
	
	/**
	 * @param name
	 * @param phoneNo
	 * Sets customer info to the appointment
	 */
	public void addCustomerInfo(String name, String phoneNo) {
		setCustomerName(name);
		setCustomerPhoneNo(phoneNo);
	}
	
	/**
	 * @param employee
	 * Set employee to the appointment
	 */
	public void addEmployee(Employee employee) {
		setEmployee(employee);
	}

	/**
	 * Get id.
	 *
	 * @return id as int.
	 */
	public int getId() {
	    return id;
	}

	/**
	 * Set id.
	 *
	 * @param id the value to set.
	 */
	public void setId(int id) {
	    this.id = id;
	}

	/**
	 * Get creationDate.
	 *
	 * @return creationDate as LocalDateTime.
	 */
	public LocalDateTime getCreationDate() {
	    return creationDate;
	}

	/**
	 * Set creationDate.
	 *
	 * @param creationDate the value to set.
	 */
	public void setCreationDate(LocalDateTime creationDate) {
	    this.creationDate = creationDate;
	}

	/**
	 * Get length.
	 *
	 * @return length as double.
	 */
	public double getLength() {
	    return length;
	}

	/**
	 * Set length.
	 *
	 * @param length the value to set.
	 */
	public void setLength(double length) {
	    this.length = length;
	}

	/**
	 * Get appointmentDate.
	 *
	 * @return appointmentDate as LocalDateTime.
	 */
	public LocalDateTime getAppointmentDate() {
	    return appointmentDate;
	}

	/**
	 * Set appointmentDate.
	 *
	 * @param appointmentDate the value to set.
	 */
	public void setAppointmentDate(LocalDateTime appointmentDate) {
	    this.appointmentDate = appointmentDate;
	}

	/**
	 * Get description.
	 *
	 * @return description as String.
	 */
	public String getDescription() {
	    return description;
	}

	/**
	 * Set description.
	 *
	 * @param description the value to set.
	 */
	public void setDescription(String description) {
	    this.description = description;
	}

	/**
	 * Get customerPhoneNo.
	 *
	 * @return customerPhoneNo as String.
	 */
	public String getCustomerPhoneNo() {
	    return customerPhoneNo;
	}

	/**
	 * Set customerPhoneNo.
	 *
	 * @param customerPhoneNo the value to set.
	 */
	public void setCustomerPhoneNo(String customerPhoneNo) {
	    this.customerPhoneNo = customerPhoneNo;
	}

	/**
	 * Get customerName.
	 *
	 * @return customerName as String.
	 */
	public String getCustomerName() {
	    return customerName;
	}

	/**
	 * Set customerName.
	 *
	 * @param customerName the value to set.
	 */
	public void setCustomerName(String customerName) {
	    this.customerName = customerName;
	}

	/**
	 * Get employee.
	 *
	 * @return employee as Employee.
	 */
	public Employee getEmployee() {
	    return employee;
	}

	/**
	 * Set employee.
	 *
	 * @param employee the value to set.
	 */
	public void setEmployee(Employee employee) {
	    this.employee = employee;
	}
}
