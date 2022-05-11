package dal;

import exceptions.DatabaseAccessException;
import model.Appointment;

public interface AppointmentDBIF {
	 boolean insertAppointment(Appointment a) throws DatabaseAccessException;
}
