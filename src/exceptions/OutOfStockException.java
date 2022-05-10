package exceptions;

public class OutOfStockException extends Exception {
	private int currentStock;
	private int quantity;
	
	public OutOfStockException(int currentStock, int quantity) {
		this.currentStock = currentStock;
		this.quantity = quantity;
	}
	
	public String getMessage() {
		return String.format("Not enough in stock %s/%s", quantity, currentStock);
	}
}
