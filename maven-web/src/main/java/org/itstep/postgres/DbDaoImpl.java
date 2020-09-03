package org.itstep.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
//import java.util.Date;
//import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.itstep.help.SqlHelp;
import org.itstep.tabs.Entity;
import org.itstep.tabs.Mapper;

public class DbDaoImpl implements DbDao {
	private Connection c;
	//private Mapper mapper;
		
	public DbDaoImpl(Connection c) {		
		this.c = c;	
		//this.mapper = new Mapper();
	}
	public void setConnection(Connection c) {
		this.c = c;
	}
	//////////////////////////////////////////////////
	//////////////////////////////////////////////////
	public String sqlInsert(Entity entity,boolean check) {		
		return "INSERT INTO \"" + entity.getDBName() + "\" (" + entity.fieldsToSql(check) +") VALUES (" + entity.numArgsToSql(check) + ")";		
	}
	public String sqlInsert(String name,String...args) {		
		return "INSERT INTO \"" + name + "\" (" +SqlHelp.fieldsToSql(args) +") VALUES (" + SqlHelp.numArgsToSql(args.length) + ")";		
	}
	//////////////////////////////////////////////////
	//////////////////////////////////////////////////
	//V//
	@Override
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
	@Override
	public Long create(String name,HashMap<String,Object> mapTab) throws DaoException {
		//String sql = "INSERT INTO \"account\"(\"number\", \"balance\", \"typeid\", \"lastsumm\", \"date\") VALUES (?, ?, ?, ?, ?)";
		Entity entity = Mapper.getEntity(name);
		String sql = sqlInsert(entity,true);
		PreparedStatement s = null;
		ResultSet r = null;
		int fieldCounter=0;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // просим, чтобы statement МОГ получить ключи
	       for (Map.Entry<String,Object> entry: mapTab.entrySet()) {
			 if (entry.getValue()==null) {continue;}
			   entity.setToTab(s, ++fieldCounter, entry.getKey(),entry.getValue());			
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
	@Override
	public Long create(String name,ArrayList<String> fields, ArrayList<String> values) throws DaoException {
		//String sql = "INSERT INTO \"account\"(\"number\", \"balance\", \"typeid\", \"lastsumm\", \"date\") VALUES (?, ?, ?, ?, ?)";
		Entity entity = Mapper.getEntity(name);
		String sql = sqlInsert(name,fields.toArray(new String[fields.size()]));
		PreparedStatement s = null;
		ResultSet r = null;	
		int fieldCounter=0;
		try {
			s = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // просим, чтобы statement МОГ получить ключи
	       for (int i=0;i<fields.size();i++) {
			 if (values.get(i)==null) {continue;}
			   entity.setToTab(s, ++fieldCounter, fields.get(i),values.get(i));			
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

	@Override
	public List<Entity> read(String name) throws DaoException {   //Long id
		String sql="SELECT * FROM \"" + name + "\"";
		List<Entity> list=new ArrayList<Entity>();
		Statement s =null;
		ResultSet r =null;	 	
		int ifields;
		String columnName;
		try {
			Entity entity; // = mapper.getEntity(name);
			s= c.createStatement();
			r=s.executeQuery(sql);			
			while(r.next()) {
				entity = Mapper.getEntity(name);
				entity.setDBName(name);
				ifields=r.getMetaData().getColumnCount();
				 for(int i=0;i<ifields;i++) {
					 columnName= r.getMetaData().getColumnName(i+1); // getColumnClassName(i+1);
					 entity.getNameFromTab(r, columnName);					
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
	@Override	
	public Entity read(String name,long id) throws DaoException {   //Long id
		//String sql="SELECT* FROM \"" + name + "\" WHERE \"id\" = ?";";
		String sql="SELECT * FROM \"" + name + "\" WHERE \"id\" = ?";
		PreparedStatement s = null;
		ResultSet r = null;		
		int ifields;
		String columnName;
		try {
			s = c.prepareStatement(sql);
			s.setLong(1, id);
			r = s.executeQuery();			
			Entity entity = Mapper.getEntity(name);
			if(r.next()) {
			  entity.setDBName(name);
			  ifields=r.getMetaData().getColumnCount();
			  for(int i=0;i<ifields;i++) {
				 columnName= r.getMetaData().getColumnName(i+1);
				 entity.getNameFromTab(r, columnName);					
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
	public Map<Long,Object> readField(String name, String tarfield, String field, Object fValue) throws DaoException {   //Long id	
		String sql;
		if (fValue==null) {
		  sql="SELECT \"id\",\""+tarfield +"\" FROM \"" + name + "\" WHERE \"" +field +"\" IS NULL";
		} else { 
		  sql="SELECT \"id\",\""+tarfield +"\" FROM \"" + name + "\" WHERE \"" +field +"\" = ?";}
		PreparedStatement s = null;
		ResultSet r = null;			
		try {
			s = c.prepareStatement(sql);
			Map<Long,Object> entities=new LinkedHashMap<Long,Object>();
			Entity entity = Mapper.getEntity(name);			
			if (fValue!=null) {entity.setForSelect(s, 1, field, fValue);}			
			r = s.executeQuery();				
			while(r.next()) {			  					 			
				 entity.getNameFromTab(r, "id");
				 entities.put(entity.DBgetId(), entity.getObjectFromTab(r, tarfield));
		       }	
				 
			  
			return entities;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	@Override
	public Map<Long,Object> readField(String name, String tarfield, String field, String expr) throws DaoException {   //Long id	
		String sql="SELECT \"id\",\""+tarfield +"\" FROM \"" + name + "\" WHERE \"" +field +"\"" + expr;		
		PreparedStatement s = null;
		ResultSet r = null;			
		try {
			s = c.prepareStatement(sql);
			Map<Long,Object> entities=new LinkedHashMap<Long,Object>();
			Entity entity = Mapper.getEntity(name);									
			r = s.executeQuery();				
			while(r.next()) {			  					 			
				 entity.getNameFromTab(r, "id");
				 entities.put(entity.DBgetId(), entity.getObjectFromTab(r, tarfield));
		       }					 
			  
			return entities;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	/////////////////////////////////
	public List<Entity> read(String name, String field, Object fValue) throws DaoException {   //Long id
		String sql;
		if (fValue==null) {
		  sql="SELECT * FROM \"" + name + "\" WHERE \"" +field +"\" IS NULL";	
		} else {
		 sql="SELECT * FROM \"" + name + "\" WHERE \"" +field +"\" = ?";}
		PreparedStatement s = null;
		ResultSet r = null;		
		int ifields;
		String columnName;
		try {
			s = c.prepareStatement(sql);
			List<Entity> entities=new ArrayList<Entity>();
			Entity entity = Mapper.getEntity(name);			
			if (fValue!=null) {entity.setForSelect(s, 1, field, fValue);}			
			r = s.executeQuery();				
			while(r.next()) {			  		
			  entity.setDBName(name);
			  ifields=r.getMetaData().getColumnCount();
			  for(int i=0;i<ifields;i++) {
				 columnName= r.getMetaData().getColumnName(i+1);
				 entity.getNameFromTab(r, columnName);					
		       }			  
			  entities.add(entity);
			  entity = Mapper.getEntity(name);			 
			 }  
			return entities;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
	public List<Entity> read(String name, String field, String expr) throws DaoException {   //Long id
		String sql="SELECT * FROM \"" + name + "\" WHERE \"" +field +"\"" + expr;			
		PreparedStatement s = null;
		ResultSet r = null;		
		int ifields;
		String columnName;
		try {
			s = c.prepareStatement(sql);
			List<Entity> entities=new ArrayList<Entity>();
			Entity entity = Mapper.getEntity(name);								
			r = s.executeQuery();				
			while(r.next()) {			  		
			  entity.setDBName(name);
			  ifields=r.getMetaData().getColumnCount();
			  for(int i=0;i<ifields;i++) {
				 columnName= r.getMetaData().getColumnName(i+1);
				 entity.getNameFromTab(r, columnName);					
		       }			  
			  entities.add(entity);
			  entity = Mapper.getEntity(name);			 
			 }  
			return entities;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////
	@Override
	public List<Entity> read(String name, String[] field, Object[] fValue) throws DaoException {   //Long id
		//String sql="SELECT FROM \"" + name + "\" WHERE " + SqlHelp.sqlWhereAndEquals(field);
		int flength=field.length;
		if (flength!=fValue.length) {
			System.out.println("the amount of fields is not equal to the amount of values");
			throw new DaoException("the amount of fields is not equal to the amount of values");
		}	
		String sql="SELECT * FROM \"" + name + "\" WHERE " + SqlHelp.sqlWhereAndEquals(field);		
		PreparedStatement s = null;
		ResultSet r = null;		
		int ifields;
		String columnName;
		try {
			s = c.prepareStatement(sql);
			List<Entity> entities=new ArrayList<Entity>();
			Entity entity = Mapper.getEntity(name);			
			for (int i=0;i<field.length;i++) {
				entity.setForSelect(s, i+1, field[i], fValue[i]);}
		   			
			r = s.executeQuery();				
			while(r.next()) {		
		      
			  entity.setDBName(name);
			  ifields=r.getMetaData().getColumnCount();
			  for(int i=0;i<ifields;i++) {
				 columnName= r.getMetaData().getColumnName(i+1);
				 entity.getNameFromTab(r, columnName);					
		       }
			  entities.add(entity);
			  entity = Mapper.getEntity(name);	
			 }  
			return entities;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
	@Override
	public List<Entity> read(String name, String field, Object[] fValue) throws DaoException {   //Long id
		//String sql="SELECT FROM \"" + name + "\" WHERE " + SqlHelp.sqlWhereAndEquals(field);
		int ifields =fValue.length;
		String sql="SELECT * FROM \"" + name + "\" WHERE " + SqlHelp.sqlWhereOrEquals(field,ifields);		
		PreparedStatement s = null;
		ResultSet r = null;		
		
		String columnName;
		try {
			s = c.prepareStatement(sql);
			List<Entity> entities=new ArrayList<Entity>();
			Entity entity = Mapper.getEntity(name);			
			for (int i=0;i<ifields;i++) {
				entity.setForSelect(s, i+1, field, fValue[i]);}
		   			
			r = s.executeQuery();				
			while(r.next()) {		
		      
			  entity.setDBName(name);
			  ifields=r.getMetaData().getColumnCount();
			  for(int i=0;i<ifields;i++) {
				 columnName= r.getMetaData().getColumnName(i+1);
				 entity.getNameFromTab(r, columnName);					
		       }
			  entities.add(entity);
			  entity = Mapper.getEntity(name);	
			 }  
			return entities;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
	@Override
	public Long[] readID(String name, String field, Object fValue) throws DaoException {   //Long id
		//String sql="SELECT FROM \"" + name + "\" WHERE \"" +field +"\" = ?";
		String sql="SELECT * FROM \"" + name + "\" WHERE \"" +field +"\" = ?";
		PreparedStatement s = null;
		ResultSet r = null;		
		try {
			s = c.prepareStatement(sql);
			ArrayList<Long> entities=new ArrayList<Long>();
			Entity entity = Mapper.getEntity(name);			
			entity.setForSelect(s, 1, field, fValue);			
			
			r = s.executeQuery();				
			while(r.next()) {				      			  
				  entities.add(r.getLong("id"));}
			
			Long[] array  = new Long[entities.size()];
			for (int i=0;i<entities.size();i++) {
				array[i]=entities.get(i);}
			
			return array;
			
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
	public Long[] readIDAnd(String name, String field, Object[] fValue) throws DaoException {   //Long id
		//String sql="SELECT FROM \"" + name + "\" WHERE " + SqlHelp.sqlWhereAndEquals(field);
		int ifields =fValue.length;
		String sql="SELECT * FROM \"" + name + "\" WHERE " + SqlHelp.sqlWhereOrEquals(field,ifields);		
		PreparedStatement s = null;
		ResultSet r = null;		
		
		try {
			s = c.prepareStatement(sql);
			ArrayList<Long> entities=new ArrayList<Long>();
			Entity entity = Mapper.getEntity(name);			
			for (int i=0;i<ifields;i++) {
				entity.setForSelect(s, i+1, field, fValue[i]);}
		   			
			r = s.executeQuery();				
			while(r.next()) {				      			  
			  entities.add(r.getLong("id"));}
			
			Long[] array  = new Long[entities.size()];
			for (int i=0;i<entities.size();i++) {
				array[i]=entities.get(i);}
			
			return array;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
	public Long[] readIDOr(String name, String field, Object[] fValue) throws DaoException {   //Long id
		//String sql="SELECT FROM \"" + name + "\" WHERE " + SqlHelp.sqlWhereAndEquals(field);
		int ifields =fValue.length;
		String sql="SELECT * FROM \"" + name + "\" WHERE " + SqlHelp.sqlWhereOrEquals(field,ifields);		
		PreparedStatement s = null;
		ResultSet r = null;		
		
		try {
			s = c.prepareStatement(sql);
			ArrayList<Long> entities=new ArrayList<Long>();
			Entity entity = Mapper.getEntity(name);			
			for (int i=0;i<ifields;i++) {
				entity.setForSelect(s, i+1, field, fValue[i]);}
		   			
			r = s.executeQuery();				
			while(r.next()) {				      			  
			  entities.add(r.getLong("id"));}
			
			Long[] array  = new Long[entities.size()];
			for (int i=0;i<entities.size();i++) {
				array[i]=entities.get(i);}
			
			return array;
		} catch(SQLException e) {
			throw new DaoException(e);
		} finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
	@Override
	public List<Entity> read(String name, String[] field, Number[] fValue, byte[] signs) throws DaoException {   //Long id
		//String sql="SELECT  \"number\", \"balance\", \"typeid\", \"lastsumm\", \"date\" FROM \"account\" WHERE \"id\" = ?";
		int flength=field.length;
		if (flength!=fValue.length) {
			System.out.println("the amount of fields is not equal to the amount of values");
			throw new DaoException("the amount of fields is not equal to the amount of values");
		}
		PreparedStatement s = null;
		ResultSet r = null;	
		try {
		String sql="SELECT * FROM \"" + name + "\" WHERE " + SqlHelp.sqlWhereAnd(field,signs);	
		int ifields;
		String columnName;
		
			s = c.prepareStatement(sql);
			List<Entity> entities=new ArrayList<Entity>();
			Entity entity = Mapper.getEntity(name);			
			for (int i=0;i<field.length;i++) {
				entity.setForSelect(s, i+1, field[i], fValue[i]);}
		   			
			r = s.executeQuery();				
			while(r.next()) {				      
			  entity.setDBName(name);
			  ifields=r.getMetaData().getColumnCount();
			  for(int i=0;i<ifields;i++) {
				 columnName= r.getMetaData().getColumnName(i+1);
				 entity.getNameFromTab(r, columnName);					
		       }
			  entities.add(entity);
			  entity = Mapper.getEntity(name);	
			 }  
			return entities;
		} catch(SQLException e) {
			throw new DaoException(e);
		} catch(Exception f) {
			throw new DaoException(f);
		}finally {
			try { r.close(); } catch(Exception e) {}
			try { s.close(); } catch(Exception e) {}
		}
	}
	
	
////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void update(String name, String field, Object fValue, String tfield, Object tValue) throws DaoException {
	//String str="UPDATE + field + SET  \"balance\" = ?, \"typeid\" = ?, \"lastsumm\" = ?, \"date\" = ?, \"id\" = ? WHERE \"number\" = ?";
		PreparedStatement s = null;		
		try {
		String sql="UPDATE \""+ name +"\" SET \"" + field + "\" = ? WHERE \"" + tfield + "\"= ?";	
	
			s = c.prepareStatement(sql);		
			Entity entity = Mapper.getEntity(name);			
			entity.setForSelect(s, 1, field, fValue);
			entity.setForSelect(s, 2, tfield, tValue);
			s.executeUpdate();				
		
		} catch(SQLException e) {
			throw new DaoException(e);	
		}finally {		
			try { s.close(); } catch(Exception e) {}
		}
		
	}
	
	@Override
	public void update(String name, String field[], Object fValue[], String tfield[], Object tValue[]) throws DaoException {
		int flength=field.length;
		if (flength!=fValue.length) {
			System.out.println("the amount of fields is not equal to the amount of values");
			throw new DaoException("the amount of fields is not equal to the amount of values");
		}
		int tlength=tfield.length;
		if (tlength!=tValue.length) {
			System.out.println("the amount of fields is not equal to the amount of values");
			throw new DaoException("the amount of fields is not equal to the amount of values");
		}
		PreparedStatement s = null;		
		try {
		String sql="UPDATE \""+ name +"\" SET " + SqlHelp.sqlUpdate(field) + " WHERE " + SqlHelp.sqlWhereAndEquals(tfield);		
			s = c.prepareStatement(sql);					
			int ifields=0;					
			Entity entity = Mapper.getEntity(name);			
			for (int i=0;i<flength;i++) {
					entity.setForSelect(s, ++ifields, field[i], fValue[i]);
			}
			for (int i=0;i<tlength;i++) {
				entity.setForSelect(s, ++ifields, tfield[i], tValue[i]);
		   }
			
			s.executeUpdate();				
		
		} catch(SQLException e) {
			throw new DaoException(e);	
		}finally {		
			try { s.close(); } catch(Exception e) {}
		}
		
	}
	
	
	public void update(String name, Long id, ArrayList<String> fields, ArrayList<String> values) throws DaoException {
		int flength=fields.size();
		if (flength!=values.size()) {
			System.out.println("the amount of fields is not equal to the amount of values");
			throw new DaoException("the amount of fields is not equal to the amount of values");
		}
		
		PreparedStatement s = null;		
		try {
		String sql="UPDATE \""+ name +"\" SET " + SqlHelp.sqlUpdate(fields) + " WHERE \"id\"=?";		
			s = c.prepareStatement(sql);					
			int ifields=0;					
			Entity entity = Mapper.getEntity(name);			
			for (int i=0;i<flength;i++) {
					entity.setForSelect(s, ++ifields, fields.get(i), values.get(i));
			}		
			s.setLong(++ifields, id);
			s.executeUpdate();				
		
		} catch(SQLException e) {
			throw new DaoException(e);	
		}finally {		
			try { s.close(); } catch(Exception e) {}
		}
		
	}

	@Override  //V//	
	public void update(Entity entity) throws DaoException {
		String name = entity.getDBName();
		PreparedStatement s = null;		
		String field;
		Object fValue;
		int counter=0;
		try {
			
			String sql="UPDATE \""+ name +"\" SET " + SqlHelp.sqlUpdate(entity.getFieldsList(true)) +" WHERE id=?";
			s = c.prepareStatement(sql);	
			//System.out.println(sql);
			for (Map.Entry<String,Object> entry: entity.getEntityValues().entrySet()) {				
				fValue =  entry.getValue(); field = entry.getKey();
				if (fValue==null || field.equals("id")) {continue;}				
				   field = entry.getKey();
				//   System.out.print(field + " ");
				   entity.setForSelect(s, ++counter, field, fValue);				   	
		    }			
			 entity.setForSelect(s, ++counter, "id", entity.DBgetId());
			//System.out.println(s);												
			s.executeUpdate();				
		
		} catch(SQLException e) {
			throw new DaoException(e);	
		}finally {		
			try { s.close(); } catch(Exception e) {}
		}
		
	}
	@Override
	public void update(Entity entity, boolean chnull) throws DaoException {
		String name = entity.getDBName();
		PreparedStatement s = null;		
		String field;
		Object fValue;
		int counter=0;
		try {
			
			String sql="UPDATE \""+ name +"\" SET " + SqlHelp.sqlUpdate(entity.getFieldsList(chnull)) +" WHERE id=?";
			s = c.prepareStatement(sql);	
			for (Map.Entry<String,Object> entry: entity.getEntityValues().entrySet()) {				
				fValue =  entry.getValue();
				if (chnull && fValue==null) {continue;}
				   field = entry.getKey();
				   entity.setForSelect(s, ++counter, field, fValue);				   	
		    }						
															
			s.executeUpdate();				
		
		} catch(SQLException e) {
			throw new DaoException(e);	
		}finally {		
			try { s.close(); } catch(Exception e) {}
		}
		
	}
	//V//
	@Override
	public boolean delete(String name, Long id) throws DaoException {
		String sql="DELETE FROM \"" + name + "\" WHERE \"id\" = ?";		
		PreparedStatement s = null;
		//Boolean res=false;
		try {
			s = c.prepareStatement(sql);
			s.setLong(1, id);
			s.executeUpdate();			
			return true;
		  } catch(SQLException e) { 
			return false;			
		 } finally {
			try { s.close(); } catch(Exception e) {}			
		 }
	}	
	@Override
	public boolean delete(String name,  String field, Object fValue) throws DaoException {
		String sql="DELETE FROM \"" + name + "\" WHERE \"" +field +"\" = ?";
	//	String sql="DELETE FROM \"" + name + "\" WHERE \"id\" = ?";		
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);
			Entity entity = Mapper.getEntity(name);			
			entity.setForSelect(s, 1, field, fValue);			
			s.executeUpdate();			
			return true;
		  } catch(SQLException e) { 
			return false;			
		 } finally {
			try { s.close(); } catch(Exception e) {}			
		 }
	}
	@Override
	public boolean delete(String name,  String[] field, Object[] fValue) throws DaoException {
		int flength=field.length;
		if (flength!=fValue.length) {
			System.out.println("the amount of fields is not equal to the amount of values");
			throw new DaoException("the amount of fields is not equal to the amount of values");
		}	
		String sql="DELETE FROM \"" + name + "\" WHERE " + SqlHelp.sqlWhereAndEquals(field);		
		PreparedStatement s = null;
		try {
			s = c.prepareStatement(sql);			
			Entity entity = Mapper.getEntity(name);			
			for (int i=0;i<field.length;i++) {
				entity.setForSelect(s, i+1, field[i], fValue[i]);}
			
			s.executeUpdate();			
			return true;
		  } catch(SQLException e) { 
			return false;			
		 } finally {
			try { s.close(); } catch(Exception e) {}			
		 }
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