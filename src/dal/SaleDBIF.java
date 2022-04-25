package dal;

import exceptions.DatabaseAccessException;
import model.Sale;

public interface SaleDBIF {
	public boolean insertSale(Sale sale) throws DatabaseAccessException;
}
