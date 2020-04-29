package tabs;

import java.sql.ResultSet;

//import help.Helper;
import postgres.DaoException;
import sqlSetGet.SqlGetterL;
//import sqlSetGet.SqlGetterO;
import sqlSetGet.SqlGetterS;
//import sqlSetGet.SqlSetterArr;
import sqlSetGet.SqlSetterL;
import sqlSetGet.SqlSetterS;

public class Img extends Entity {
	private Long items;
	private String title;
	private String alt;
	private String url;
	
	public Img() {
		super("img");
		entityValues.put("id", null);
		entityValues.put("items", null);
		entityValues.put("title", null);
		entityValues.put("alt", null);	
		entityValues.put("url", null);		

		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("items", new SqlSetterL());		
		tabSetter.put("title", new SqlSetterS());
		tabSetter.put("alt", new SqlSetterS());
		tabSetter.put("url", new SqlSetterS());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("items", new SqlGetterL());	    
		tabGetter.put("title", new SqlGetterS());
		tabGetter.put("alt", new SqlGetterS());
		tabGetter.put("url", new SqlGetterS());
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nitems:"+ this.items + "\ntitle:"+ this.title 
				 + "\nalt:"+ this.alt+ "\nurl:"+ this.url; }					
	
	public Long getItems() {
		return items;}
	public String getTitle() {
		return title;}
	public String getAlt() {
		return alt;}
	public String getUrl() {
		return url;}
	
	public Long getItems(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "items"); return items;}
	public String getTitle(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "title"); return title;}
	public String getAlt(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "alt"); return alt;}
	public String getUrl(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "url"); return url;}
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));
		this.items= (Long) entityValues.get("items");
		this.title=entityValues.get("title").toString();
		this.alt=entityValues.get("alt").toString();
		this.url=entityValues.get("url").toString();
	}

	@Override
	public void cast(String name) {		
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id")); return;}
		if (name.equals("items")) {this.items=(Long)entityValues.get("items"); return;}
		if (name.equals("title")) {this.title=entityValues.get("title").toString();return;}
		if (name.equals("alt")) {this.alt=entityValues.get("alt").toString();return;}
		if (name.equals("url")) {this.url=entityValues.get("url").toString();}
	}

}