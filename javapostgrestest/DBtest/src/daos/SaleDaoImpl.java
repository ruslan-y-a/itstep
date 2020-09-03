package daos;

//import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entities.Currency;
import entities.Orders;
import entities.Sale;

import postgres.DaoException;

public class SaleDaoImpl extends DaoImpl<Sale> implements SaleDao {
	/*private Connection c; public void setConnection(Connection c) {this.c = c;} */
	private Map<Long, Sale> cache = new HashMap<>();
				
	@Override
	public Long create(Sale sale) throws DaoException {
		String sql = "INSERT INTO \"sale\"(\"datetime\", \"order\", \"returned\", \"currency\") VALUES (?, ?, ?, ?)";
		PreparedStatement s = null;
		//int isize=0;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
	        s.setTimestamp(1, new java.sql.Timestamp(sale.getDatetime().getTime())); // java.sql.timestamp
	        s.setLong(2, sale.getOrder().getId());
	        s.setBoolean(3, sale.getReturned());
	        s.setLong(4, sale.getCurrency().getId());	        		
												
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
	public Sale read(Long id) throws DaoException {
		String sql = "SELECT \"datetime\", \"order\", \"returned\", \"currency\" FROM \"sale\"  WHERE \"id\" = ?";
		Sale sale = cache.get(id);
		if(sale == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					sale  = new Sale();
					sale.setId(id);										
					sale.setDatetime(new java.util.Date(r.getDate("datetime").getTime()));									
					sale.setOrder(new Orders()); 
					sale.getOrder().setId(r.getLong("order"));	
					sale.setReturned(r.getBoolean("returned")); 					
					sale.setCurrency(new Currency()); 
					sale.getCurrency().setId(r.getLong("currency"));																			
					
					cache.put(id, sale);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return sale;
	}

		
	@Override
	public void update(Sale sale) throws DaoException {		
	    String sql = "UPDATE \"sale\" SET \"datetime\" = ?, \"order\" = ?, \"returned\" = ?, \"currency\" = ? WHERE \"id\" = ?";

	    PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			//  System.out.println("====================DAO DATA" + sale.getDatetime());
			    s.setTimestamp(1, new java.sql.Timestamp(sale.getDatetime().getTime()));
		        s.setLong(2, sale.getOrder().getId());
		        s.setBoolean(3, sale.getReturned());
		        s.setLong(4, sale.getCurrency().getId());	 	
			s.setLong(5, sale.getId());
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
		String sql = "DELETE FROM \"sale\" WHERE \"id\" = ?";
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
	public List<Sale> read() throws DaoException {
		String sql = "SELECT \"id\", \"datetime\", \"order\", \"returned\", \"currency\" FROM \"sale\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Sale> sales = new ArrayList<>();
			while(r.next()) {
				Sale sale = new Sale();
				sale.setId(r.getLong("id"));
				
				sale.setDatetime(new java.util.Date(r.getDate("datetime").getTime()));									
				sale.setOrder(new Orders()); 
				sale.getOrder().setId(r.getLong("order"));	
				sale.setReturned(r.getBoolean("returned")); 					
				sale.setCurrency(new Currency()); 
				sale.getCurrency().setId(r.getLong("currency"));	
				
				sales.add(sale);
			}
			return sales;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
}
