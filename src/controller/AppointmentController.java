package controller;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import dal.AppointmentDB;
import exceptions.DatabaseAccessException;
import exceptions.LengthUnderrunException;
import exceptions.QuantityUnderrunException;
import model.Appointment;
import model.Employee;

public class AppointmentController {
	private PersonController personCtrl;
	private AppointmentDB appointmentdb;
	private Appointment currentAppointment;
	
	public AppointmentController() {
		personCtrl = new PersonController();
		appointmentdb = new AppointmentDB();
	}
	
	/**
	 * Get all employees from the person controller
	 * @return ArrayList of all employees
	 * @throws DatabaseAccessException
	 */
	public ArrayList<Employee> getAllEmployees() throws DatabaseAccessException{
		return personCtrl.getAllEmployees();
	}
	
	/**
	 * Creates appointment
	 * @param date
	 * @param length
	 * @param description
	 * @throws DatabaseAccessException 
	 */
	public boolean createAppointment(LocalDateTime date, int length, String description) throws DatabaseAccessException, LengthUnderrunException{
		boolean retVal = false;
		boolean correctAppointment;
		ArrayList<Appointment> appointments = new ArrayList<>();
		appointments = appointmentdb.getAllAppointments(date);
		
		if(length > 0)
		{
			for(int i = 0; i < appointments.size(); i++)
			{
				/*if()
				{
					correctAppointment = false;
					break;
				}*/
			}
			
			if(correctAppointment = true)
			{
				currentAppointment = new Appointment(date, length, description);
				retVal = true;
			}
		}
		else
		{
			throw new LengthUnderrunException(length);
		}
		return retVal;
	}
	
	/**
	 * @param name
	 * @param phoneNo
	 * @return boolean true if Customer info was added for appointment
	 */
	public boolean addCustomerInfo(String name, String phoneNo) {
		boolean retVal = false;
		if(currentAppointment != null) {
			currentAppointment.addCustomerInfo(name, phoneNo);
			retVal = true;
		}
		return retVal;
	}
	
	/**
	 * @param employee
	 * @return boolean true if Employee added
	 */
	public boolean addEmployee(Employee employee) {
		boolean retVal = false;
		if(currentAppointment != null) {
			currentAppointment.addEmployee(employee);
			retVal = true;
		}
		return retVal;
	}
	
	/**
	 * Insert appointment into the database if its filled;
	 * @return boolean
	 * @throws DatabaseAccessException
	 */
	public boolean finishAppointment() throws DatabaseAccessException {
		boolean retVal = false;
		if(currentAppointment.isFilled()) {
			try {
				appointmentdb.insertAppointment(currentAppointment);
				currentAppointment = null;
				retVal = true;
			}
			catch(DatabaseAccessException e) {
				e.printStackTrace();
				throw new DatabaseAccessException(DatabaseAccessException.CONNECTION_MESSAGE);
			}	
		}
		return retVal; 
	}
}
