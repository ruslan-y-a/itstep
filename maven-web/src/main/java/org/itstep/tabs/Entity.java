package org.itstep.tabs;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.itstep.postgres.DaoException;
import org.itstep.sqlSetGet.SqlGetter;
import org.itstep.sqlSetGet.SqlSetter;
import org.itstep.sqlSetGet.SqlSetterS;

abstract public class Entity implements absEntity, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7630122116440798619L;
	protected String DBName;
	protected LinkedHashMap<String,Object> entityValues;
	protected LinkedHashMap<String,SqlSetter> tabSetter;
	protected LinkedHashMap<String,SqlGetter> tabGetter;
	
	private Long id;

	public Entity(String str) {
	  this.DBName=new String(str);	
	  this.entityValues = new LinkedHashMap<String,Object>();
	  this.tabSetter = new LinkedHashMap<String,SqlSetter>();
	  this.tabGetter = new LinkedHashMap<String,SqlGetter>();
	}

	  @Override
	  public void setToTab (PreparedStatement s, Integer i,String columnName, Object o) throws DaoException {	
		  try { 
		  SqlSetter sqlSetter = tabSetter.get(columnName);         
		  sqlSetter.sqlSet(s, i, o);         
	    } catch (DaoException e) {
		  throw new DaoException(e + " " + columnName); 
	    } 
	  }
	  @Override
	  public void setToTab (PreparedStatement s, Integer i,String columnName) throws DaoException {	
		  try { 
		  SqlSetter sqlSetter = tabSetter.get(columnName);         
		  Object o = entityValues.get(columnName);
		  sqlSetter.sqlSet(s, i, o);            
		  } catch (DaoException e) {
			  throw new DaoException(e + " " + columnName); 
		    } 
	  }
	  public void setToTab (PreparedStatement s, Integer i,String columnName,String value) throws DaoException {	
		  try { 
		  		
		  SqlSetter sqlSetter = tabSetter.get(columnName);
/*		  if (sqlSetter==null) {		
			  for (String str: tabSetter.keySet()) {
				    if (columnName==str || columnName.contentEquals(str) || columnName.indexOf(str)>=0 || str.indexOf(columnName)>=0) {
				    	sqlSetter = tabSetter.get(str); break;   }
			      }
		  }*/		  
		  sqlSetter.sqlSet(s, i, value);      
	    } catch (DaoException e) {
		  throw new DaoException(e + " " + columnName); 
	    } 
	  }
	  @Override
	  public void getNameFromTab (ResultSet r, String columnName) throws DaoException {		  
		  SqlGetter sqlGetter = tabGetter.get(columnName);			   
		  entityValues.put(columnName, sqlGetter.sqlGet(r, columnName));
		 try { this.cast(columnName);}
		 catch(NullPointerException e) {}
	  } 
	  @Override
	  public Object getObjectFromTab (ResultSet r, String columnName) throws DaoException {		  
		  SqlGetter sqlGetter = tabGetter.get(columnName);			   
		  return sqlGetter.sqlGet(r, columnName);		 
	  } 
	  
	  @Override
	  public void setForSelect (PreparedStatement s, Integer i,String columnName, Object o) throws DaoException {	
		 try { 
			 SqlSetter sqlSetter = tabSetter.get(columnName);         		 		 
		     sqlSetter.sqlSet(s, i, o);
		  } catch (DaoException e) {
			  throw new DaoException(e + " " + columnName); 
		  } 
	  }
	  @Override
	  public void setForSelect (PreparedStatement s, Integer i,String columnName, String o) throws DaoException {	
		  try { 
		  SqlSetter sqlSetter = tabSetter.get(columnName);         		 
		  sqlSetter.sqlSet(s, i, o);  
		  } catch (DaoException e) {
			  throw new DaoException(e + " " + columnName); 
		  } 
	  }
