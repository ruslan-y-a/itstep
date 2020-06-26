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
import tabs.Role;
import entities.User;

public class UserDbDaoImpl implements UserDao {
	private Connection c;

	public void setConnection(Connection c) {
		this.c = c;
	}

	private Map<Long, User> cache = new HashMap<>();

	@Override
	public Long create(User user) throws DaoException {
		String sql = "INSERT INTO \"users\"(\"login\", \"password\", \"roleid\", \"name\", \"email\") VALUES (?, ?, ?, ?, ?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, user.getLogin());
			s.setString(2, user.getPassword());
			s.setInt(3, user.getRole().ordinal());
			s.setString(4, user.getName());
			s.setString(5, user.getEmail());
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
	public User read(Long id) throws DaoException {
		String sql = "SELECT \"login\", \"password\", \"roleid\" , \"name\", \"email\" FROM \"users\" WHERE \"id\" = ?";
		User user = cache.get(id);
		if(user == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					user = new User();
					user.setId(id);
					user.setLogin(r.getString("login"));
					user.setPassword(r.getString("password"));
					user.setRole(Role.values()[r.getInt("roleid")]);
					user.setName(r.getString("name"));
					user.setEmail(r.getString("email"));
					
					cache.put(id, user);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return user;
	}

	@Override
	public User read(String login, String password) throws DaoException {
		String sql = "SELECT \"id\", \"roleid\" FROM \"users\" WHERE \"login\" = ? AND \"password\" = ?";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql);
			s.setString(1, login);
			s.setString(2, password);
			r = s.executeQuery();
			User user = null;
			if(r.next()) {
				user = new User();
				user.setId(r.getLong("id"));
				user.setLogin(login);
				user.setPassword(password);
				user.setRole(Role.values()[r.getInt("roleid")]);
			}
		//	System.out.println("USER:" + user);
			return user;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
	@Override
	public void update(User user) throws DaoException {
		String sql = "UPDATE \"users\" SET \"login\" = ?, \"password\" = ?, \"roleid\" = ?, \"name\" = ?, \"email\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			s.setString(1, user.getLogin());
			s.setString(2, user.getPassword());
			s.setInt(3, user.getRole().ordinal());
			s.setString(4, user.getName());
			s.setString(5, user.getEmail());
			s.setLong(6, user.getId());
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
		String sql = "DELETE FROM \"users\" WHERE \"id\" = ?";
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
	public List<User> read() throws DaoException {
		String sql = "SELECT \"id\", \"login\", \"password\", \"roleid\" , \"name\", \"email\" FROM \"users\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<User> users = new ArrayList<>();
			while(r.next()) {
				User user = new User();
				user.setId(r.getLong("id"));
				
				user.setLogin(r.getString("login"));
				user.setPassword(r.getString("password"));
				user.setRole(Role.values()[r.getInt("roleid")]);
				user.setName(r.getString("name"));
				user.setEmail(r.getString("email"));
				
				users.add(user);
			}
			return users;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
	
}
