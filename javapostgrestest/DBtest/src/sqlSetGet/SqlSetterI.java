package sqlSetGet;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import postgres.DaoException;

public class SqlSetterI implements SqlSetter{  	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, Object o) throws DaoException {
		 try {
			s.setInt(i,(Integer) o);
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + o);
		 }
	}
	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, String ss) throws DaoException {
		 try {
			s.setInt(i, Integer.parseInt(ss));
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + ss);
		 }
	}
}


