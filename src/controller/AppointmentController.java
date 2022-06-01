package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import dal.AppointmentDB;
import exceptions.DatabaseAccessException;
import exceptions.LengthUnderrunException;
import model.Appointment;
import model.Employee;
import model.Vehicle;

public class AppointmentController {
	private PersonController personCtrl;
	private AppointmentDB appointmentdb;
	private Appointment currentAppointment;
	private VehicleController vehicleController;
	
	public AppointmentController() {
		personCtrl = new PersonController();
		vehicleController = new VehicleController();
		appointmentdb = new AppointmentDB();
	}
	
	/**
	 * Get all employees from the person controller
	 * @return ArrayList of all employees
	 * @throws DatabaseAccessException
	 */
	public List<Employee> getAllEmployees() throws DatabaseAccessException{
		return personCtrl.getAllEmployees();
	}
	
	/**
	 * Creates appointment
	 * @param date
	 * @param length
	 * @param description
	 * @throws DatabaseAccessException 
	 */
	public boolean createAppointment(LocalDateTime date, int length, String description)
			throws DatabaseAccessException, LengthUnderrunException {
		boolean retVal = false;
		
		// Length cannot be negative or zero
		if (length <= 0) {
			throw new LengthUnderrunException(length);
		}
		
		// Get all appointments on the date from the database
		List<Appointment> appointments = new ArrayList<>();
		appointments = appointmentdb.getAppointmentsOnDate(date);
		
		boolean overlaps = appointments.parallelStream()			
			.filter(a -> 
					(
						// Check if the date is starting in the already existing appointment
						// Boolean expression is ((existing < date) || (date == existing)) && (date < existingEnd)
						
						// If date is after the existing appointment...
						(date.isAfter(a.getAppointmentDate()) ||
						// Or is starting at the same time as existing appointment
						date.isEqual(a.getAppointmentDate())) &&
					
						// And the date starts before the existing appointment ends
						// ...then the appointment will stay
						date.isBefore(a.getAppointmentDate().plusMinutes((long) a.getLength()))
					
						
					) || (
						// Check if the date is ending in the already existing appointment
						// Boolean expression is (existing < dateEnd) && (dateEnd < existingEnd)
						
						// If date ending is after existing appointment start...
						date.plusMinutes(length).isAfter(a.getAppointmentDate()) &&
							
						// And date ending is before existing end
						// ...then the appointment will stay
						date.plusMinutes(length).isBefore(
						a.getAppointmentDate().plusMinutes((long) a.getLength()))
					) || (
						// Check if the date contains inside already existing appointment
						// Boolean expression is (date < existing) && (existing < dateEnd)
							
						// If date starts before the appointment starts...
						date.isBefore(a.getAppointmentDate()) &&
							
						// And date ending is after the appointment starts
						// ...then the appointment will stay
						date.plusMinutes(length).isAfter(
							a.getAppointmentDate()))
			)
			// If no appointments stayed, then it doesn't overlaps with the date
			.count() != 0;
		
		// If doesn't overlap, make new appointment
		if (!overlaps) {
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
		
		if (currentAppointment != null) {
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
		
		if (currentAppointment != null) {
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
		
		if (currentAppointment != null) {
			if (currentAppointment.isFilled()) {
				try {
					appointmentdb.insertAppointment(currentAppointment);
					notifyCustomerOfAppointment();
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
	
	/**
	 * Cancel current appointment
	 */
	public void cancelAppointment() {
		currentAppointment = null;
	}
	
	/**
	 * Get all appointments on a day
	 * @param date The date that appointments are retrieved on
	 * @return List of appointments with the same date
	 * @throws DatabaseAccessException
	 */
	public List<Appointment> getAppointmentsOnDay(LocalDateTime date) throws DatabaseAccessException {
		return appointmentdb.getAppointmentsOnDate(date);
	}
	
	/**
	 * Get all vehicles from the controller
	 * @return ArrayList of all vehicles
	 */
	public ArrayList<Vehicle> getAllVehicles() {
		return vehicleController.getAllVehicles();
	}
	
	/**
	 * Get current appointment
	 * @return Current appointment as Appointment
	 */
	public Appointment getCurrentAppointment() {
		return currentAppointment;
	}
	
	public void notifyCustomerOfAppointment() {
		//TODO notify customer about the appointment with SMS message sent to his phone
		//currentAppointment.getCustomerPhoneNo();
	}
	
	/**
	 * Change vehicle check up date to a new one
	 * @param plateNumber Plate number of the vehicle
	 * @param newCheckUpDate New check up date
	 */
	public void makeVehicleCheckUp(String plateNumber, LocalDate newCheckUpDate) {
		VehicleController v = new VehicleController();
		
		v.updateCheckUpDate(plateNumber, newCheckUpDate);
	}
}
