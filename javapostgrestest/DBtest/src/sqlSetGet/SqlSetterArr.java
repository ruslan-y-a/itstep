package sqlSetGet;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
	
}
