package controller;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

import dal.AppointmentDB;
import exceptions.DatabaseAccessException;
import exceptions.LengthUnderrunException;
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
		
		// Length cannot be negative or zero
		if (length <= 0) {
			throw new LengthUnderrunException(length);
		}
		
		// Get all appointments on the date from the database
		ArrayList<Appointment> appointments = new ArrayList<>();
		appointments = appointmentdb.getAllAppointments(date);
		
		// Check if the date is starting in the already existing appointment
		boolean startsInExisting = appointments.parallelStream()
			// Boolean expression is ((existing < date) || (date == existing)) && (date < existingEnd)
			
			// If date is after the existing appointment...
			.filter(a -> (date.isAfter(a.getAppointmentDate()) ||
					// Or is starting at the same time as existing appointment
					date.isEqual(a.getAppointmentDate())) &&
					
					// And the date starts before the existing appointment ends
					// ...then the appointment will stay
					date.isBefore(a.getAppointmentDate().plusMinutes((long) a.getLength())))
			
			// If no appointments stayed, then the date doesn't start in existing one
			.count() != 0;
		
		// Check if the date is ending in the already existing appointment
		boolean endsInExisting = appointments.parallelStream()
			// Boolean expression is (existing < dateEnd) && (dateEnd < existingEnd)
				
			// If date ending is after existing appointment start...
			.filter(a -> date.plusMinutes(length).isAfter(a.getAppointmentDate()) &&
					
					// And date ending is before existing end
					// ...then the appointment will stay
					date.plusMinutes(length).isBefore(
							a.getAppointmentDate().plusMinutes((long) a.getLength())))
			
			// If no appointments stayed, then the date doesn't end in existing one
			.count() != 0;
		
		// Check if the date contains inside already existing appointment
		boolean overlapsExisting = appointments.parallelStream()
			// Boolean expression is (date < existing) && (existing < dateEnd)
				
			// If date starts before the appointment starts...
			.filter(a -> date.isBefore(a.getAppointmentDate()) &&
					
					// And date ending is after the appointment starts
					// ...then the appointment will stay
					date.plusMinutes(length).isAfter(
							a.getAppointmentDate()))
			
			// If no appointments stayed, then the date doesn't contain the start of existing one
			.count() != 0;
		
		// If they don't overlap, make new appointment
		if (!startsInExisting && !endsInExisting && !overlapsExisting) {
			currentAppointment = new Appointment(date, length, description);
			retVal = true;
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
	public boolean addEmployee(Employee employee) throws DatabaseAccessException {
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
		if(currentAppointment != null) {
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
		}
		return retVal; 
	}
	
	public void cancelAppointment()
	{
		currentAppointment = null;
	}
}
