package dal;

import java.time.LocalDateTime;

import exceptions.DatabaseAccessException;
import model.Sale;

public interface SaleDBIF {
	public Sale insertSale(Sale sale) throws DatabaseAccessException;
}
