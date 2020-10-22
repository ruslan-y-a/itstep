package org.itstep.daos;

//import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itstep.entities.Currency;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CurrencyDaoImpl extends DaoImpl<Currency> implements CurrencyDao {	
	//@Autowired private Connection c;
	private Map<Long, Currency> cache = new HashMap<>();
		
	@Override
	public Long create(Currency currency) throws DaoException {
		String sql = "INSERT INTO \"currency\"(\"name\", \"rate\") VALUES (?, ?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);		
			s.setString(1, currency.getName());
			s.setDouble(2, currency.getRate());
						
			s.executeUpdate();
			r = s.getGeneratedKeys();
			r.next();
			cache.clear();
			return r.getLong(1);
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
			try { r.close(); } catch(Exception e) {}
		}
	}
	
	@Override
	public Currency read(Long id) throws DaoException {
		String sql = "SELECT \"rate\", \"name\" FROM \"currency\" WHERE \"id\" = ?";
		Currency currency = cache.get(id);
		if(currency == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					currency  = new Currency();
					currency.setId(id);
					currency.setRate(r.getDouble("rate"));					
					currency.setName(r.getString("name"));										
					
					cache.put(id, currency);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return currency;
	}

		
	@Override
	public void update(Currency currency) throws DaoException {		
	    String sql = "UPDATE \"currency\" SET \"rate\" = ?, \"name\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			s.setDouble(1, currency.getRate());
			s.setString(2, currency.getName());			
			s.setLong(3, currency.getId());
			s.executeUpdate();
			cache.clear();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
		}
	}

	@Override
	public void delete(Long id) throws DaoException {
		String sql = "DELETE FROM \"currency\" WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			s.setLong(1, id);
			s.executeUpdate();
			cache.clear();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
		}
	}
	
	@Override
	public List<Currency> read() throws DaoException {
		String sql = "SELECT  \"id\", \"rate\", \"name\" FROM \"currency\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Currency> currencies = new ArrayList<>();
			while(r.next()) {
				Currency currency = new Currency();
				currency.setId(r.getLong("id"));
				
				currency.setRate(r.getDouble("rate"));					
				currency.setName(r.getString("name"));			
				
				currencies.add(currency);
			}
			return currencies;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
}
