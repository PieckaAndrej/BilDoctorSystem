package exceptions;

public class QuantityUnderrunException  extends Exception {
	private int quantity;
	
	public QuantityUnderrunException(int quantity) {
		this.quantity = quantity;
	}
	
	public String getMessage() {
		return String.format("Invalid quantity: %s", quantity);
	}
}
