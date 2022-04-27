package dal;

import exceptions.DatabaseAccessException;
import model.Sale;
import model.Service;

public interface ServiceDBIF {
	public boolean insertService(Service service, Sale sale) throws DatabaseAccessException;
}
