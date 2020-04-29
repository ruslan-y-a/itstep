package sqlSetGet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import postgres.DaoException;

public class SqlSetterDb implements SqlSetter{  	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, Object o) throws DaoException {
		 try {
			s.setDouble(i,(Double) o);
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + o);
		 }
	}
	@Override
	public void sqlSet(PreparedStatement s, Integer i, String ss) throws DaoException {
		 try {
			s.setDouble(i, Double.parseDouble(ss));
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + ss);
		 }
	}
	
}