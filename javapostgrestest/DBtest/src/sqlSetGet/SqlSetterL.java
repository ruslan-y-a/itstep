package sqlSetGet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import postgres.DaoException;

public class SqlSetterL implements SqlSetter{  	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, Object o) throws DaoException{
		 try {
			s.setLong(i, (Long) o);
		} catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + o);
		}}	
}
