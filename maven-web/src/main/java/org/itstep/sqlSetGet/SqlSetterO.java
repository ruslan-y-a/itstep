package org.itstep.sqlSetGet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.itstep.postgres.DaoException;

public class SqlSetterO  implements SqlSetter{  	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, Object o) throws DaoException {
		 try {
			s.setObject(i, o);
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + o);
		 }
	}
	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, String ss) throws DaoException {
		 try {
			s.setObject(i, ss);
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + ss);
		 }
	}
}
