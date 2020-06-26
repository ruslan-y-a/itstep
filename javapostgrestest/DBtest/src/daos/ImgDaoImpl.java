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


import entities.Img;
import postgres.DaoException;

public class ImgDaoImpl implements ImgDao {

	private Connection c;
	public void setConnection(Connection c) {
		this.c = c;
	}
	
	private Map<Long, Img> cache = new HashMap<>();

	@Override
	public Long create(Img img) throws DaoException {
		String sql = "INSERT INTO \"img\"(\"title\", \"alt\", \"url\") VALUES (?, ?, ?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {		
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, img.getTitle());
			s.setString(2, img.getAlt());
			s.setString(3, img.getUrl());			
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
	public Img read(Long id) throws DaoException {
		String sql = "SELECT \"title\", \"alt\", \"url\" FROM \"img\" WHERE \"id\" = ?";
		Img img = cache.get(id);
		if(img == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					img = new Img();
					img.setId(id);
					img.setTitle(r.getString("title"));
					img.setAlt(r.getString("alt"));
					img.setUrl(r.getString("url"));														
					cache.put(id, img);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return img;
	}

		
	@Override
	public void update(Img img) throws DaoException {		
	    String sql = "UPDATE \"img\" SET \"title\"= ?, \"alt\"= ?, \"url\"= ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			s.setString(1, img.getTitle());
			s.setString(2, img.getAlt());
			s.setString(3, img.getUrl());			
			s.setLong(4, img.getId());
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
		String sql = "DELETE FROM \"img\" WHERE \"id\" = ?";
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
	public List<Img> read() throws DaoException {
		String sql = "SELECT \"id\", \"title\", \"alt\", \"url\" FROM \"img\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Img> imgs = new ArrayList<>();
			while(r.next()) {
				Img img = new Img();
				img.setId(r.getLong("id"));
				img.setTitle(r.getString("title"));
				img.setAlt(r.getString("alt"));
				img.setUrl(r.getString("url"));		
				
				imgs.add(img);
			}
			return imgs;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
}
