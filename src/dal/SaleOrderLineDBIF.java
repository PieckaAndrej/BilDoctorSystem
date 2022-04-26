package dal;

import exceptions.DatabaseAccessException;
import model.OrderLine;
import model.Sale;

public interface SaleOrderLineDBIF {
	public boolean insertOrderLine(OrderLine orderLineine, Sale sale) throws DatabaseAccessException;
}
