package daos; //SizeDao

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Size;
import entities.Variant;
import postgres.DaoException;

public class SizeDaoImpl implements SizeDao {

	private Connection c;
	public void setConnection(Connection c) {
		this.c = c;
	}	

	private Map<Long, Variant> cache = new HashMap<>();
		
	@Override
	public Long create(Size size) throws DaoException {
		String sql = "INSERT INTO \"size\"(\"name\") VALUES (?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);		
			s.setString(1, size.getName());			
					
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
	public Size read(Long id) throws DaoException {
		String sql = "SELECT \"name\" FROM \"size\" WHERE \"id\" = ?";
		Size size = (Size) cache.get(id);
		if(size == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					size  = (Size) Variant.getVariant("size");
					size.setId(id);					 
					size.setName(r.getString("name"));  					
					
					cache.put(id, size);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return size;
	}

		
	@Override
	public void update(Size size) throws DaoException {		
	    String sql = "UPDATE \"size\" SET \"name\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);			
			s.setString(1, size.getName());			
			s.setLong(2, size.getId());
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
		String sql = "DELETE FROM \"size\" WHERE \"id\" = ?";
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
	public List<Size> read() throws DaoException {
		String sql = "SELECT \"id\", \"name\" FROM \"size\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Size> variants = new ArrayList<>();
			while(r.next()) {
				Size variant =  new Size();
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