package sqlSetGet;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, String ss) throws DaoException {
		 try {
			 SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			 Date d1 = format1.parse(ss);			
			 s.setDate(i,new java.sql.Date(d1.getTime()));
		 } catch (SQLException | ParseException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + ss);
		 }
	}
	
}