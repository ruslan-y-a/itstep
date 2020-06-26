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

import entities.Category;
import entities.Webpages;
import postgres.DaoException;

public class CategoryDaoImpl implements CategoryDao {

	private Connection c;
	public void setConnection(Connection c) {
		this.c = c;
	}
	
	private Map<Long, Category> cache = new HashMap<>();

	@Override
	public Long create(Category category) throws DaoException {
		String sql = "INSERT INTO \"category\"(\"name\", \"parentid\", \"webpages\") VALUES (?, ?, ?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, category.getName());
			if (category.getParentid()==null) {
				s.setObject(2, null);
				}
			else {
				s.setLong(2, category.getParentid());}
		//	System.out.println(category.getWebpages());
			if (category.getWebpages()==null) {
				 s.setObject(3, null);
				} else {
				s.setLong(3, category.getWebpages().getId());
				}
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
	public Category read(Long id) throws DaoException {
		String sql = "SELECT \"name\", \"parentid\", \"webpages\" FROM \"category\" WHERE \"id\" = ?";
		Category category = cache.get(id);
		if(category == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					category  = new Category();
					category.setId(id);
					category.setName(r.getString("name"));
					category.setParentid(r.getLong("parentid"));  
				//	if (category.getParentid()>0) {category.setParentname(read(category.getParentid()).getName());}				
					category.setWebpages(new Webpages()); 
					category.getWebpages().setId(r.getLong("webpages"));				
					
					cache.put(id, category);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return category;
	}

		
	@Override
	public void update(Category category) throws DaoException {		
	    String sql = "UPDATE \"category\" SET \"name\" = ?, \"parentid\" = ?, \"webpages\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			s.setString(1, category.getName());
			if (category.getParentid()==null) {
				s.setObject(2, null);}
			else {s.setLong(2, category.getParentid());}			
			if ( category.getWebpages()==null) {
				s.setObject(3, null);}
			else {s.setLong(3, category.getWebpages().getId());}
			
			s.setLong(4, category.getId());
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
		String sql = "DELETE FROM \"category\" WHERE \"id\" = ?";
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
	public List<Category> read() throws DaoException {
		String sql = "SELECT \"id\", \"name\", \"parentid\", \"webpages\" FROM \"category\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Category> categories = new ArrayList<>();
			while(r.next()) {
				Category category = new Category();
				category.setId(r.getLong("id"));
				category.setName(r.getString("name"));
				category.setParentid(r.getLong("parentid"));  				
		//		if (category.getParentid()>0) {category.setParentname(read(category.getParentid()).getName());}					
				category.setWebpages(new Webpages()); 
				category.getWebpages().setId(r.getLong("webpages"));		
				categories.add(category);
			}
			return categories;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
}
