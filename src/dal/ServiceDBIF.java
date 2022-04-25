package dal;

import exceptions.DatabaseAccessException;
import model.Service;

public interface ServiceDBIF {
	public boolean insertService(Service service) throws DatabaseAccessException;
}