/*===================================================================*/	  
	public void setDBName(String dBName) {
		DBName = dBName;
	}

	public HashMap<String, Object> getEntityValues() {
		return entityValues;
	}
	public List<Object> getEntityValuesList() {
		return (List<Object>)entityValues.values();
	}

	public void put(String s,Object o) throws DaoException {
		entityValues.put(s, o);	
	}
	
	public Long DBgetId() {
		return id;
	}

	public void DBsetId(Long id) {
		this.id = id;
	}
	
	public String getDBName() {
		 return DBName;
	  }
	
	
/*=========================================================*/
/*=========================================================*/	
	  public String getSqlDBName() {
		 return "\"" + DBName +"\"";
	  }
	  
	  public String fieldsToSql(String[] args, boolean check) {		  
		  if (args.length==0) return "";
		  StringBuilder ss = new StringBuilder(); 
		  if (check) {
			  if (entityValues.get(args[0])!=null) {   
				  ss.append("\"" + args[0] +"\"");}
	       } else {  
		   ss.append("\"" + args[0] +"\"");}
		  
		  for (int i=1;i<args.length;i++) {
			if (check) {
				  if (entityValues.get(args[i])!=null) {
			         ss.append(", " + "\"" + args[i] +"\"");}
			} else {	  
				ss.append(", " + "\"" + args[i] +"\"");}  
		  }	  
		  return ss.toString();
	  }
	  public String fieldsToSql() {		  
		  String[] args=this.getFieldsList();		  
		  StringBuilder ss = new StringBuilder(); 
		  ss.append("\"" + args[0] +"\"");
		  for (int i=1;i<args.length;i++) {
			  ss.append(", " + "\"" + args[i] +"\""); 		  
		  }	  
		  return ss.toString();
	  }	
	  public String fieldsToSql(boolean check) {		  
		  String[] args=this.getFieldsList();		  
		  StringBuilder ss = new StringBuilder(); 
		  boolean bf=true;
		  for (int i=0;i<args.length;i++) {
			if (entityValues.get(args[i])!=null) {
			  if (bf) {ss.append("\"" + args[i] +"\"");bf=false;}
			  else {ss.append(", " + "\"" + args[i] +"\"");}
			}  
		  }	  
		  return ss.toString();
	  }	
	  public String numArgsToSql(boolean check) {		 
		  StringBuilder ss = new StringBuilder(); 		
		 
		  boolean n=true;
		 if (check) {  
		  for(Object entry : entityValues.values()) {
		 	 if (n) {
		 	  	if (entry!=null) {ss.append("?");n=false;}
		 	 } else {
		 		if (entry!=null) {ss.append(", ?" );}
		 	 }		 		 
		   }
		  
		  }   else {
			  int nargs=entityValues.size();
			  if (nargs==0) return "";
			  ss.append("?");
			  for (int i=1;i<=nargs;i++) {
				  ss.append(", ?" ); 		  
			  }	  
		  } 		 		  			    	    
		  return ss.toString();
	  }
	  public String numArgsToSql() {
		  int nargs=entityValues.size();
		  StringBuilder ss = new StringBuilder(); 		
		  ss.append("?" ); 
		  for (int i=1;i<nargs;i++) {
			  ss.append(", ?" ); 		  
		  }	  
		  return ss.toString();
	  }
	  
	  
	  public Integer getSize() {
		  return entityValues.size();
	  } 
	  
	  public String[] getFieldsList() {
			 return entityValues.keySet().toArray(new String[this.getSize()]);	
		  }
	  public ArrayList<String> getFieldsArrayList() {
		  ArrayList<String> list = new ArrayList<String>();
		  for (String str:entityValues.keySet()) {
			  list.add(str);}
		    return list;	
		  }
	  
	  public String[] getFieldsList(boolean chnull) {
		   if (chnull) {
			 ArrayList<String> ar = new ArrayList<String>(); 
			 for (Map.Entry<String,Object> entry: entityValues.entrySet()) {		
				 if (entry.getValue()!=null && !entry.getKey().equals("id")) {
					 ar.add(entry.getKey());} 
			 }			   
			 return ar.toArray(new String[ar.size()]);  
		   }
		   return entityValues.keySet().toArray(new String[this.getSize()]);	
		  }
	
}