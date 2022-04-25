package dal;

import exceptions.DatabaseAccessException;
import model.OrderLine;

public interface SaleOrderLineDBIF {
	public boolean insertOrderLine(OrderLine orderLineine) throws DatabaseAccessException;
}
