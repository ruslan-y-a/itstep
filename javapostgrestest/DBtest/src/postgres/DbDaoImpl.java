package postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Date;
//import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import tabs.Entity;
import tabs.Mapper;

public class DbDaoImpl implements DbDao {
	private Connection c;
	private Mapper mapper;
		
	public DbDaoImpl(Connection c) {		
		this.c = c;	
		this.mapper = new Mapper();
	}
	public void setConnection(Connection c) {
		this.c = c;
	}
	//////////////////////////////////////////////////
	//////////////////////////////////////////////////
	public String sqlInsert(Entity entity,boolean check) {		
		return "INSERT INTO \"" + entity.getDBName() + "\"(" + entity.fieldsToSql(check) +") VALUES (" + entity.numArgsToSql(check) + ")";		
	}
	//////////////////////////////////////////////////
	//////////////////////////////////////////////////
	public Long create(Entity entity) throws DaoException {
		//String sql = "INSERT INTO \"account\"(\"number\", \"balance\", \"typeid\", \"lastsumm\", \"date\") VALUES (?, ?, ?, ?, ?)";
		String sql = sqlInsert(entity,true);
		PreparedStatement s = null;
		ResultSet r = null;
		int fieldCounter=0;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // просим, чтобы statement МОГ получить ключи
	       for (Map.Entry<String,Object> entry: entity.getEntityValues().entrySet()) {
			 if (entry.getValue()==null) {continue;}
			   entity.setToTab(s, ++fieldCounter, entry.getKey());			
	         }
		
			s.executeUpdate();
			r = s.getGeneratedKeys(); // ПОЛУЧАЕМ сгенерированные ключи (не работает без Statement.RETURN_GENERATED_KEYS)
			r.next();
			return r.getLong(1);
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
			try { r.close(); } catch(Exception e) {}
		}
	}				

