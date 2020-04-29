package tabs;

import java.sql.ResultSet;
import postgres.DaoException;
import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlGetterS;
import sqlSetGet.SqlSetterL;
import sqlSetGet.SqlSetterS;


public class Webpages extends Entity {
	private String[] robotstype ={"index,follow","noindex,nofollow","noindex,follow"};
	
	private String url;
	private String title;
	private String description;
	private String keywords;
	private String h1;
	private String text;
	private Long categoryid;
	private Long tagurl;
	private String robots="index,follow";
	
	public Webpages() {
		super("webpages");
		entityValues.put("id", null);
		entityValues.put("url", null);
		entityValues.put("title", null);		
		entityValues.put("description", null);
		entityValues.put("keywords", null);		
		entityValues.put("h1", null);
		entityValues.put("text", null);		
		entityValues.put("categoryid", null);
		entityValues.put("tagurl", null);		
		entityValues.put("robots", "index,follow");		
	
		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("url", new SqlSetterS());
		tabSetter.put("title", new SqlSetterS());
		tabSetter.put("description", new SqlSetterS());
		tabSetter.put("keywords", new SqlSetterS());
		tabSetter.put("h1", new SqlSetterS());
		tabSetter.put("text", new SqlSetterS());
		tabSetter.put("categoryid", new SqlSetterL());
		tabSetter.put("tagurl", new SqlSetterL());	
		tabSetter.put("robots", new SqlSetterS());
		
		tabGetter.put("id", new SqlGetterL());
		tabGetter.put("url", new SqlGetterS());
		tabGetter.put("title", new SqlGetterS());
		tabGetter.put("description", new SqlGetterS());
		tabGetter.put("keywords", new SqlGetterS());
		tabGetter.put("h1", new SqlGetterS());
		tabGetter.put("text", new SqlGetterS());
		tabGetter.put("categoryid", new SqlGetterL());
		tabGetter.put("tagurl", new SqlGetterL());
		tabGetter.put("robots", new SqlGetterS());				
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nurl:"+ this.url + "\ntitle:"+ this.title
				 + "\ndescription:"+ this.description + "\nkeywords:"+ this.keywords 
				 + "\nh1:"+ this.h1 + "\ntext:"+ this.text
				 + "\ncategoryid:"+ this.categoryid + "\ntagurl:"+ this.tagurl
				 + "\nrobots:"+ this.robots ; }
	
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
	public Long getCategoryid() {
		return categoryid;}
	public Long getTagurl() {
		return tagurl;}
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
	public Long getCategoryid(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "categoryid"); return categoryid;}
	public Long getTagurl(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "tagurl"); return tagurl;}
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
		if (name.equals("categoryid")) {this.categoryid= (Long) entityValues.get("categoryid"); return;}
		if (name.equals("tagurl")) {this.tagurl= (Long) entityValues.get("tagurl"); return;}
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
		this.categoryid= (Long) entityValues.get("categoryid");
		this.tagurl= (Long) entityValues.get("tagurl");
		this.robots= entityValues.get("robots").toString();
	}
	
	@SuppressWarnings("unused")
	public void setIndex() {
		this.robots = robotstype[0];entityValues.put("robots", robotstype[0]);		}
	@SuppressWarnings("unused")
	public void setNoIndex() {
		this.robots = robotstype[1];entityValues.put("robots", robotstype[1]);		}
	@SuppressWarnings("unused")
	public void setFollow() {
		this.robots = robotstype[2];entityValues.put("robots", robotstype[2]);		}
	
	public boolean chkRobots(String str) {
	  if (str.equals(robotstype[0]) || str.equals(robotstype[1]) || str.equals(robotstype[2])){
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
