package test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dal.DbConnection;

public class TestDatabaseConnection {

	Connection con;

	@BeforeEach
	public void setUp() {
		con = DbConnection.getInstance().getConnection();
	}
	
	
	@Test
	public void wasConnected() {
		assertNotNull(con, "Connected - connection cannot be null");	
	}

}
