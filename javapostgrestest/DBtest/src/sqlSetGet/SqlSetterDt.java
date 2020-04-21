package sqlSetGet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import postgres.DaoException;

public class SqlSetterDt implements SqlSetter{  	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, Object o) throws DaoException {
		 try {
			 Date date =  (Date)o;			
			 s.setDate(i,new java.sql.Date(date.getTime()));
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + o);
		 }
	}
	
}