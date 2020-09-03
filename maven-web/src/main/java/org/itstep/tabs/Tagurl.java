package org.itstep.tabs;

import java.sql.ResultSet;

import org.itstep.postgres.DaoException;
import org.itstep.sqlSetGet.SqlGetterL;
import org.itstep.sqlSetGet.SqlGetterS;
import org.itstep.sqlSetGet.SqlSetterL;
import org.itstep.sqlSetGet.SqlSetterS;

public class Tagurl extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3054416628412216675L;
	private String tagname;
	private String url;
	
	public Tagurl() {
		super("tagurl");
		entityValues.put("id", null);
		entityValues.put("tagname", null);
		entityValues.put("url", null);		

		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("tagname", new SqlSetterS());
		tabSetter.put("url", new SqlSetterS());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("tagname", new SqlGetterS());
		tabGetter.put("url", new SqlGetterS());
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\ntagname:"+ this.tagname + "\nurl:"+ this.url; }		
			
	public String getTagname() {
		return tagname;}
	public String getUrl() {
		return url;}

	public String getTagname(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "tagname"); return tagname;}
	public String getUrl(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "url"); return url;}
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));
		this.tagname=entityValues.get("tagname").toString();
		this.url= entityValues.get("url").toString();
	}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("tagname")) {this.tagname=entityValues.get("tagname").toString();return;}
		if (name.equals("url")) {this.url= entityValues.get("url").toString();}
	}

}
