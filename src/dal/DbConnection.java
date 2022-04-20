package dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

public class DbConnection {
	
	private static DbConnection instance = new DbConnection();
	private Connection conn;
	
	/**
	 * Constructor for the DbConnection
	 */
	private DbConnection() {
		try {
			SQLServerDataSource ds = new SQLServerDataSource();
			ds.setEncrypt(false);
			ds.setPortNumber(1433);
			ds.setUser("CSC-CSD-S211_10407543");
			ds.setPassword("Password1!");
			ds.setServerName("hildur.ucn.dk");
			ds.setDatabaseName("CSC-CSD-S211_10407543");
			conn = ds.getConnection();

			
		} catch (SQLException e) {

			e.printStackTrace();
		}	
	}
	
	/**
	 * @return the instance of DbConnection
	 */
	public static DbConnection getInstance() {
		return instance;		
	}
	
	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return conn;
	}
	
	/**
	 * Prints out the information of the connection
	 * @param conn the connection to print out information about
	 */
	public void printSessionInfo(Connection conn) {
		
		PreparedStatement selectSessionInfo = null;
		
		try {
			selectSessionInfo = conn.prepareStatement("select @@SPID");
			ResultSet sessionInfoRows = selectSessionInfo.executeQuery();
			sessionInfoRows.next();
			System.out.println(Thread.currentThread().getName()+ " - Session: "+ sessionInfoRows.getInt(1) + ", IsolationLevel: "+ conn.getTransactionIsolation());	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Executes insert and gets the generated key of it
	 * @param ps the prepared statement to execute
	 * @return the generated key
	 */
	public int executeSqlInsertWithIdentity(PreparedStatement ps) {
		int res = -1;
		try {
			res = ps.executeUpdate();
			if (res > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				res = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Sets auto commit to false and starts the transaction
	 */
	public void startTransaction() {
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Commits transaction and sets the auto commit to true
	 */
	public void commitTransaction() {
		try {
			try {
				conn.commit();
			} catch (SQLException e) {
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rollbacks transaction and sets the auto commit to true
	 */
	public void rollbackTransaction() {
		try {
			try {
				conn.rollback();
			} catch (SQLException e) {
				throw e;
			} finally {
				conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Sets the isolation level of the transaction
	 * @param level the level to set to
	 */
	public void setTransactionIsolation(int level) {
		try {
			conn.setTransactionIsolation(level);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}