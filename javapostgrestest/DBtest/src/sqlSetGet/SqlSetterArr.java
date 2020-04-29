package sqlSetGet;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.util.ArrayList;

import postgres.DaoException;

public class SqlSetterArr implements SqlSetter{  	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, Object o) throws DaoException {
		 try {		
			s.setArray(i, (Array) o);
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + o);
		 }
	}
	@Override
	public void sqlSet(PreparedStatement s, Integer i, String ss) throws DaoException {
		 try {		
			 Object o=null;
			if (ss.indexOf(',')!=-1) {			
		      o=ss.split(",");
			}			
			s.setArray(i, (Array)o);
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + ss);
		 }
	}
	
}
