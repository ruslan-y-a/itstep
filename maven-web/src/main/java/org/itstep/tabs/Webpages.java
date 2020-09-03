package org.itstep.tabs;

import java.sql.ResultSet;
import org.itstep.postgres.DaoException;
import org.itstep.sqlSetGet.SqlGetterL;
import org.itstep.sqlSetGet.SqlGetterS;
import org.itstep.sqlSetGet.SqlSetterL;
import org.itstep.sqlSetGet.SqlSetterS;


public class Webpages extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5207099217272128948L;

	private static final String[] ROBOTSTYPE ={"index,follow","noindex,nofollow","noindex,follow"};
	
	private String url;
	private String title;
	private String description;
	private String keywords;
	private String h1;
	private String text;
	private String robots=ROBOTSTYPE[0];
	
	public Webpages() {
		super("webpages");
		entityValues.put("id", null);
		entityValues.put("url", null);
		entityValues.put("title", null);		
		entityValues.put("description", null);
		entityValues.put("keywords", null);		
		entityValues.put("h1", null);
		entityValues.put("text", null);		
		entityValues.put("robots", "index,follow");		
	
		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("url", new SqlSetterS());
		tabSetter.put("title", new SqlSetterS());
		tabSetter.put("description", new SqlSetterS());
		tabSetter.put("keywords", new SqlSetterS());
		tabSetter.put("h1", new SqlSetterS());
		tabSetter.put("text", new SqlSetterS());
		tabSetter.put("robots", new SqlSetterS());
		
		tabGetter.put("id", new SqlGetterL());
		tabGetter.put("url", new SqlGetterS());
		tabGetter.put("title", new SqlGetterS());
		tabGetter.put("description", new SqlGetterS());
		tabGetter.put("keywords", new SqlGetterS());
		tabGetter.put("h1", new SqlGetterS());
		tabGetter.put("text", new SqlGetterS());
		tabGetter.put("robots", new SqlGetterS());				
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nurl:"+ this.url + "\ntitle:"+ this.title
				 + "\ndescription:"+ this.description + "\nkeywords:"+ this.keywords 
				 + "\nh1:"+ this.h1 + "\ntext:"+ this.text
				 + "\ncategoryid:"	 + "\nrobots:"+ this.robots ; }
	
	public String getUrl() {
		return url;}
	public String getTitle() {
		return title;}
	public String getDescription() {
		return description;}
	public String getKeywords() {
		return keywords;}
	public String getH1() {
		return h1;}
	public String getText() {
		return text;}
		public String getRobots() {
		return robots;}

	public String getUrl(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "url"); return url;}
	public String getTitle(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "title"); return title;}
	public String getDescription(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "description"); return description;}
	public String getKeywords(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "keywords"); return keywords;}
	public String getH1(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "h1"); return h1;}
	public String getText(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "text"); return text;}
	public String getRobots(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "robots"); return robots;}
	
	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("url")) {this.url= entityValues.get("url").toString(); return;}
		if (name.equals("title")) {this.title=  entityValues.get("title").toString(); return;}
		if (name.equals("description")) {this.description= entityValues.get("description").toString(); return;}
		if (name.equals("keywords")) {this.keywords= entityValues.get("keywords").toString(); return;}
		if (name.equals("h1")) {this.h1=  entityValues.get("h1").toString(); return;}
		if (name.equals("text")) {this.text=  entityValues.get("text").toString(); return;}
		if (name.equals("robots")) {this.robots= entityValues.get("robots").toString();}		
	}

	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));		
		this.url= entityValues.get("url").toString();
		this.title=  entityValues.get("title").toString();
		this.description= entityValues.get("description").toString();
		this.keywords= entityValues.get("keywords").toString();
		this.h1=  entityValues.get("h1").toString();
		this.text=  entityValues.get("text").toString();
		this.robots= entityValues.get("robots").toString();
	}
	
	
	public void setIndex() {
		this.robots = ROBOTSTYPE[0];entityValues.put("robots", ROBOTSTYPE[0]);		}
	
	public void setNoIndex() {
		this.robots = ROBOTSTYPE[1];entityValues.put("robots", ROBOTSTYPE[1]);		}
	
	public void setFollow() {
		this.robots = ROBOTSTYPE[2];entityValues.put("robots", ROBOTSTYPE[2]);		}
	
	public boolean chkRobots(String str) {
	  if (str.equals(ROBOTSTYPE[0]) || str.equals(ROBOTSTYPE[1]) || str.equals(ROBOTSTYPE[2])){
		return true;}	
		return false;
	}
	
	
	  @Override
	  public void put(String s,Object o) throws DaoException{
		  if (s.equals("robots")) {
			  if (!chkRobots(o.toString())) {
				  throw new DaoException("Error in settings for robots:" +o.toString());}
			 }
			entityValues.put(s, o);	
		}
	
}
