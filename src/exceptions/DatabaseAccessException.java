package exceptions;

public class DatabaseAccessException extends Exception {
	public static final String CONNECTION_MESSAGE = "The connection has failed";
	
	private String message;
	
	public DatabaseAccessException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
