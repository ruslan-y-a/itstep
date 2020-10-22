package org.itstep.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;
import org.itstep.config.ConnectionThreadHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class CurRatDao {
	
	protected Connection c = ConnectionThreadHolder.getConnection();
	protected final Connection getConnection() {return c;}
	public final void setConnection(Connection c) {this.c = c;}

	public List<List<String>> read() throws DaoException {		
		String sql = "SELECT * FROM currency__rate " + 
				"INNER JOIN rate ON rate.id = rate_id " + 
				"INNER JOIN currency ON currency.id = currency_id; ";
		Statement s = null;
		ResultSet r = null;
		List<List<String>> list= new ArrayList<>();
		List<String> ls0 = new ArrayList<>();
		ls0.add("date");ls0.add("name");ls0.add("rate");
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			
			while(r.next()) {
				List<String> ls = new ArrayList<>();
				ls.add(String.valueOf(new java.util.Date(r.getDate("date").getTime())));
				ls.add(r.getString("name"));
				ls.add(String.valueOf(r.getDouble("rate")));				
				list.add(ls);				
			}
			return list;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
/////////////////////////////////////////
	
}
