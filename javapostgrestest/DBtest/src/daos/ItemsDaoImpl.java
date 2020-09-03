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

import entities.Items;
import entities.ItemsSort;
import entities.Category;
import entities.Classification;
import entities.Img;
import entities.Webpages;
import help.Helper;
import postgres.DaoException;

public class ItemsDaoImpl extends DaoImpl<Items> implements ItemsDao{
	/*private Connection c; public void setConnection(Connection c) {this.c = c;} */	
	private Map<Long, Items> cache = new HashMap<>();
		

	@Override
	public Long create(Items items) throws DaoException {
		String sql = "INSERT INTO \"items\"(\"articul\", \"model\", \"category\", \"baseprice\", \"discount\", \"name\", \"classification\" , \"img\" , \"active\", \"webpages\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement s = null;
		//int isize=0;
		ResultSet r = null;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			s.setString(1, items.getArticul());	
			s.setString(2, items.getModel());	
			s.setLong(3, items.getCategory().getId());
			s.setLong(4, items.getBaseprice());
			s.setInt(5, items.getDiscount());
			s.setString(6, items.getName());
			
			if (items.getClassification()!=null) {
				long[] result = items.getClassification().stream().map((x)->x.getId()).mapToLong(x -> x).toArray();				
				s.setObject( 7, (Object) result);				
				}
			else {s.setObject(7,null);}
			
			if (items.getImg()!=null) {
				long[] result1 = items.getImg().stream().map((x)->x.getId()).mapToLong(x -> x).toArray();					
				s.setObject( 8, (Object) result1);					
				}
			else {s.setObject(8,null);}			
								
			s.setBoolean(9, items.getActive());
			
			if (items.getWebpages()!=null) {s.setLong(10, items.getWebpages().getId());}	
			else {s.setObject(10,null);}
						
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
	public Items read(Long id) throws DaoException {
		String sql = "SELECT \"articul\", \"model\", \"category\", \"baseprice\", \"discount\", \"name\", \"classification\" , \"img\" , \"active\", \"webpages\" FROM \"items\" WHERE \"id\" = ?";
		Items items = cache.get(id);
  
		if(items == null) {
		    ArrayList<Long> iClassification;
		    ArrayList<Long> iImg;
		    ArrayList<Classification> classification= new ArrayList<>();
		    ArrayList<Img> img= new ArrayList<>();
		   			
			PreparedStatement s = null;			
			ResultSet r = null;
			try {
				s = c.prepareStatement(sql);
				s.setLong(1, id);
				r = s.executeQuery();
				if(r.next()) {
					items  = new Items();
					items.setId(id);
					items.setArticul(r.getString("articul"));
					items.setModel(r.getString("model"));
					items.setCategory(new Category()); 
					items.getCategory().setId(r.getLong("category"));	
					items.setBaseprice(r.getLong("baseprice"));
					items.setDiscount(r.getInt("discount"));					
					items.setName(r.getString("name"));					
					iClassification=Helper.objToLongArrayList(r.getObject("classification"));
					iImg=Helper.objToLongArrayList(r.getObject("img"));
					if (iClassification!=null) {iClassification.forEach((x) -> {
						Classification cl = new Classification();
						cl.setId(x);
						classification.add(cl);
					  });}					
					if (iImg!=null) {iImg.forEach((x) -> {
					    Img cl = new Img();					 
						cl.setId(x);
						img.add(cl);
					  }); }
			
					items.setClassification(classification);
					items.setImg(img);
					
					items.setActive(r.getBoolean("active"));
					items.setWebpages(new Webpages()); 
					items.getWebpages().setId(r.getLong("webpages"));	
					
					cache.put(id, items);
				}
			} catch(SQLException e) {
				throw new DaoException(e);
			} finally {
				try { r.close(); } catch(Exception e) {}
				try { s.close(); } catch(Exception e) {}
			}
		}
		return items;
	}

		
	@Override
	public void update(Items items) throws DaoException {		
	    String sql = "UPDATE \"items\" SET \"articul\" = ?, \"model\" = ?, \"category\" = ?, \"baseprice\" = ?, \"discount\" = ?, \"name\" = ?, \"classification\" = ?, \"img\"  = ?, \"active\" = ?, \"webpages\" = ? WHERE \"id\" = ?";
	  //  int isize=0;
	    PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			s.setString(1, items.getArticul());	
			s.setString(2, items.getModel());	
			s.setLong(3, items.getCategory().getId());
			s.setLong(4, items.getBaseprice());
			s.setInt(5, items.getDiscount());
			s.setString(6, items.getName());
			if (items.getClassification()!=null) {
				long[] result = items.getClassification().stream().map((x)->x.getId()).mapToLong(x -> x).toArray();				
				s.setObject( 7, (Object) result);				
				}
			else {s.setObject(7,null);}
			
			if (items.getImg()!=null) {
				long[] result1 = items.getImg().stream().map((x)->x.getId()).mapToLong(x -> x).toArray();				
				s.setObject( 8, (Object) result1);					
				}
			else {s.setObject(8,null);}
			s.setBoolean(9, items.getActive());
			if (items.getWebpages()!=null) {s.setLong(10, items.getWebpages().getId());}	
			else {s.setObject(10,null);}
			s.setLong(11, items.getId());
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
		String sql = "DELETE FROM \"items\" WHERE \"id\" = ?";
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
	public List<Items> read() throws DaoException {
		String sql = "SELECT \"id\", \"articul\", \"model\", \"category\", \"baseprice\", \"discount\", \"name\", \"classification\" , \"img\" , \"active\", \"webpages\" FROM \"items\"";
		Statement s = null;
		ResultSet r = null;
		try {
						
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Items> items = new ArrayList<>();
			while(r.next()) {
				
			    ArrayList<Long> iClassification= new ArrayList<>();
			    ArrayList<Long> iImg= new ArrayList<>();
			    ArrayList<Classification> classification= new ArrayList<>();
			    ArrayList<Img> img= new ArrayList<>();
				
				Items item = new Items();
				item.setId(r.getLong("id"));
				
				item.setArticul(r.getString("articul"));
				item.setModel(r.getString("model"));
				item.setCategory(new Category()); 
				item.getCategory().setId(r.getLong("category"));	
				item.setBaseprice(r.getLong("baseprice"));
				item.setDiscount(r.getInt("discount"));					
				item.setName(r.getString("name"));					
				iClassification=Helper.objToLongArrayList(r.getObject("classification"));
				iImg=Helper.objToLongArrayList(r.getObject("img"));
				if (iClassification!=null) {iClassification.forEach((x) -> {
					Classification cl = new Classification();
					cl.setId(x);
					classification.add(cl);
				  }); 
				}
				if (iImg!=null) {iImg.forEach((x) -> {
				    Img cl = new Img();
					cl.setId(x);
					img.add(cl);
				  });
				}
				item.setClassification(classification);
				item.setImg(img);
				
				item.setActive(r.getBoolean("active"));
				item.setWebpages(new Webpages()); 
				item.getWebpages().setId(r.getLong("webpages"));		
				
				items.add(item);
			}
			return items;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
	@Override
	public List<Items> search(String search) throws DaoException {
	    String sql = String.format("SELECT \"id\", \"articul\", \"model\", \"category\", \"baseprice\", \"discount\", \"name\", \"classification\" , \"img\" , \"active\", \"webpages\" FROM \"items\" WHERE \"name\" LIKE '%%%s%%' ORDER BY \"name\"", search);		
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Items> items = new ArrayList<>();
			while(r.next()) {
				
			    ArrayList<Long> iClassification= new ArrayList<>();
			    ArrayList<Long> iImg= new ArrayList<>();
			    ArrayList<Classification> classification= new ArrayList<>();
			    ArrayList<Img> img= new ArrayList<>();
				
				Items item = new Items();
				item.setId(r.getLong("id"));
				
				item.setArticul(r.getString("articul"));
				item.setModel(r.getString("model"));
				item.setCategory(new Category()); 
				item.getCategory().setId(r.getLong("category"));	
				item.setBaseprice(r.getLong("baseprice"));
				item.setDiscount(r.getInt("discount"));					
				item.setName(r.getString("name"));					
				iClassification=Helper.objToLongArrayList(r.getObject("classification"));
				iImg=Helper.objToLongArrayList(r.getObject("img"));
				if (iClassification!=null) {iClassification.forEach((x) -> {
					Classification cl = new Classification();
					cl.setId(x);
					classification.add(cl);
				  });
				}
				if (iImg!=null) {iImg.forEach((x) -> {
				    Img cl = new Img();
					cl.setId(x);
					img.add(cl);
				  });
				}
				item.setClassification(classification);
				item.setImg(img);
				
				item.setActive(r.getBoolean("active"));
				item.setWebpages(new Webpages()); 
				item.getWebpages().setId(r.getLong("webpages"));		
				
				items.add(item);
			}
			return items;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////
	private String sortOrder(ItemsSort itemsSort) {
		if (itemsSort==ItemsSort.NAMEDESC) {
			return "ORDER BY \"name\" DESC "; 
		}
        if (itemsSort==ItemsSort.PRICEASC) {
        	return "ORDER BY \"baseprice\" ASC ";
		}
        if (itemsSort==ItemsSort.PRICEDESC) {
        	return "ORDER BY \"baseprice\" DESC ";
		}
        return "ORDER BY \"name\" ASC ";	
	}
	
	@Override
	public List<Items> search(String search, ItemsSort itemsSort, Integer limit, Integer page) throws DaoException {
		 /* Select * from items ORDER BY id ASC limit 10 offset 10*0;  */
		String sql = String.format("SELECT \"id\", \"articul\", \"model\", \"category\", \"baseprice\", \"discount\", \"name\", \"classification\" ,"
		+ " \"img\" , \"active\", \"webpages\" FROM \"items\" WHERE \"name\" LIKE '%%%s%%' " + sortOrder(itemsSort) +
		" limit " + limit+" offset " + limit*(page-1), search);		
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Items> items = new ArrayList<>();
			while(r.next()) {
				
			    ArrayList<Long> iClassification= new ArrayList<>();
			    ArrayList<Long> iImg= new ArrayList<>();
			    ArrayList<Classification> classification= new ArrayList<>();
			    ArrayList<Img> img= new ArrayList<>();
				
				Items item = new Items();
				item.setId(r.getLong("id"));
				
				item.setArticul(r.getString("articul"));
				item.setModel(r.getString("model"));
				item.setCategory(new Category()); 
				item.getCategory().setId(r.getLong("category"));	
				item.setBaseprice(r.getLong("baseprice"));
				item.setDiscount(r.getInt("discount"));					
				item.setName(r.getString("name"));					
				iClassification=Helper.objToLongArrayList(r.getObject("classification"));
				iImg=Helper.objToLongArrayList(r.getObject("img"));
				if (iClassification!=null) {iClassification.forEach((x) -> {
					Classification cl = new Classification();
					cl.setId(x);
					classification.add(cl);
				  });
				}
				if (iImg!=null) {iImg.forEach((x) -> {
				    Img cl = new Img();
					cl.setId(x);
					img.add(cl);
				  });
				}
				item.setClassification(classification);
				item.setImg(img);
				
				item.setActive(r.getBoolean("active"));
				item.setWebpages(new Webpages()); 
				item.getWebpages().setId(r.getLong("webpages"));		
				
				items.add(item);
			}
			return items;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<Items> search(Integer category, ItemsSort itemsSort, Integer limit, Integer page) throws DaoException {
	   /* Select * from items ORDER BY id ASC limit 10 offset 10*0;  */				
		String sql = "SELECT \"id\", \"articul\", \"model\", \"category\", \"baseprice\", \"discount\", \"name\", \"classification\" ,"
				+ " \"img\" , \"active\", \"webpages\" FROM \"items\" WHERE \"category\" = " + category + " " + sortOrder(itemsSort) +
				" limit " + limit+" offset " + limit*(page-1);	
		
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();			
			r = s.executeQuery(sql);
			List<Items> items = new ArrayList<>();
			while(r.next()) {
				
			    ArrayList<Long> iClassification= new ArrayList<>();
			    ArrayList<Long> iImg= new ArrayList<>();
			    ArrayList<Classification> classification= new ArrayList<>();
			    ArrayList<Img> img= new ArrayList<>();
				
				Items item = new Items();
				item.setId(r.getLong("id"));
				
				item.setArticul(r.getString("articul"));
				item.setModel(r.getString("model"));
				item.setCategory(new Category()); 
				item.getCategory().setId(r.getLong("category"));	
				item.setBaseprice(r.getLong("baseprice"));
				item.setDiscount(r.getInt("discount"));					
				item.setName(r.getString("name"));					
				iClassification=Helper.objToLongArrayList(r.getObject("classification"));
				iImg=Helper.objToLongArrayList(r.getObject("img"));
				if (iClassification!=null) {iClassification.forEach((x) -> {
					Classification cl = new Classification();
					cl.setId(x);
					classification.add(cl);
				  });
				}
				if (iImg!=null) {iImg.forEach((x) -> {
				    Img cl = new Img();
					cl.setId(x);
					img.add(cl);
				  });
				}
				item.setClassification(classification);
				item.setImg(img);
				
				item.setActive(r.getBoolean("active"));
				item.setWebpages(new Webpages()); 
				item.getWebpages().setId(r.getLong("webpages"));		
				
				items.add(item);
			}
			return items;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	///////////////////////////////////////////////////////////
	@Override
	public List<Items> readPage(ItemsSort itemsSort, Integer limit, Integer page) throws DaoException {
	   /* Select * from items ORDER BY id ASC limit 10 offset 10*0;  */				
		String sql = "SELECT \"id\", \"articul\", \"model\", \"category\", \"baseprice\", \"discount\", \"name\", \"classification\" ,"
				+ " \"img\" , \"active\", \"webpages\" FROM \"items\" " + sortOrder(itemsSort) +
				" limit " + limit+" offset " + limit*(page-1);	
		
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();			
			r = s.executeQuery(sql);
			List<Items> items = new ArrayList<>();
			while(r.next()) {
				
			    ArrayList<Long> iClassification= new ArrayList<>();
			    ArrayList<Long> iImg= new ArrayList<>();
			    ArrayList<Classification> classification= new ArrayList<>();
			    ArrayList<Img> img= new ArrayList<>();
				
				Items item = new Items();
				item.setId(r.getLong("id"));
				
				item.setArticul(r.getString("articul"));
				item.setModel(r.getString("model"));
				item.setCategory(new Category()); 
				item.getCategory().setId(r.getLong("category"));	
				item.setBaseprice(r.getLong("baseprice"));
				item.setDiscount(r.getInt("discount"));					
				item.setName(r.getString("name"));					
				iClassification=Helper.objToLongArrayList(r.getObject("classification"));
				iImg=Helper.objToLongArrayList(r.getObject("img"));
				if (iClassification!=null) {iClassification.forEach((x) -> {
					Classification cl = new Classification();
					cl.setId(x);
					classification.add(cl);
				  });
				}
				if (iImg!=null) {iImg.forEach((x) -> {
				    Img cl = new Img();
					cl.setId(x);
					img.add(cl);
				  });
				}
				item.setClassification(classification);
				item.setImg(img);
				
				item.setActive(r.getBoolean("active"));
				item.setWebpages(new Webpages()); 
				item.getWebpages().setId(r.getLong("webpages"));		
				
				items.add(item);
			}
			return items;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	///////////////////////////////////////////////////////////
	private String queryClassification(List<Integer> classification) {
		StringBuilder SB = new StringBuilder();
		Boolean first=false;
		for (Integer x:classification) {
			if (x!=null) {SB.append((first?" and ":"") + x +" = ANY (\"classification\")");
		  if (!first) {first=true;}	}
		}
		return SB.toString();
	}
	///////////////////////////////////////////////////////////
	private String queryCategories(List<Long> categories) {
		StringBuilder SB = new StringBuilder();
		Boolean first=false;
		for (Long x:categories) {			
			if (x!=null) {SB.append((first?" OR ":"") + x +" = \"category\"");
		  if (!first) {first=true;}	}
		}
		return SB.toString();
	}
	private static String queryClassificationLists(List<List<Integer>> classification) {
		StringBuilder SB = new StringBuilder();
		Boolean first=false; Boolean first2;
		for (List<Integer> list: classification) {
		  if (list!=null && list.size()>0) {	
		    SB.append(first?" and (":"("); first2=false;
		    for (Integer x:list) {
		     //   System.out.println("========================x"+x);		        		        
		    	
		    	if (x!=null) { SB.append((first2?" or ":"") + x +" = ANY (\"classification\")");
		                       if (!first2) {first2=true;}	}
		    }
		    SB.append(")");  if (!first) {first=true;}
		  }  
	    }
		  return SB.toString();
	}
	///////////////////////////////////////////////////////////
	///////////////////////
	@Override
	public List<Items> search(List<Integer> sclassification, ItemsSort itemsSort, Integer limit, Integer page) throws DaoException {
	   /* Select * from items ORDER BY id ASC limit 10 offset 10*0;  */
		/* select * from items where 2 = ANY (classification) and 31 = ANY (classification) */
		String sql = "SELECT \"id\", \"articul\", \"model\", \"category\", \"baseprice\", \"discount\", \"name\", \"classification\" ,"
				+ " \"img\" , \"active\", \"webpages\" FROM \"items\" WHERE " + queryClassification(sclassification) +" "+ sortOrder(itemsSort) +
				" limit " + limit+" offset " + limit*(page-1);	
		
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Items> items = new ArrayList<>();
			while(r.next()) {
				
			    ArrayList<Long> iClassification= new ArrayList<>();
			    ArrayList<Long> iImg= new ArrayList<>();
			    ArrayList<Classification> classification= new ArrayList<>();
			    ArrayList<Img> img= new ArrayList<>();
				
				Items item = new Items();
				item.setId(r.getLong("id"));
				
				item.setArticul(r.getString("articul"));
				item.setModel(r.getString("model"));
				item.setCategory(new Category()); 
				item.getCategory().setId(r.getLong("category"));	
				item.setBaseprice(r.getLong("baseprice"));
				item.setDiscount(r.getInt("discount"));					
				item.setName(r.getString("name"));					
				iClassification=Helper.objToLongArrayList(r.getObject("classification"));
				iImg=Helper.objToLongArrayList(r.getObject("img"));
				if (iClassification!=null) {iClassification.forEach((x) -> {
					Classification cl = new Classification();
					cl.setId(x);
					classification.add(cl);
				  });
				}
				if (iImg!=null) {iImg.forEach((x) -> {
				    Img cl = new Img();
					cl.setId(x);
					img.add(cl);
				  });
				}
				item.setClassification(classification);
				item.setImg(img);
				
				item.setActive(r.getBoolean("active"));
				item.setWebpages(new Webpages()); 
				item.getWebpages().setId(r.getLong("webpages"));		
				
				items.add(item);
			}
			return items;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////	
	@Override
	public List<Items> searchListsCategories(List<List<Integer>> sclassification, ItemsSort itemsSort, Integer limit, Integer page) throws DaoException {
	   /* Select * from items ORDER BY id ASC limit 10 offset 10*0;  */
		/* select * from items where 2 = ANY (classification) and 31 = ANY (classification) */
		String sql = "SELECT \"id\", \"articul\", \"model\", \"category\", \"baseprice\", \"discount\", \"name\", \"classification\" ,"
				+ " \"img\" , \"active\", \"webpages\" FROM \"items\" WHERE " + queryClassificationLists(sclassification)  +" "+ sortOrder(itemsSort)+
				" limit " + limit+" offset " + limit*(page-1);	
	//	System.out.println("==============SQL "+sql);
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Items> items = new ArrayList<>();
			while(r.next()) {
				
			    ArrayList<Long> iClassification= new ArrayList<>();
			    ArrayList<Long> iImg= new ArrayList<>();
			    ArrayList<Classification> classification= new ArrayList<>();
			    ArrayList<Img> img= new ArrayList<>();
				
				Items item = new Items();
				item.setId(r.getLong("id"));
				
				item.setArticul(r.getString("articul"));
				item.setModel(r.getString("model"));
				item.setCategory(new Category()); 
				item.getCategory().setId(r.getLong("category"));	
				item.setBaseprice(r.getLong("baseprice"));
				item.setDiscount(r.getInt("discount"));					
				item.setName(r.getString("name"));					
				iClassification=Helper.objToLongArrayList(r.getObject("classification"));
				iImg=Helper.objToLongArrayList(r.getObject("img"));
				if (iClassification!=null) {iClassification.forEach((x) -> {
					Classification cl = new Classification();
					cl.setId(x);
					classification.add(cl);
				  });
				}
				if (iImg!=null) {iImg.forEach((x) -> {
				    Img cl = new Img();
					cl.setId(x);
					img.add(cl);
				  });
				}
				item.setClassification(classification);
				item.setImg(img);
				
				item.setActive(r.getBoolean("active"));
				item.setWebpages(new Webpages()); 
				item.getWebpages().setId(r.getLong("webpages"));		
				
				items.add(item);
			}
			return items;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<Items> searchCategories(List<Long> categories, ItemsSort itemsSort, Integer limit, Integer page) throws DaoException {
	   /* Select * from items ORDER BY id ASC limit 10 offset 10*0;  */
		/* select * from items where 2 = ANY (classification) and 31 = ANY (classification) */
		String sql = "SELECT \"id\", \"articul\", \"model\", \"category\", \"baseprice\", \"discount\", \"name\", \"classification\" ,"
				+ " \"img\" , \"active\", \"webpages\" FROM \"items\" WHERE " + queryCategories(categories)  +" "+ sortOrder(itemsSort)+
				" limit " + limit+" offset " + limit*(page-1);	
				
		Statement s = null;
		ResultSet r = null;
		try {
			s = c.createStatement();
			r = s.executeQuery(sql);
			List<Items> items = new ArrayList<>();
			while(r.next()) {
				
			    ArrayList<Long> iClassification= new ArrayList<>();
			    ArrayList<Long> iImg= new ArrayList<>();
			    ArrayList<Classification> classification= new ArrayList<>();
			    ArrayList<Img> img= new ArrayList<>();
				
				Items item = new Items();
				item.setId(r.getLong("id"));
				
				item.setArticul(r.getString("articul"));
				item.setModel(r.getString("model"));
				item.setCategory(new Category()); 
				item.getCategory().setId(r.getLong("category"));	
				item.setBaseprice(r.getLong("baseprice"));
				item.setDiscount(r.getInt("discount"));					
				item.setName(r.getString("name"));					
				iClassification=Helper.objToLongArrayList(r.getObject("classification"));
				iImg=Helper.objToLongArrayList(r.getObject("img"));
				if (iClassification!=null) {iClassification.forEach((x) -> {
					Classification cl = new Classification();
					cl.setId(x);
					classification.add(cl);
				  });
				}
				if (iImg!=null) {iImg.forEach((x) -> {
				    Img cl = new Img();
					cl.setId(x);
					img.add(cl);
				  });
				}
				item.setClassification(classification);
				item.setImg(img);
				
				item.setActive(r.getBoolean("active"));
				item.setWebpages(new Webpages()); 
				item.getWebpages().setId(r.getLong("webpages"));		
				
				items.add(item);
			}
			return items;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
}
