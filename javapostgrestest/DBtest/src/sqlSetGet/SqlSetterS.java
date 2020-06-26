package sqlSetGet;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import postgres.DaoException;

public class SqlSetterS implements SqlSetter{  	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, Object o) throws DaoException{
		 try {
			s.setString(i, o.toString().trim());
		} catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + o);
		}}	
	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, String ss) throws DaoException{
		 try {
			s.setString(i, ss.toString().trim());
		} catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + ss);
		}}	
}
