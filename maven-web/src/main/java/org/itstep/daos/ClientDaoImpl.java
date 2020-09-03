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

import org.itstep.entities.Client;
import org.itstep.entities.Country;
import org.itstep.entities.Items;
import org.itstep.entities.User;
import org.itstep.help.Helper;
import org.itstep.postgres.DaoException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ClientDaoImpl extends DaoImpl<Client> implements ClientDao {
	private Map<Long, Client> cache = new HashMap<>();
		
	@Override
	public Long create(Client client) throws DaoException {
		String sql = "INSERT INTO \"client\"(\"countryid\", \"address\", \"creationdate\", \"userid\", \"bonuspoints\", \"phoneno\", \"recentitems\") VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
						
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setLong(1, client.getCountry().getId());
			s.setString(2, client.getAddress());
			s.setDate(3, new java.sql.Date(client.getCreationdate().getTime()));
			s.setLong(4, client.getUser().getId());			
			if (client.getBonuspoints()==null) {s.setObject(5, null);}
			else {s.setInt(5, client.getBonuspoints());}
			s.setString(6, client.getPhoneno());			
		//	s.setObject(7, client.getRecentitems());
			if (client.getRecentitems()==null || client.getRecentitems().size()==0) {
				 s.setObject(7, null);}
			else {
				long[] result = client.getRecentitems().stream().map((x)->x.getId()).mapToLong(x -> x).toArray();				
				s.setObject( 7, (Object) result);
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
	public Client read(Long id) throws DaoException {
		String sql = "SELECT \"countryid\", \"address\", \"creationdate\", \"userid\", \"bonuspoints\", \"phoneno\", \"recentitems\", users.name as uname FROM \"client\" "
				+"INNER JOIN \"users\" ON client.userid = users.id "
				+ "WHERE client.id = ?";		
		
		Client client = cache.get(id);
		if(client == null) {
			
			ArrayList<Long> iList= new ArrayList<>();	
		    ArrayList<Items> Litems= new ArrayList<>();		
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					client  = new Client();
					client.setId(id);
					client.setCountry(new Country()); 
					client.getCountry().setId(r.getLong("countryid"));					
					client.setAddress(r.getString("address"));
					client.setCreationdate(new java.util.Date(r.getDate("creationdate").getTime()));
					client.setUser(new User()); 
					client.getUser().setId(r.getLong("userid"));
					client.getUser().setName(r.getString("uname"));
					client.setBonuspoints(r.getInt("bonuspoints"));  
					client.setPhoneno(r.getString("phoneno"));  
					iList=Helper.objToLongArrayList(r.getObject("recentitems"));
					if (iList!=null) {iList.forEach((x) -> {
						Items cl = new Items();
						cl.setId(x);
						Litems.add(cl);
					  });}
					client.setRecentitems(Litems);		
					
					cache.put(id, client);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return client;
	}

		
	@Override
	public void update(Client client) throws DaoException {		
	    String sql = "UPDATE \"client\" SET \"countryid\" = ?, \"address\" = ?, \"creationdate\" = ?, \"userid\" = ?, \"bonuspoints\" = ?, \"phoneno\" = ?, \"recentitems\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			s.setLong(1, client.getCountry().getId());
			s.setString(2, client.getAddress());
			s.setDate(3, new java.sql.Date(client.getCreationdate().getTime()));
			s.setLong(4, client.getUser().getId());
			s.setInt(5, client.getBonuspoints());
			s.setString(6, client.getPhoneno());
			if (client.getRecentitems()==null || client.getRecentitems().size()==0) {
				 s.setObject(7, null);}
			else {			
				long[] result = client.getRecentitems().stream().map((x)->x.getId()).mapToLong(x -> x).toArray();				
				s.setObject( 7, (Object) result);
			}
			s.setLong(8, client.getId());
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
		String sql = "DELETE FROM \"client\" WHERE \"id\" = ?";
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
	public List<Client> read() throws DaoException {
		String sql = "SELECT client.id, \"countryid\", \"address\", \"creationdate\", \"userid\", \"bonuspoints\", \"phoneno\", \"recentitems\", users.name as uname FROM \"client\" "
				+"INNER JOIN \"users\" ON client.userid = users.id";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Client> clients = new ArrayList<>();
			while(r.next()) {
				
				ArrayList<Long> iList= new ArrayList<>();	
			    ArrayList<Items> Litems= new ArrayList<>();				
				
				Client client = new Client();
				client.setId(r.getLong("id"));

				client.setCountry(new Country()); 
				client.getCountry().setId(r.getLong("countryid"));					
				client.setAddress(r.getString("address"));
				client.setCreationdate(new java.util.Date(r.getDate("creationdate").getTime()));
				client.setUser(new User()); 
				client.getUser().setId(r.getLong("userid"));	
				client.getUser().setName(r.getString("uname"));
				client.setBonuspoints(r.getInt("bonuspoints"));  
				client.setPhoneno(r.getString("phoneno"));  
			//	client.setRecentitems(Helper.objToIntArray(r.getObject("recentitems")));								
				iList=Helper.objToLongArrayList(r.getObject("recentitems"));
				if (iList!=null) {iList.forEach((x) -> {
					Items cl = new Items();
					cl.setId(x);
					Litems.add(cl);
				  });}
				client.setRecentitems(Litems);				
				
				clients.add(client);
			}
			return clients;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Long findByUserId(Long id) throws DaoException {
		String sql = "SELECT client.id as cid FROM \"client\" "
				+"INNER JOIN \"users\" ON client.userid = users.id "
				+ "WHERE users.id = ?";								
			
			Long cid=null;			  		
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {					 
					cid=r.getLong("cid");										
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		
		return cid;
	}
	////////////////////////////////////////////////////////////
	@Override
	public Client readByUserId(Long id) throws DaoException {
		String sql = "SELECT client.id as cid, \"countryid\", \"address\", \"creationdate\", \"userid\", \"bonuspoints\", \"phoneno\", \"recentitems\", users.name as uname FROM \"client\" "
				+"INNER JOIN \"users\" ON client.userid = users.id "
				+ "WHERE users.id = ?";			
		
	//	Client client = cache.get(id);
	//	if(client == null) {
		Client client = new Client(); 
			ArrayList<Long> iList= new ArrayList<>();	
		    ArrayList<Items> Litems= new ArrayList<>();		
			PreparedStatement s = null;
			ResultSet r = null;
			Long cid;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					cid=r.getLong("cid");				
					client.setId(cid);
					client.setCountry(new Country()); 
					client.getCountry().setId(r.getLong("countryid"));					
					client.setAddress(r.getString("address"));
					client.setCreationdate(new java.util.Date(r.getDate("creationdate").getTime()));
					client.setUser(new User()); 
					client.getUser().setId(r.getLong("userid"));
					client.getUser().setName(r.getString("uname"));
					client.setBonuspoints(r.getInt("bonuspoints"));  
					client.setPhoneno(r.getString("phoneno"));  
					iList=Helper.objToLongArrayList(r.getObject("recentitems"));
					if (iList!=null) {iList.forEach((x) -> {
						Items cl = new Items();
						cl.setId(x);
						Litems.add(cl);
					  });}
					client.setRecentitems(Litems);		
					
					cache.put(cid, client);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
	//	}
	//	System.out.println("================1)"+client);
		return client;
	}
}
