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

import entities.Baseitem;
import entities.Client;
import entities.Currency;
import entities.Delivery;
import entities.Orderstatus;
import entities.Orders;
import postgres.DaoException;


public class OrdersDaoImpl implements OrdersDao {
	private static Integer maxOrder=0;			
	@Override
	public Integer getMaxOrder() {
		return maxOrder;}
	
	private Connection c;
	public void setConnection(Connection c) {
		this.c = c;
	}
	private Map<Long, Orders> cache = new HashMap<>();
			
	@Override
	public Long create(Orders orders) throws DaoException {
		String sql = "INSERT INTO \"orders\"(\"number\", \"datetime\", \"dateexpired\", \"baseitem\", \"client\", \"quantity\", \"sum\", \"bonuspoints\" ,\"currency\" , \"delivery\" , \"active\", \"status\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement s = null;
		//int isize=0;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setInt(1, orders.getNumber());	
	        s.setDate(2, new java.sql.Date(orders.getDatetime().getTime()));
			s.setDate(3, new java.sql.Date(orders.getDateexpired().getTime()));
			s.setLong(4, orders.getBaseitem().getId());
			s.setLong(5, orders.getClient().getId());
			s.setInt(6, orders.getQuantity());
			s.setLong(7, orders.getSum());
			s.setInt(8, orders.getBonuspoints());
			s.setLong(9, orders.getCurrency().getId());
			s.setInt(10, orders.getDelivery().ordinal());
			s.setBoolean(11, orders.getActive());
			s.setInt(12, orders.getStatus().ordinal());
												
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
	public Orders read(Long id) throws DaoException {
		String sql = "SELECT \"number\", \"datetime\", \"dateexpired\", \"baseitem\", \"client\", \"quantity\", \"sum\", \"bonuspoints\", \"currency\" , \"delivery\" , \"active\", \"status\" FROM \"orders\" WHERE \"id\" = ?";			        
		
		Orders orders = cache.get(id);
		if(orders == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					orders  = new Orders();
					orders.setId(id);
					orders.setNumber(r.getInt("number"));
					orders.setDatetime(new java.util.Date(r.getDate("datetime").getTime()));
					orders.setDateexpired(new java.util.Date(r.getDate("dateexpired").getTime()));
					
					orders.setBaseitem(new Baseitem()); 
					orders.getBaseitem().setId(r.getLong("baseitem"));	
					orders.setClient(new Client()); 
					orders.getClient().setId(r.getLong("client"));
					orders.setQuantity(r.getInt("quantity"));
					orders.setSum(r.getLong("sum"));
					orders.setBonuspoints(r.getInt("bonuspoints"));
					orders.setCurrency(new Currency()); 
					orders.getCurrency().setId(r.getLong("currency"));						
					orders.setDelivery(Delivery.values()[r.getInt("delivery")]);
					orders.setActive(r.getBoolean("active"));
					orders.setStatus(Orderstatus.values()[r.getInt("status")]);		
					
					cache.put(id, orders);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return orders;
	}

		
	@Override
	public void update(Orders orders) throws DaoException {		
	    String sql = "UPDATE \"orders\" SET \"number\" = ?, \"datetime\" = ?, \"dateexpired\" = ?, \"baseitem\" = ?, \"client\" = ?, \"quantity\" = ?, \"sum\" = ?, \"bonuspoints\" = ?, \"currency\" = ?, \"delivery\" = ?, \"active\" = ?, \"status\" = ? WHERE \"id\" = ?";

	    PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			s.setInt(1, orders.getNumber());	
	        s.setDate(2, new java.sql.Date(orders.getDatetime().getTime()));
			s.setDate(3, new java.sql.Date(orders.getDateexpired().getTime()));
			s.setLong(4, orders.getBaseitem().getId());
			s.setLong(5, orders.getClient().getId());
			s.setInt(6, orders.getQuantity());
			s.setLong(7, orders.getSum());
			s.setInt(8, orders.getBonuspoints());
			s.setLong(9, orders.getCurrency().getId());
			s.setInt(10, orders.getDelivery().ordinal());
			s.setBoolean(11, orders.getActive());
			s.setInt(12, orders.getStatus() .ordinal());
			s.setLong(13, orders.getId());
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
		String sql = "DELETE FROM \"orders\" WHERE \"id\" = ?";
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
	public List<Orders> read() throws DaoException {
		String sql = "SELECT \"id\", \"number\", \"datetime\", \"dateexpired\", \"baseitem\", \"client\", \"quantity\", \"sum\", \"bonuspoints\", \"currency\", \"delivery\" , \"active\", \"status\" FROM \"orders\"";					
		
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Orders> orders = new ArrayList<>();
			while(r.next()) {
				Orders order = new Orders();
				order.setId(r.getLong("id"));
				
				order.setNumber(r.getInt("number"));
				if (order.getNumber()>=maxOrder) {
					OrdersDaoImpl.maxOrder =order.getNumber()+1; }
				
				order.setDatetime(new java.util.Date(r.getDate("datetime").getTime()));
				order.setDateexpired(new java.util.Date(r.getDate("dateexpired").getTime()));				
				order.setBaseitem(new Baseitem()); 
				order.getBaseitem().setId(r.getLong("baseitem"));	
				order.setClient(new Client()); 
				order.getClient().setId(r.getLong("client"));
				order.setQuantity(r.getInt("quantity"));
				order.setSum(r.getLong("sum"));
				order.setBonuspoints(r.getInt("bonuspoints"));
				order.setCurrency(new Currency()); 
				order.getCurrency().setId(r.getLong("currency"));						
				order.setDelivery(Delivery.values()[r.getInt("delivery")]);
				order.setActive(r.getBoolean("active"));
				order.setStatus(Orderstatus.values()[r.getInt("status")]);		
				
				orders.add(order);
			}
			return orders;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
}
