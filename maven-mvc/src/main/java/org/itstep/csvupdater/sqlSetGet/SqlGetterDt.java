package org.itstep.csvupdater.sqlSetGet;

import java.sql.ResultSet;
import org.itstep.daos.DaoException;
import java.sql.SQLException;

public class SqlGetterDt implements SqlGetter{  	
	@Override
	public Object sqlGet(ResultSet r,String name) throws DaoException {
		 try {
			 return new java.util.Date(r.getDate(name).getTime());
		 } catch (SQLException e) {	 		
			e.printStackTrace();
			throw new DaoException("Error in getting " + name);
		 }
	}
	
}
