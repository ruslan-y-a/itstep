package sqlSetGet;

//import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.util.ArrayList;

import postgres.DaoException;

public class SqlSetterArr implements SqlSetter{  	
	@Override
	public void sqlSet(PreparedStatement s, Integer i, Object o) throws DaoException {
		 try {		
			 s.setObject(i, o);
			//s.setArray(i, (Array) o);
		 } catch (SQLException e) {			
			e.printStackTrace();
			throw new DaoException("Error in saving " + o);
		 }
	}
	@Override
	public void sqlSet(PreparedStatement s, Integer i, String ss) throws DaoException {
		 int im[]=null;
		 String smas[];
		if (ss.indexOf(',')!=-1) {
		   smas=ss.split(",");
		   im=new int[smas.length];
		   for(int ii=0;ii<smas.length;ii++) {
			   im[ii]=Integer.parseInt(smas[ii]);}
		   
		}			
		Object o= im;
		this.sqlSet(s, i, o);
		//s.setObject(i, o);
		//s.setArray(i, (Array) o);
	}
	
}
