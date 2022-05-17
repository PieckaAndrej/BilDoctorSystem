package dal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import exceptions.DatabaseAccessException;
import model.Appointment;

public interface AppointmentDBIF {
	 boolean insertAppointment(Appointment a) throws DatabaseAccessException;
	 ArrayList<Appointment> getAllAppointments(LocalDateTime date) throws DatabaseAccessException;
}
