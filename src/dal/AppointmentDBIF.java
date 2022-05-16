package dal;

import java.util.ArrayList;

import exceptions.DatabaseAccessException;
import model.Appointment;

public interface AppointmentDBIF {
	 boolean insertAppointment(Appointment a) throws DatabaseAccessException;
	 ArrayList<Appointment> getAllAppointments() throws DatabaseAccessException;
}
