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

import postgres.DaoException;
import entities.Webpages;

public class WebpagesDaoImpl implements WebpagesDao{
	private Connection c;
	public void setConnection(Connection c) {
		this.c = c;
	}

	private Map<Long, Webpages> cache = new HashMap<>();
		
	@Override
	public Long create(Webpages webpages) throws DaoException {
		String sql = "INSERT INTO \"webpages\"(\"url\", \"title\", \"description\", \"keywords\", \"h1\", \"text\", \"robots\") VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, webpages.getUrl());
			s.setString(2, webpages.getTitle());
			s.setString(3, webpages.getDescription());
			s.setString(4, webpages.getKeywords());
			s.setString(5, webpages.getH1());
			s.setString(6, webpages.getText());
			s.setString(7, webpages.getRobots());
						
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
	public Webpages read(Long id) throws DaoException {
		String sql = "SELECT \"url\", \"title\", \"description\", \"keywords\", \"h1\", \"text\", \"robots\" FROM \"webpages\" WHERE \"id\" = ?";
		Webpages webpages = cache.get(id);
		if(webpages == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					webpages = new Webpages();
					webpages.setId(id);
					webpages.setUrl(r.getString("url"));
					webpages.setTitle(r.getString("title"));
					webpages.setDescription(r.getString("description"));
					webpages.setKeywords(r.getString("keywords"));
					webpages.setH1(r.getString("h1"));
					webpages.setText(r.getString("text"));
					webpages.setRobots(r.getString("robots"));
					
					cache.put(id, webpages);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return webpages;
	}

	
	@Override
	public void update(Webpages webpages) throws DaoException {
		String sql = "UPDATE \"webpages\" SET \"url\"= ?, \"title\"= ?, \"description\"= ?, \"keywords\"= ?, \"h1\"= ?, \"text\"= ?, \"robots\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			s.setString(1, webpages.getUrl());
			s.setString(2, webpages.getTitle());
			s.setString(3, webpages.getDescription());
			s.setString(4, webpages.getKeywords());
			s.setString(5, webpages.getH1());
			s.setString(6, webpages.getText());
			s.setString(7, webpages.getRobots());
			s.setLong(8, webpages.getId());
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
		String sql = "DELETE FROM \"webpages\" WHERE \"id\" = ?";
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
	public List<Webpages> read() throws DaoException {
		String sql = "SELECT \"id\", \"url\", \"title\", \"description\", \"keywords\", \"h1\", \"text\", \"robots\" FROM \"webpages\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Webpages> webpages = new ArrayList<>();
			while(r.next()) {
				Webpages webpage = new Webpages();
				webpage.setId(r.getLong("id"));
				
				webpage.setUrl(r.getString("url"));
				webpage.setTitle(r.getString("title"));
				webpage.setDescription(r.getString("description"));
				webpage.setKeywords(r.getString("keywords"));
				webpage.setH1(r.getString("h1"));
				webpage.setText(r.getString("text"));
				webpage.setRobots(r.getString("robots"));
				
				webpages.add(webpage);
			}
			return webpages;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
}
