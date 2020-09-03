package org.itstep.tabs;

import java.sql.ResultSet;

import org.itstep.postgres.DaoException;
import org.itstep.sqlSetGet.SqlGetterL;
import org.itstep.sqlSetGet.SqlGetterS;
import org.itstep.sqlSetGet.SqlSetterL;
import org.itstep.sqlSetGet.SqlSetterS;

public class Category  extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2716356823967822252L;
	private Long parentid;
	private String name;
	private Webpages webpages;
	
	public Category() {
		super("category");
		entityValues.put("id", null);
		entityValues.put("parentid", null);
		entityValues.put("name", null);
		entityValues.put("webpages", null);		

		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("parentid", new SqlSetterL());
		tabSetter.put("name", new SqlSetterS());
		tabSetter.put("webpages", new SqlSetterL());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("parentid", new SqlGetterL());
		tabGetter.put("name", new SqlGetterS());
		tabGetter.put("webpages", new SqlGetterL());
		
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nparentid:"+ this.parentid + "\nname:"+ this.name;  
	}		
			
	public Long getParentid() {
		return parentid;}
	public String getName() {
		return name;}
	public Long getParentid(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "parentid"); return parentid;}
	public String getName(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "name"); return name;}
	public Webpages getWebpages(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "webpages"); return webpages;}
	
	@Override
	public void cast() {
	  try {	
		this.DBsetId((Long) entityValues.get("id"));
		this.parentid=(Long) entityValues.get("parentid");
		this.name= entityValues.get("name").toString();
		Webpages webpages=new Webpages(); webpages.DBsetId((Long)entityValues.get("webpages")); this.webpages=webpages;
	  } catch(NullPointerException e) {
		  
	  }
	}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("parentid")) {this.parentid=(Long) entityValues.get("parentid");return;}
		if (name.equals("name")) {this.name= entityValues.get("name").toString();return;}
		if (name.equals("webpages")) { Webpages webpages=new Webpages();
		 webpages.DBsetId((Long)entityValues.get("webpages")); this.webpages=webpages; return;}
	}

}
