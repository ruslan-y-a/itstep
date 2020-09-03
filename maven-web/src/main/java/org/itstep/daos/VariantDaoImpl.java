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


import org.itstep.entities.Variant;

import org.itstep.postgres.DaoException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class VariantDaoImpl extends DaoImpl<Variant>  implements VariantDao {
    private String name;	
	public void setName(String name) {this.name = name;}
	private Map<Long, Variant> cache = new HashMap<>();
		
	@Override
	public Long create(Variant variant) throws DaoException {
		String sql = "INSERT INTO \""+ this.name +"\"(\"name\") VALUES (?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);		
			s.setString(1, variant.getName());			
					
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
	public Variant read(Long id) throws DaoException {
		String sql = "SELECT \"name\" FROM \""+ this.name +"\" WHERE \"id\" = ?";
		Variant variant = cache.get(id);
		if(variant == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					variant  = Variant.getVariant(this.name);
					variant.setId(id);					 
					variant.setName(r.getString("name"));  					
					
					cache.put(id, variant);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return variant;
	}

		
	@Override
	public void update(Variant variant) throws DaoException {		
	    String sql = "UPDATE \""+ this.name +"\" SET \"name\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);			
			s.setString(1, variant.getName());			
			s.setLong(2, variant.getId());
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
		String sql = "DELETE FROM \""+ this.name +"\" WHERE \"id\" = ?";
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
	public List<Variant> read() throws DaoException {
		String sql = "SELECT \"id\", \"name\" FROM \""+ this.name +"\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Variant> variants = new ArrayList<>();
			while(r.next()) {
				Variant variant = new Variant(this.name);
				variant.setId(r.getLong("id"));
								
				variant.setName(r.getString("name"));  		
				
				variants.add(variant);
			}
			return variants;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
}
