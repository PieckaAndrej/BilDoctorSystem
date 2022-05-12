package dal;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import exceptions.DatabaseAccessException;
import model.Appointment;
import model.OrderLine;
import model.Product;
import model.Service;

public class AppointmentDB implements AppointmentDBIF {
	
	private static final String CREATE_STATEMENT = "INSERT INTO Appointment(creationDate, length, date, description, employeePhoneNo) VALUES(?, ?, ?, ? ,?)";
	private PreparedStatement createStatement;
		
	public AppointmentDB() {
		super();
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

}
