package dal;

import java.time.LocalDateTime;
import java.util.List;

import exceptions.DatabaseAccessException;
import model.Appointment;

public interface AppointmentDBIF {
	 boolean insertAppointment(Appointment a) throws DatabaseAccessException;
	 List<Appointment> getAppointmentsOnDate(LocalDateTime date) throws DatabaseAccessException;
}
