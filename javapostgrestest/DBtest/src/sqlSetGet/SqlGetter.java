package sqlSetGet;

import java.sql.ResultSet;
import postgres.DaoException;

public interface SqlGetter {
	Object sqlGet(ResultSet r,String name) throws DaoException;
}