//	@Override
	public List<Entity> read(String name) throws DaoException {   //Long id
		String sql="SELECT * FROM \"" + name + "\"";
		List<Entity> list=new ArrayList<Entity>();
		Statement s =null;
		ResultSet r =null;	 	
		int ifields;
		String columnName;
		try {
			Entity entity = mapper.getEntity(name);
			s= c.createStatement();
			r=s.executeQuery(sql);			
			while(r.next()) {
				entity.setDBName(name);
				ifields=r.getMetaData().getColumnCount();
				 for(int i=0;i<ifields;i++) {
					 columnName= r.getMetaData().getColumnName(i+1); // getColumnClassName(i+1);
					 entity.getFromTab(r, columnName);					
			      }   									
				list.add(entity);				
			}
			return list;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}	
		
	public Entity read(String name,long id) throws DaoException {   //Long id
		//String sql="SELECT  \"number\", \"balance\", \"typeid\", \"lastsumm\", \"date\" FROM \"account\" WHERE \"id\" = ?";
		String sql="SELECT * FROM \"" + name + "\" WHERE \"id\" = ?";
		PreparedStatement s = null;
		ResultSet r = null;		
		int ifields;
		String columnName;
		try {
			s = c.prepareStatement(sql);
			s.setLong(1, id);
			r = s.executeQuery();			
			Entity entity = mapper.getEntity(name);
			if(r.next()) {
			  entity.setDBName(name);
			  ifields=r.getMetaData().getColumnCount();
			  for(int i=0;i<ifields;i++) {
				 columnName= r.getMetaData().getColumnName(i+1);
				 entity.getFromTab(r, columnName);					
		       }
			 }  
			return entity;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
	public Entity read(String name, String field, Object fValue) throws DaoException {   //Long id
		//String sql="SELECT  \"number\", \"balance\", \"typeid\", \"lastsumm\", \"date\" FROM \"account\" WHERE \"id\" = ?";
		String sql="SELECT * FROM \"" + name + "\" WHERE \"" +field +"\" = ?";
		PreparedStatement s = null;
		ResultSet r = null;		
		int ifields;
		String columnName;
		try {
			s = c.prepareStatement(sql);
			Entity entity = mapper.getEntity(name);
			entity.setDBName(name);
			entity.setForSelect(s, 1, field, fValue);			
			r = s.executeQuery();				
			if(r.next()) {			
			  ifields=r.getMetaData().getColumnCount();
			  for(int i=0;i<ifields;i++) {
				 columnName= r.getMetaData().getColumnName(i+1);
				 entity.getFromTab(r, columnName);					
		       }
			 }  
			return entity;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}

	@Override
	public void update(Entity entity) throws DaoException {
		
	}
	
	@Override
	public boolean delete(String name, Long id) throws DaoException {
		// TODO Auto-generated method stub
		return false;
	}
	
	/*	
	@Override
	public void update(account myaccount) throws DaoException {
		//String str="UPDATE \"account\" SET \"number\" = ?, \"balance\" = ?, \"typeid\" = ?, \"lastsumm\" = ?, \"date\" = ? WHERE \"id\" = ?";
		String str="";
		String str0="UPDATE \"account\" SET "; 
		Integer argcounter=0;
		PreparedStatement s = null;
		Long acc=null;
		long balance;
		Long lsum=null;
		Integer typeid=null;
		Date tDate=null;
		try {
			acc=myaccount.getAcc();
			if (acc!=null) {++argcounter;str=new String("\"number\" = ?");}
			balance=myaccount.getBalance();
			if (balance>=0) {
				str=new String(str + (argcounter++==0?"":", ")+ "\"balance\" = ?");}
			if (myaccount.getType()!=null) {typeid=myaccount.getType().DBgetId();}
			if (typeid!=null) {
				str=new String(str + (argcounter++==0?"":", ")+ "\"typeid\" = ?");}
			lsum=myaccount.getLastsumm();
			if (lsum!=null) {
				str=new String(str + (argcounter++==0?"":", ")+ "\"lastsumm\" = ?");}
			tDate=myaccount.getDate();
			if (tDate!=null) {
				str=new String(str + (argcounter++==0?"":", ")+ "\"date\" = ?");}
			
			str=new String(str0 + str + " WHERE \"id\" = ?"); 
			s = c.prepareStatement(str);
			argcounter=0;
			if (acc!=null) { s.setLong(++argcounter, acc);}
			if (balance>=0) { s.setLong(++argcounter, balance);}
			if (typeid!=null) { s.setInt(++argcounter, typeid);
			this.categoryDao.update(myaccount.getType());
			}
			if (lsum!=null) { s.setLong(++argcounter, lsum);}
			if (tDate!=null) { s.setDate(++argcounter,new java.sql.Date(tDate.getTime()));}			
			s.setLong(++argcounter, myaccount.DBgetId());
			s.executeUpdate();
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { s.close(); } catch(Exception e) {}
		}
	}

///////////////////////////////////////////////////////////	
	@Override
	public boolean delete(Integer id) throws DaoException {
		String sql = "DELETE FROM \"account\" WHERE \"id\" = ?";
		PreparedStatement s = null;
		//Boolean res=false;
		try {
			s = c.prepareStatement(sql);
			s.setInt(1, id);
			s.executeUpdate();			
			return true;
		  } catch(SQLException e) { 
			return false;			
		 } finally {
			try { s.close(); } catch(Exception e) {}			
		 }
	}
	@Override
	public boolean delete(Long account) throws DaoException {
		String sql = "DELETE FROM \"account\" WHERE \"number\" = ?";
		PreparedStatement s = null;
		//Boolean res=false;
		try {
			s = c.prepareStatement(sql);
			s.setLong(1, account);
			s.executeUpdate();			
			return true;
		  } catch(SQLException e) {
			return false;
			//throw new DaoException(e);			
		 } finally {
			try { s.close(); } catch(Exception e) {}			
		 }
	}

	////////////////////////////////////////////////////////////


/*
 *  if (entry==null) {continue;}
	    	 classname = entry.getValue().getClass().toString();
	    	   if (classname.indexOf("String")>=0) { 
                   s.setString(++fieldCounter, entry.getValue().toString().trim());
                   continue;}
	    	   if (classname.indexOf("Integer")>=0) { 
                   s.setInt(++fieldCounter,(Integer) entry.getValue());
                   continue;}
	   	       if (classname.indexOf("Long")>=0) { 
	   	    	   s.setLong(++fieldCounter,(Long) entry.getValue());
                   continue;}
 	   	       if (classname.indexOf("Double")>=0) { 
 	   	    	   s.setDouble(++fieldCounter,(Double) entry.getValue());
                   continue;} 	   	       
	   	       if (classname.indexOf("Date")>=0) {	   	    	   
	   	    	 Date date =  (Date)entry.getValue();
	   	    	  if (entry.getKey().indexOf("time")>=0) {   
	   	    	    s.setTime(++fieldCounter,new java.sql.Time(date.getTime()));}
	   	    	  else {
	   	    		s.setDate(++fieldCounter,new java.sql.Date(date.getTime()));}
	   	    	continue;
	   	       } 	   
 	   	       if (classname.indexOf("Object")>=0) { 
 	   	    	   s.setObject(++fieldCounter, entry.getValue());
                   continue;} 
	           }
 */

	
}