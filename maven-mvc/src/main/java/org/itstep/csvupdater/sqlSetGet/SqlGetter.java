package org.itstep.csvupdater.sqlSetGet;

import java.sql.ResultSet;
import org.itstep.daos.DaoException;

public interface SqlGetter {
	Object sqlGet(ResultSet r,String name) throws DaoException;
}
