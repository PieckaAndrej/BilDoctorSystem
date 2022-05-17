package exceptions;

public class LengthUnderrunException extends Exception {
	private int length;
	
	public LengthUnderrunException(int length) {
		this.length = length;
	}
	
	public String getMessage() {
		return String.format("Invalid length: %s", length);
	}
}
