package daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Color;
import entities.Variant;
import postgres.DaoException;

public class ColorDaoImpl implements ColorDao {

	private Connection c;
	public void setConnection(Connection c) {
		this.c = c;
	}	

	private Map<Long, Variant> cache = new HashMap<>();
		
	@Override
	public Long create(Color color) throws DaoException {
		String sql = "INSERT INTO \"color\"(\"name\") VALUES (?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);		
			s.setString(1, color.getName());			
					
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
	public Color read(Long id) throws DaoException {
		String sql = "SELECT \"name\" FROM \"color\" WHERE \"id\" = ?";
		Color color = (Color) cache.get(id);
		if(color == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					color  = (Color) Variant.getVariant("color");
					color.setId(id);					 
					color.setName(r.getString("name"));  					
					
					cache.put(id, color);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return color;
	}

		
	@Override
	public void update(Color color) throws DaoException {		
	    String sql = "UPDATE \"color\" SET \"name\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);			
			s.setString(1, color.getName());			
			s.setLong(2, color.getId());
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
		String sql = "DELETE FROM \"color\" WHERE \"id\" = ?";
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
	public List<Color> read() throws DaoException {
		String sql = "SELECT \"id\", \"name\" FROM \"color\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Color> variants = new ArrayList<>();
			while(r.next()) {
				Color variant =  new Color();
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