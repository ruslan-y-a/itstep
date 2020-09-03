package org.itstep.sqlSetGet;

import java.sql.ResultSet;
import org.itstep.postgres.DaoException;

public interface SqlGetter {
	Object sqlGet(ResultSet r,String name) throws DaoException;
}
