package sqlSetGet;

import java.sql.ResultSet;
import java.sql.SQLException;
import postgres.DaoException;

public class SqlGetterArr implements SqlGetter{  	
	@Override
	public Object sqlGet(ResultSet r,String name) throws DaoException {
		 try {
			 return r.getArray(name);
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in getting " + name);
		 }
	}
	
}
