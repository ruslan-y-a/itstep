package sqlSetGet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import postgres.DaoException;

public class SqlSetterTm  implements SqlSetter{  	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, Object o) throws DaoException {
		 try {
			 Date date =  (Date)o;			
			 s.setTime(i,new java.sql.Time(date.getTime()));
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + o);
		 }
	}
	
}