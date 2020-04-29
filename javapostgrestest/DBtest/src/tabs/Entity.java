package tabs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import postgres.DaoException;
import sqlSetGet.SqlGetter;
import sqlSetGet.SqlSetter;

abstract public class Entity implements absEntity{
	protected String DBName;
	protected HashMap<String,Object> entityValues;
	protected HashMap<String,SqlSetter> tabSetter;
	protected HashMap<String,SqlGetter> tabGetter;
	
	private Long id;

	public Entity(String str) {
	  this.DBName=new String(str);	
	  this.entityValues = new HashMap<String,Object>();
	  this.tabSetter = new HashMap<String,SqlSetter>();
	  this.tabGetter = new HashMap<String,SqlGetter>();
	}

	  @Override
	  public void setToTab (PreparedStatement s, Integer i,String columnName) throws DaoException {	
		  SqlSetter sqlSetter = tabSetter.get(columnName);         
		  Object o = entityValues.get(columnName);
		  sqlSetter.sqlSet(s, i, o);            
	  }
	  public void setToTab (PreparedStatement s, Integer i,String columnName,String value) throws DaoException {	
		  SqlSetter sqlSetter = tabSetter.get(columnName);         		  
		  sqlSetter.sqlSet(s, i, value);            
	  }
	  @Override
	  public void getNameFromTab (ResultSet r, String columnName) throws DaoException {		  
		  SqlGetter sqlGetter = tabGetter.get(columnName);			   
		  entityValues.put(columnName, sqlGetter.sqlGet(r, columnName));
		 try { this.cast(columnName);}
		 catch(NullPointerException e) {}
	  }  
	  
	  @Override
	  public void setForSelect (PreparedStatement s, Integer i,String columnName, Object o) throws DaoException {	
		  SqlSetter sqlSetter = tabSetter.get(columnName);         		 
		  sqlSetter.sqlSet(s, i, o);            
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
	  public String[] getFieldsList(boolean chnull) {
		   if (chnull) {
			 ArrayList<String> ar = new ArrayList<String>(); 
			 for (Map.Entry<String,Object> entry: entityValues.entrySet()) {		
				 if (entry.getValue()!=null) {
					 ar.add(entry.getKey());} 
			 }			   
			 return ar.toArray(new String[this.getSize()]);  
		   }
		   return entityValues.keySet().toArray(new String[this.getSize()]);	
		  }
	
}