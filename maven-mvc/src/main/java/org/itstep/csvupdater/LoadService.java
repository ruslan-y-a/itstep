package org.itstep.csvupdater;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itstep.config.ConnectionThreadHolder;
import org.itstep.csvLoader.CsvLoader;
import org.itstep.csvupdater.entities.Baseitem;
import org.itstep.csvupdater.entities.Client;
import org.itstep.csvupdater.entities.Entity;
import org.itstep.csvupdater.entities.Items;
import org.itstep.csvupdater.entities.Orders;
import org.itstep.csvupdater.entities.Sale;
import org.itstep.csvupdater.entities.Tagcloud;
import org.itstep.daos.BaseitemDaoImpl;
import org.itstep.daos.DaoException;
import org.itstep.help.SqlHelp;
import org.itstep.service.LogicException;



public class LoadService {
	private static final Logger logger = LogManager.getLogger();
	protected Connection c = ConnectionThreadHolder.getConnection();
	protected final Connection getConnection() {return c;}
	public final void setConnection(Connection c) {this.c = c;}
	
	public boolean createCsvLoad(String dbName) throws LogicException {
		Boolean result=false;
		try {
			CsvLoader csvLoader = new CsvLoader(dbName);
			Map<String,ArrayList<String>> mData = csvLoader.Load();
			if (mData==null) { logger.error("CSV FILE IS NOT PARSED"); return false;}
			dbName=getDBName(dbName);
			ArrayList<String> fileds=new ArrayList<String>();
			mData.keySet().forEach((x)->{
				  if (!x.equals("id")) {fileds.add(x);}
				  });
			int rnum =mData.get(fileds.get(0)).size();
			long idc=-1;
			ArrayList<String> values=new ArrayList<String>();						
			for (int i=0;i<rnum;i++) {
				values.clear();
				
				for (Map.Entry<String,ArrayList<String>> entry: mData.entrySet()) {	
				  if (!entry.getKey().equals("id")) {
					 values.add(entry.getValue().get(i));
				  } else {
					  idc=Long.parseLong(entry.getValue().get(i));}
				}				
				if (idc==-1) {				
				   try {create(dbName, fileds, values); result=true;
				       } catch (DaoException e) {				
						e.printStackTrace(); logger.warn("CSV FILE IS NOT LOADED");
						//return false; //throw new LogicException(e);
				       }
				} else {
					try {update(dbName,idc, fileds, values); result=true;
				       } catch (DaoException e) {				
				    	   e.printStackTrace(); logger.warn("CSV FILE IS NOT LOADED");
						//	return false; //throw new LogicException(e);
				       }					
				}
				
			}						
		} catch(ClassNotFoundException | IOException e) {
			   e.printStackTrace(); logger.warn("CSV FILE IS NOT LOADED");
				return false; //throw new LogicException(e);
		}
		return result;
	}
	public boolean createCsvLoad(File file) throws LogicException {
		Boolean result=false;
		try {
			
			CsvLoader csvLoader = new CsvLoader(file);
			Map<String,ArrayList<String>> mData = csvLoader.Load();
			if (mData==null) { logger.error("CSV FILE IS NOT PARSED"); return false;}
			String dbName=getDBName(file.getName());
			ArrayList<String> fileds=new ArrayList<String>();  //!!!fileds
			mData.keySet().forEach((x)->{
				  if (!x.equals("id")) {fileds.add(x);}
				  });
			int rnum =mData.get(fileds.get(0)).size();
			long idc=-1;
			ArrayList<String> values=new ArrayList<String>();						
			for (int i=0;i<rnum;i++) {
				values.clear();
				
				for (Map.Entry<String,ArrayList<String>> entry: mData.entrySet()) {	
				  if (!entry.getKey().equals("id")) {
					 values.add(entry.getValue().get(i));	
				  } else {
					  idc=Long.parseLong(entry.getValue().get(i));}
				}				
				if (idc==-1) {				
				   try {create(dbName, fileds, values);result=true;
				       } catch (DaoException e) {				
				    	   e.printStackTrace(); logger.warn("CSV FILE IS NOT LOADED");
							//return false; //throw new LogicException(e);
				       }
				} else {
					try {update(dbName,idc, fileds, values);result=true;
				       } catch (DaoException e) {				
				    	   e.printStackTrace(); logger.warn("CSV FILE IS NOT LOADED");
						//	return false; //throw new LogicException(e);
				       }					
				}
				
			}						
		} catch(ClassNotFoundException | IOException e) {
			  e.printStackTrace(); logger.warn("CSV FILE IS NOT LOADED");
				return false; //throw new LogicException(e);
		}
		return result;
	}
//////////////////////////////////////////////////////////////////////////////////////////////////
	public Long create(String name,ArrayList<String> fields, ArrayList<String> values) throws DaoException {
		//String sql = "INSERT INTO \"account\"(\"number\", \"balance\", \"typeid\", \"lastsumm\", \"date\") VALUES (?, ?, ?, ?, ?)";
		Entity entity = getEntity(name);
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
			Entity entity = getEntity(name);			
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
/////////////////////////////	
	public String sqlInsert(Entity entity,boolean check) {		
		return "INSERT INTO \"" + entity.getDBName() + "\" (" + entity.fieldsToSql(check) +") VALUES (" + entity.numArgsToSql(check) + ")";		
	}
	public String sqlInsert(String name,String...args) {		
		return "INSERT INTO \"" + name + "\" (" +SqlHelp.fieldsToSql(args) +") VALUES (" + SqlHelp.numArgsToSql(args.length) + ")";		
	}
///////////////////////////////////////////////////////////////////////////////////////////////
/*	private Long getEntityNoID(String name, ArrayList<String> fileds, Map<String,ArrayList<String>> mData) {
		Long bil=null;
		if (name.contains("baseitem")) {
			int i1=fileds.indexOf("itemid"); int i2=fileds.indexOf("color"); int i3=fileds.indexOf("size");
		   if (i1>=0 && i2>=0 && i3>=0) {
			   long l1=Long.parseLong(mData.get("itemid").get(i1)); 
			   long l2=Long.parseLong(mData.get("color").get(i2)); 
			   long l3=Long.parseLong(mData.get("size").get(i3)); 
			   BaseitemDaoImpl bDao = new BaseitemDaoImpl(); bDao.setConnection(c);
			   try {
				org.itstep.entities.Baseitem bi=bDao.readByICS(l1,l2,l3);
				bil = bi.getId();
			   } catch (DaoException e) {e.printStackTrace(); return null;}
		   }
		}
		
		return bil;
	}
	*/
	////////////////////////////////////////////////////////////////////////////////////////////////
	/*
	public static String fieldsToSql(String[] args) {		  
		  if (args.length==0) return "";
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"");
		  for (int i=1;i<args.length;i++) {
			  ss.append(", " + "\"" + args[i] +"\""); 		  
		  }	  
		  return ss.toString();
	  }
	  public static String numArgsToSql(int nargs) {
		  if (nargs==0) return "";
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(", ?" );}
		  
		  return ss.toString();
	  }
	  public static String sqlUpdate(String[] args) {		  
		  int nargs=args.length;
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"= ?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(", \"" + args[i] +"\"= ?");}		  
		  return ss.toString();
	  }
	  
	  public static String sqlUpdate(ArrayList<String> args) {		  
		  int nargs=args.size();
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args.get(0) +"\"= ?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(", \"" + args.get(i) +"\"= ?");}		  
		  return ss.toString();
	  }
	  
	  public static String sqlWhereAndEquals(String[] args) {		  
		  int nargs=args.length;
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"= ?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(" AND \"" + args[i] +"\"= ?");}		  
		  return ss.toString();
	  }
	  public static String sqlWhereAndEquals(String str, int nargs) {		  		 
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + str +"\"= ?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(" AND \"" + str +"\"= ?");}		  
		  return ss.toString();
	  }
	  public static String sqlWhereOrEquals(String[] args) {		  
		  int nargs=args.length;
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"= ?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(" OR \"" + args[i] +"\"=  ?");}		  
		  return ss.toString();
	  }
	  public static String sqlWhereOrEquals(String str, int nargs) {		  		
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + str +"\"= ?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(" OR \"" + str +"\"=  ?");}		  
		  return ss.toString();
	  }
	  
	  public static String sign(byte sign) {
		  if (sign>0) return ">";
		  if (sign<0) return "<";
		  return "=";  
	  }
	  public static String sqlWhereAnd(String[] args, byte[]signs) throws Exception {		  
		  int nargs=args.length;
		  if (args.length!=signs.length) {throw new Exception("arguments are not valid");}
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"" +sign(signs[0])+"?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(" AND \"" + args[i] + "\"" +sign(signs[0])+"?");}
		  
		  return ss.toString();
	  }
	  public static String sqlWhereOr(String[] args, byte[]signs) throws Exception {		  
		  int nargs=args.length;
		  if (args.length!=signs.length) {throw new Exception("arguments are not valid");}
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"" +sign(signs[0])+"?");
		  for (int i=1;i<nargs;i++) {
			  ss.append(" OR \"" + args[i] + "\"" +sign(signs[0])+"?");}
		  
		  return ss.toString();
	  }
////////////////////////////////////////////////////////////////////////////////
		*/
	
	  public static String getDBName(String str) {
		  if (str.isBlank()) {return null;} 

		  if (str.indexOf("client")>=0)      {return "client";}
		  if (str.indexOf("items")>=0)       {return "items";}
		  if (str.indexOf("baseitem")>=0)    {return "baseitem";}
		  if (str.indexOf("orders")>=0)      {return "orders";}
		  if (str.indexOf("sale")>=0)        {return "sale";}
		  if (str.indexOf("tagcloud")>=0)    {return "tagcloud";}	
	
			return null;	  
		  }
	  
	   public static ArrayList<String> getEntityFieldsforName(String name){	    
		return getEntity(name).getFieldsArrayList();}	   
	   
		 public static Entity getEntity(String name) {
		
				if (name.equals("client"))  {return new Client();}			
				if (name.equals("items"))  {return new Items();}
				if (name.equals("baseitem"))  {return new Baseitem();}
				if (name.equals("orders"))  {return new Orders();}
				if (name.equals("sale"))  {return new Sale();}
				if (name.equals("tagcloud"))  {return new Tagcloud();}

				return null;    
			  }

}
