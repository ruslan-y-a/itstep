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

import entities.Classification;
import postgres.DaoException;

public class ClassificationDaoImpl implements ClassificationDao {

	private Connection c;
	public void setConnection(Connection c) {
		this.c = c;
	}
	
	private Map<Long, Classification> cache = new HashMap<>();

	@Override
	public Long create(Classification classification) throws DaoException {
		String sql = "INSERT INTO \"classification\"(\"name\", \"parentid\", \"categoryid\") VALUES (?, ?, ?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, classification.getName());

			if (classification.getParentid()==null) {
				s.setObject(2, null);}
			else {s.setLong(2, classification.getParentid());}				
			
			if (classification.getCategoryid()==null) {
				s.setObject(3, null);}
			else {s.setLong(3, classification.getCategoryid());	}	
									
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
	public Classification read(Long id) throws DaoException {
		String sql = "SELECT \"name\", \"parentid\", \"categoryid\" FROM \"classification\" WHERE \"id\" = ?";
		Classification classification = cache.get(id);
		if(classification == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					classification  = new Classification();
					classification.setId(id);
					classification.setName(r.getString("name"));
					classification.setParentid(r.getLong("parentid"));  
					if (classification.getParentid()!=null && classification.getParentid()!=0) {
						if (classification.getParentid()==id) {classification.setParentname(classification.getName());}
						else {classification.setParentname(read(classification.getParentid()).getName());}	
					}  
					classification.setCategoryid(r.getLong("categoryid"));														
					cache.put(id, classification);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return classification;
	}

		
	@Override
	public void update(Classification classification) throws DaoException {		
	    String sql = "UPDATE \"classification\" SET \"name\" = ?, \"parentid\" = ?,  \"categoryid\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			s.setString(1, classification.getName());
			
			if (classification.getParentid()==null) {
				s.setObject(2, null);}
			else {s.setLong(2, classification.getParentid());}				
			
			if (classification.getCategoryid()==null) {
				s.setObject(3, null);}
			else {s.setLong(3, classification.getCategoryid());	}				
			
			s.setLong(4, classification.getId());
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
		String sql = "DELETE FROM \"classification\" WHERE \"id\" = ?";
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
	public List<Classification> read() throws DaoException {
		String sql = "SELECT  \"id\", \"name\", \"parentid\", \"categoryid\" FROM \"classification\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Classification> classifications = new ArrayList<>();
			while(r.next()) {
				Classification classification = new Classification();
				classification.setId(r.getLong("id"));
				classification.setName(r.getString("name"));				
				classification.setParentid(r.getLong("parentid"));
				if (classification.getParentid()!=null && classification.getParentid()!=0) {
				//	classification.setParentname(read(classification.getParentid()).getName());
					if (classification.getParentid()==classification.getId()) {classification.setParentname(classification.getName());}
					else {classification.setParentname(read(classification.getParentid()).getName());}	
				}													
				classification.setCategoryid(r.getLong("categoryid"));									
				classifications.add(classification);
			}
			return classifications;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
}
