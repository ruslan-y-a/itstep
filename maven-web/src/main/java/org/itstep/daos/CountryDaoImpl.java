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
import org.itstep.entities.Country;
import org.itstep.postgres.DaoException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CountryDaoImpl extends DaoImpl<Country>  implements CountryDao {
	private Map<Long, Country> cache = new HashMap<>();
		
	@Override
	public Long create(Country country) throws DaoException {
		String sql = "INSERT INTO \"country\"(\"currency\", \"name\") VALUES (?, ?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setLong(1, country.getCurrency().getId());
			s.setString(2, country.getName());
						
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
	public Country read(Long id) throws DaoException {
		String sql = "SELECT \"currency\", \"name\" FROM \"country\" WHERE \"id\" = ?";
		Country country = cache.get(id);
		if(country == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					country  = new Country();
					country.setId(id);
					country.setCurrency(new Currency()); 
					country.getCurrency().setId(r.getLong("currency"));					
					country.setName(r.getString("name"));										
					
					cache.put(id, country);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return country;
	}

		
	@Override
	public void update(Country country) throws DaoException {		
	    String sql = "UPDATE \"country\" SET \"currency\" = ?, \"name\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			s.setLong(1, country.getCurrency().getId());
			s.setString(2, country.getName());
			s.setLong(3, country.getId());
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
		String sql = "DELETE FROM \"country\" WHERE \"id\" = ?";
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
	public List<Country> read() throws DaoException {
		String sql = "SELECT \"id\", \"currency\", \"name\" FROM \"country\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Country> countries = new ArrayList<>();
			while(r.next()) {
				Country country = new Country();
				country.setId(r.getLong("id"));
				country.setCurrency(new Currency()); 
				country.getCurrency().setId(r.getLong("currency"));					
				country.setName(r.getString("name"));		
				
				countries.add(country);
			}
			return countries;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
}
