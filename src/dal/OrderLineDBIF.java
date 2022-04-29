package dal;

import exceptions.DatabaseAccessException;
import model.OrderLine;
import model.Sale;

public interface OrderLineDBIF {
	public boolean insertOrderLine(OrderLine orderline, Sale sale) throws DatabaseAccessException;
}
