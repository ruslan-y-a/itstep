package org.itstep.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.sql.Connection;
import org.itstep.entities.Color;
import org.itstep.entities.Currency;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Items;
import org.itstep.entities.Size;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class BaseitemDaoImpl extends DaoImpl<Baseitem> implements BaseitemDao {
	//@Autowired private Connection c;
	private Map<Long, Baseitem> cache = new HashMap<>();

	@Override
	public Long create(Baseitem baseitem) throws DaoException {
		String sql = "INSERT INTO \"baseitem\"(\"itemid\", \"color\", \"size\", \"name\", \"quantity\", \"baseprice\", \"currency\") VALUES (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setLong(1, baseitem.getItem().getId());
			s.setLong(2, baseitem.getColor().getId());
			s.setLong(3, baseitem.getSize().getId());
			s.setString(4, baseitem.getName());
			s.setInt(5, baseitem.getQuantity());
			s.setLong(6, baseitem.getBaseprice());
			s.setLong(7, baseitem.getCurrency().getId());
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
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////	
	@Override
	public Baseitem read(Long id) throws DaoException {
		String sql = "SELECT \"itemid\", \"color\", \"size\", baseitem.name, \"quantity\", baseitem.baseprice, \"currency\", " + 
				"size.name as sname, color.name as cname, items.name as iname, items.baseprice as ibprice, "+
				"items.discount as idiscount FROM \"baseitem\" "+
				"INNER JOIN \"size\" ON baseitem.size = size.id "+
				"INNER JOIN \"color\" ON baseitem.color = color.id "+
				"INNER JOIN \"items\" ON baseitem.itemid = items.id "+				
				"WHERE baseitem.id = ?";
		Baseitem baseitem = cache.get(id);
		if(baseitem == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					baseitem = new Baseitem();
					baseitem.setId(id);
					baseitem.setItem(new Items()); 
					baseitem.getItem().setId(r.getLong("itemid"));					
					
					baseitem.setColor(new Color()); 
					baseitem.getColor().setId(r.getLong("color"));
					baseitem.setSize(new Size()); 
					baseitem.getSize().setId(r.getLong("size"));
					baseitem.setName(r.getString("name"));
					baseitem.setQuantity(r.getInt("quantity"));
					baseitem.setBaseprice(r.getLong("baseprice"));
					if (baseitem.getBaseprice()==0) {
					  baseitem.getItem().setBaseprice(r.getLong("ibprice"));
					}
					baseitem.getItem().setDiscount(r.getInt("idiscount"));
					
					baseitem.setCurrency(new Currency()); 
					baseitem.getCurrency().setId(r.getLong("currency"));
					
					baseitem.getColor().setName(r.getString("cname"));
					baseitem.getSize().setName(r.getString("sname"));
					baseitem.getItem().setName(r.getString("iname"));
					
					cache.put(id, baseitem);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return baseitem;
	}
	@Override
	public Baseitem readByICS(Long id,Long colorid,Long sizeid) throws DaoException {
		String sql = "SELECT \"itemid\", \"color\", \"size\", \"name\", \"quantity\", \"baseprice\", \"currency\" " + 				
				" FROM \"baseitem\" WHERE baseitem.id = ? and color.id = ? and size.id = ?";
		Baseitem baseitem = cache.get(id);
		if(baseitem == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				s.setLong(2, colorid);
				s.setLong(3, sizeid);
				r = s.executeQuery();
				if(r.next()) {
					baseitem = new Baseitem();
					baseitem.setId(id);
					baseitem.setItem(new Items()); 
					baseitem.getItem().setId(r.getLong("itemid"));					
					
					baseitem.setColor(new Color()); 
					baseitem.getColor().setId(r.getLong("color"));
					baseitem.setSize(new Size()); 
					baseitem.getSize().setId(r.getLong("size"));
					baseitem.setName(r.getString("name"));
					baseitem.setQuantity(r.getInt("quantity"));
					baseitem.setBaseprice(r.getLong("baseprice"));										
					baseitem.setCurrency(new Currency()); 
					baseitem.getCurrency().setId(r.getLong("currency"));
					
					cache.put(id, baseitem);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return baseitem;
	}
///////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Baseitem read(Long id, boolean bDisk) throws DaoException {
		String sql = "SELECT \"itemid\", \"color\", \"size\", baseitem.name, \"quantity\", baseitem.baseprice, \"currency\", " + 
				"size.name as sname, color.name as cname, items.name as iname, items.baseprice as ibprice, "+
				"items.discount as idiscount FROM \"baseitem\" "+
				"INNER JOIN \"size\" ON baseitem.size = size.id "+
				"INNER JOIN \"color\" ON baseitem.color = color.id "+
				"INNER JOIN \"items\" ON baseitem.itemid = items.id "+				
				"WHERE baseitem.id = ?";
		Baseitem baseitem = cache.get(id);
		if(baseitem == null) {
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					baseitem = new Baseitem();
					baseitem.setId(id);
					baseitem.setItem(new Items()); 
					baseitem.getItem().setId(r.getLong("itemid"));					
					
					baseitem.setColor(new Color()); 
					baseitem.getColor().setId(r.getLong("color"));
					baseitem.setSize(new Size()); 
					baseitem.getSize().setId(r.getLong("size"));
					baseitem.setName(r.getString("name"));
					baseitem.setQuantity(r.getInt("quantity"));
					baseitem.setBaseprice(r.getLong("baseprice"));
					if (baseitem.getBaseprice()==0) {
					  baseitem.getItem().setBaseprice(r.getLong("ibprice"));
					}
					baseitem.getItem().setDiscount(r.getInt("idiscount"));
					Integer idisc=r.getInt("idiscount");
						baseitem.getItem().setDiscount(idisc);
						if (idisc>0) {						
							baseitem.setBaseprice((Long)Math.round((100-idisc)*baseitem.getBaseprice()/100d));
						}	
					
					baseitem.setCurrency(new Currency()); 
					baseitem.getCurrency().setId(r.getLong("currency"));
					
					baseitem.getColor().setName(r.getString("cname"));
					baseitem.getSize().setName(r.getString("sname"));
					baseitem.getItem().setName(r.getString("iname"));
					
					cache.put(id, baseitem);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return baseitem;
	}
	
	@Override
	public void update(Baseitem baseitem) throws DaoException {		
	    String sql = "UPDATE \"baseitem\" SET \"itemid\"= ?, \"color\"= ?, \"size\"= ?, \"name\"= ?, \"quantity\"= ?, \"baseprice\"= ?, \"currency\" = ? WHERE \"id\" = ?";
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			s.setLong(1, baseitem.getItem().getId());
			s.setLong(2, baseitem.getColor().getId());
			s.setLong(3, baseitem.getSize().getId());
			s.setString(4, baseitem.getName());
			s.setInt(5, baseitem.getQuantity());
			s.setLong(6, baseitem.getBaseprice());
			s.setLong(7, baseitem.getCurrency().getId());			
			s.setLong(8, baseitem.getId());
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
		String sql = "DELETE FROM \"baseitem\" WHERE \"id\" = ?";
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
	public List<Baseitem> read() throws DaoException {
		String sql = "SELECT baseitem.id, \"itemid\", \"color\", \"size\", baseitem.name, \"quantity\", "+
				"baseitem.baseprice, \"currency\", size.name as sname, color.name as cname, "+
				"items.name as iname, items.baseprice as ibprice, items.discount as idiscount FROM \"baseitem\" "+
				"INNER JOIN \"size\" ON baseitem.size = size.id "+
				"INNER JOIN \"color\" ON baseitem.color = color.id "+
				"INNER JOIN \"items\" ON baseitem.itemid = items.id";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Baseitem> baseitems = new ArrayList<>();
			while(r.next()) {
				    Baseitem baseitem = new Baseitem();
					baseitem.setId(r.getLong("id"));
					baseitem.setItem(new Items()); 
					baseitem.getItem().setId(r.getLong("itemid"));					
					
					baseitem.setColor(new Color()); 
					baseitem.getColor().setId(r.getLong("color"));
					baseitem.setSize(new Size()); 
					baseitem.getSize().setId(r.getLong("size"));
					baseitem.setName(r.getString("name"));
					baseitem.setQuantity(r.getInt("quantity"));
					baseitem.setBaseprice(r.getLong("baseprice"));
					if (baseitem.getBaseprice()==0) {
						  baseitem.getItem().setBaseprice(r.getLong("ibprice"));
						}
						baseitem.getItem().setDiscount(r.getInt("idiscount"));
					
					baseitem.setCurrency(new Currency()); 
					baseitem.getCurrency().setId(r.getLong("currency"));
					
					baseitem.getColor().setName(r.getString("cname"));
					baseitem.getSize().setName(r.getString("sname"));
					baseitem.getItem().setName(r.getString("iname"));
					
					baseitems.add(baseitem);
				}
			return baseitems;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	//////////////////////////////////////////////////////////////////
	@Override
	public List<Baseitem> readItemRow(Long itemid) throws DaoException {
		String sql = "SELECT baseitem.id, \"itemid\", \"color\", \"size\", baseitem.name, \"quantity\", baseitem.baseprice, \"currency\", size.name as sname, color.name as cname, items.name as iname, items.baseprice as ibprice, items.discount as idiscount FROM \"baseitem\" "+
				"INNER JOIN \"size\" ON baseitem.size = size.id "+
				"INNER JOIN \"color\" ON baseitem.color = color.id "+
				"INNER JOIN \"items\" ON baseitem.itemid = items.id "+ 
				"WHERE baseitem.itemid = ?";		
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql);
			s.setLong(1, itemid);
			r = s.executeQuery();
		//	System.out.println("====================BA D  READING s " +s);
			Integer idisc=0;		
			List<Baseitem> baseitems = new ArrayList<>();
			while(r.next()) {
				    Baseitem baseitem = new Baseitem();
					baseitem.setId(r.getLong("id"));
			//		System.out.println("====================BA D  baseitem id " + r.getLong("id"));
					baseitem.setItem(new Items()); 
					baseitem.getItem().setId(r.getLong("itemid"));										
					baseitem.setColor(new Color()); 
					baseitem.getColor().setId(r.getLong("color"));
					baseitem.setSize(new Size()); 
					baseitem.getSize().setId(r.getLong("size"));
					baseitem.setName(r.getString("name"));
					baseitem.setQuantity(r.getInt("quantity"));
					baseitem.setBaseprice(r.getLong("baseprice"));
					if (baseitem.getBaseprice()==0) {
						  baseitem.getItem().setBaseprice(r.getLong("ibprice"));
						}
					    idisc=r.getInt("idiscount");
						baseitem.getItem().setDiscount(idisc);
						if (idisc>0) {						
							baseitem.setBaseprice((Long)Math.round((100-idisc)*baseitem.getBaseprice()/100d));
						}						
						
					baseitem.setCurrency(new Currency()); 
					baseitem.getCurrency().setId(r.getLong("currency"));
					
					baseitem.getColor().setName(r.getString("cname"));
					baseitem.getSize().setName(r.getString("sname"));
					baseitem.getItem().setName(r.getString("iname"));
				//	System.out.println("====================BA D  baseitem " + baseitem);
					baseitems.add(baseitem);
				}
			
		//	System.out.println("====================BA D  baseitemsize  " + baseitems.size());
			if ( baseitems.size()>0) {return baseitems;}
			return null;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Boolean getInStock(Long itemid) throws DaoException {
		String sql = "SELECT \"id\", \"quantity\" FROM \"baseitem\" WHERE baseitem.itemid = ?";		
		PreparedStatement s = null;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql);
			s.setLong(1, itemid);
			r = s.executeQuery();
		//	Integer idisc=0;					
			if (r.next()) {
				 return r.getInt("quantity")>0;
				}			
         	return false;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	//////////////////////////////////////////////////////////////////
}
