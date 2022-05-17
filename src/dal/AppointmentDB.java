package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import exceptions.DatabaseAccessException;
import model.Appointment;

public class AppointmentDB implements AppointmentDBIF {
	
	private static final String CREATE_STATEMENT = "INSERT INTO Appointment(creationDate, length, date, description, employeePhoneNo) VALUES(?, ?, ?, ? ,?)";
	private PreparedStatement createStatement;
	private static final String GET_APPOINTMENTS_STATEMENT = "SELECT * FROM Appointment WHERE date LIKE ?";
	private PreparedStatement getAppointmentsStatement;
		
	public AppointmentDB() {
	}


	/**
	 * Insert appointment to the appointment database
	 * @param a Appointment to be inserted
	 * @return True if the appointment was inserted successfully
	 */
	@Override
	public boolean insertAppointment(Appointment a) 
			throws DatabaseAccessException {
		boolean retVal = false;
		
		try {
			
			// Create statement
			try {
				createStatement = DbConnection.getInstance().getConnection()
						.prepareStatement(CREATE_STATEMENT, Statement.RETURN_GENERATED_KEYS);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DatabaseAccessException(DatabaseAccessException.CONNECTION_MESSAGE);
			}
			
			// Start transaction
			DbConnection.getInstance().startTransaction();
			
			
			// Insert appointment
			createStatement.setTimestamp(1, Timestamp.valueOf(a.getCreationDate()));
			createStatement.setDouble(2, a.getLength());
			createStatement.setTimestamp(3, Timestamp.valueOf(a.getAppointmentDate()));
			createStatement.setString(4, a.getDescription());
			createStatement.setString(5, a.getEmployee().getPhoneNumber());
			a.setId(DbConnection.getInstance().executeSqlInsertWithIdentity(createStatement));
			
		
			// Commit
			System.out.println("Commited");
			DbConnection.getInstance().commitTransaction();
			
			retVal = true;
			
		} catch (SQLException e) {
			// Roll back
			System.out.println("Rolled back");
			DbConnection.getInstance().rollbackTransaction();
			
			throw new DatabaseAccessException(e.getMessage());
		}

		return retVal;
	}


	@Override
	public ArrayList<Appointment> getAllAppointments() throws DatabaseAccessException {
		ArrayList<Appointment> appointments = new ArrayList<>();
		
		Appointment currentAppointment = null;
		
		try {
			getAppointmentsStatement = DbConnection.getInstance().getConnection().prepareStatement(GET_APPOINTMENTS_STATEMENT);
			
			// To do
			getAppointmentsStatement.setString(1, null);
			
			ResultSet rs = getAppointmentsStatement.executeQuery();
			
			while(rs.next()) {
				currentAppointment = buildObject(rs);
				appointments.add(currentAppointment);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return appointments;
	}
	
	private Appointment buildObject(ResultSet rs) throws SQLException {
		return new Appointment(rs.getTimestamp("date").toLocalDateTime(), rs.getInt("length"), rs.getString("description"));
	}


	/**
	 * @return the createStatement
	 */
	public static String getCreateStatement() {
		return CREATE_STATEMENT;
	}

}
