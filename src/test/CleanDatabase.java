package test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dal.DbConnection;

public abstract class CleanDatabase {
	private String tableName;
	private ResultSet databaseBuffer;
	
	private static final String SELECT_QUERY = "SELECT * FROM ";
	private String retrieveQuery;
	private boolean hasIdentity;
	
	public CleanDatabase(String tableName, String retrieveQuery) {
		this(tableName, retrieveQuery, false);
	}
	
	public CleanDatabase(String tableName, String retrieveQuery, boolean hasIdentity) {
		this.tableName = tableName;
		this.retrieveQuery = retrieveQuery;
		this.hasIdentity = hasIdentity;
	}
	
	public void setUp() throws SQLException {
		getDatabaseRows();
		cleanDatabase();
	}
	
	public void getDatabaseRows() throws SQLException {
		PreparedStatement stmt = DbConnection.getInstance()
				.getConnection().prepareStatement(SELECT_QUERY + tableName);
		
		databaseBuffer = stmt.executeQuery();
	}
	
	public void cleanDatabase() throws SQLException {
		PreparedStatement stmt = DbConnection.getInstance()
				.getConnection().prepareStatement("DELETE FROM " + tableName);
		
		stmt.executeUpdate();
		
		if (hasIdentity) {
			resetIdentity();
		}
	}
	
	public void retrieveDatabase() throws SQLException {
		cleanDatabase();

		while (databaseBuffer.next()) {
			PreparedStatement stmt = DbConnection.getInstance()
					.getConnection().prepareStatement(retrieveQuery);
			
			insertPreparedStatement(stmt, databaseBuffer);
			
			stmt.executeUpdate();
		}
	}
	
	public void resetIdentity() throws SQLException {
		PreparedStatement identity = DbConnection.getInstance()
				.getConnection().prepareStatement("DBCC CHECKIDENT (" + tableName + ", RESEED, 0)");
		
		identity.executeUpdate();
	}
	
	public abstract void insertPreparedStatement(PreparedStatement stmt, ResultSet object);
}
