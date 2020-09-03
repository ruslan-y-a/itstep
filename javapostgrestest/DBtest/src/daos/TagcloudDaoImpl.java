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

import entities.Classification;
import entities.Tagcloud;
import entities.Webpages;
import help.Helper;
import postgres.DaoException;

public class TagcloudDaoImpl extends DaoImpl<Tagcloud> implements TagcloudDao {
	/*private Connection c; public void setConnection(Connection c) {this.c = c;} */
	private Map<Long, Tagcloud> cache = new HashMap<>();
			
	@Override
	public Long create(Tagcloud tagcloud) throws DaoException {
		String sql = "INSERT INTO \"tagcloud\"(\"classification\", \"webpages\") VALUES (?, ?)";
		PreparedStatement s = null;//	int isize=0;	//
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		//	isize=tagcloud.getClassification().size();			
			if (tagcloud.getClassification()==null || tagcloud.getClassification().size()==0) {
				 s.setObject(1, null);}
			else {
				long[] result = tagcloud.getClassification().stream().map((x)->x.getId()).mapToLong(x -> x).toArray();				
				s.setObject( 1, (Object) result);
			}		
	        s.setLong(2, tagcloud.getWebpages().getId());		
												
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
	public Tagcloud read(Long id) throws DaoException {
		String sql = "SELECT \"classification\", \"webpages\" FROM \"tagcloud\"  WHERE \"id\" = ?";
		Tagcloud tagcloud = cache.get(id);
		if(tagcloud == null) {
			    ArrayList<Long> itagcloud;
			    ArrayList<Classification> tlist= new ArrayList<>();
			    
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					tagcloud  = new Tagcloud();
					tagcloud.setId(id);
					
					itagcloud=Helper.objToLongArrayList(r.getObject("classification"));
					itagcloud.forEach((x) -> {
						Classification cl = new Classification();
						cl.setId(x);
						tlist.add(cl);
					  });	
					tagcloud.setClassification(tlist);
					tagcloud.setWebpages(new Webpages());
					tagcloud.getWebpages().setId(r.getLong("webpages"));
					
					cache.put(id, tagcloud);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return tagcloud;
	}

		
	@Override
	public void update(Tagcloud tagcloud) throws DaoException {		
	    String sql = "UPDATE \"tagcloud\" SET \"classification\"= ?, \"webpages\"= ? WHERE \"id\" = ?";
	    //int isize=0;
	    PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);				
			if (tagcloud.getClassification()==null || tagcloud.getClassification().size()==0) {
				 s.setObject(1, null);}
			else {
				long[] result = tagcloud.getClassification().stream().map((x)->x.getId()).mapToLong(x -> x).toArray();				
				s.setObject( 1, (Object) result);
			}		
			
	        s.setLong(2, tagcloud.getWebpages().getId());			
			s.setLong(3, tagcloud.getId());
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
		String sql = "DELETE FROM \"tagcloud\" WHERE \"id\" = ?";
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
	public List<Tagcloud> read() throws DaoException {
		String sql = "SELECT \"id\", \"classification\", \"webpages\" FROM \"tagcloud\"";
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Tagcloud> tagclouds = new ArrayList<>();
			while(r.next()) {
			    ArrayList<Long> itagcloud=new ArrayList<>();
			    ArrayList<Classification> tlist= new ArrayList<>();
				Tagcloud tagcloud = new Tagcloud();
				tagcloud.setId(r.getLong("id"));
				
				itagcloud=Helper.objToLongArrayList(r.getObject("classification"));
				itagcloud.forEach((x) -> {
					Classification cl = new Classification();
					cl.setId(x);
					tlist.add(cl);
				  });	
				tagcloud.setClassification(tlist);
				tagcloud.setWebpages(new Webpages());
				tagcloud.getWebpages().setId(r.getLong("webpages"));
				
				tagclouds.add(tagcloud);
			}
			return tagclouds;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<Long> readByWP(Long id) throws DaoException {
		String sql = "SELECT \"id\", \"classification\" FROM \"tagcloud\"  WHERE \"webpages\" = ?";
		  //Tagcloud tagcloud;		
		    ArrayList<Long> itagcloud= new ArrayList<>();
		 //   ArrayList<Classification> tlist= new ArrayList<>();			    
			PreparedStatement s = null;
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					//tagcloud  = new Tagcloud(); tagcloud.setId(id);					
					itagcloud=Helper.objToLongArrayList(r.getObject("classification"));
			/*		itagcloud.forEach((x) -> {
						Classification cl = new Classification();
						cl.setId(x);
						tlist.add(cl);
					  });	
					tagcloud.setClassification(tlist);
					tagcloud.setWebpages(new Webpages());
					tagcloud.getWebpages().setId(r.getLong("webpages")); */									
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}		
		return itagcloud;
	}
///////////////////////////////////////////////////////////////////////////////
}
