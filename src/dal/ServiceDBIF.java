package dal;

import exceptions.DatabaseAccessException;

public interface ServiceDBIF {
	public boolean insertService() throws DatabaseAccessException;
}
