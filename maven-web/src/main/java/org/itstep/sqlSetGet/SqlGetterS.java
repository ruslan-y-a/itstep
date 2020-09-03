package org.itstep.sqlSetGet;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.itstep.postgres.DaoException;

public class SqlGetterS  implements SqlGetter{  	
	@Override
	public Object sqlGet(ResultSet r,String name) throws DaoException {
		 try {
			return r.getString(name);
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in getting " + name);
		 }
	}
	
}