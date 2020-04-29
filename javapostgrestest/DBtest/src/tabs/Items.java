package tabs;

import java.sql.ResultSet;
//import java.util.Date;
//import java.util.HashMap;

//import help.Helper;
import postgres.DaoException;
import sqlSetGet.SqlGetterI;
import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlGetterS;
import sqlSetGet.SqlSetterI;
import sqlSetGet.SqlSetterL;
import sqlSetGet.SqlSetterS;

public class Items extends Entity {
   private String articul;
   private String model;
   private Long baseprice;
   private Integer discount;	
   private String title;
   private String text;
   private String name;
   private String description;
   private Long tagurl;	
   private String keywords;
   private String mainimgurl;
   private String url;
	
	 public Items() {	  		  
		    super("items");
		    entityValues.put("id", null);
		    entityValues.put("articul", null);
		    entityValues.put("model", null);
		    entityValues.put("baseprice", null);
		    entityValues.put("discount", null);	
		    entityValues.put("title", null);
		    entityValues.put("text", null);
		    entityValues.put("name", null);
		    entityValues.put("description", null);
		    entityValues.put("tagurl", null);	
		    entityValues.put("keywords", null);
		    entityValues.put("mainimgurl", null);
		    entityValues.put("url", null);
		    
		    tabSetter.put("id", new SqlSetterL());
		    tabSetter.put("articul", new SqlSetterS());
		    tabSetter.put("model", new SqlSetterS());
		    tabSetter.put("baseprice", new SqlSetterL());
		    tabSetter.put("discount", new SqlSetterI());
		    tabSetter.put("title", new SqlSetterS());
		    tabSetter.put("text", new SqlSetterS());
		    tabSetter.put("name", new SqlSetterS());
		    tabSetter.put("description", new SqlSetterS());
		    tabSetter.put("tagurl", new SqlSetterL());
		    tabSetter.put("keywords", new SqlSetterS());
		    tabSetter.put("mainimgurl", new SqlSetterS());
		    tabSetter.put("url", new SqlSetterS());
		    
		    tabGetter.put("id", new SqlGetterL());
		    tabGetter.put("articul", new SqlGetterS());
		    tabGetter.put("model", new SqlGetterS());
		    tabGetter.put("baseprice", new SqlGetterL());
		    tabGetter.put("discount", new SqlGetterI());  
		    tabGetter.put("title", new SqlGetterS());
		    tabGetter.put("text", new SqlGetterS());
		    tabGetter.put("name", new SqlGetterS());
		    tabGetter.put("description", new SqlGetterS());  
		    tabGetter.put("tagurl", new SqlGetterL());
		    tabGetter.put("keywords", new SqlGetterS());
		    tabGetter.put("mainimgurl", new SqlGetterS());
		    tabGetter.put("url", new SqlGetterS());  
		    
		  }
		  @Override
		  public String toString() {		  
			 return "id:" + this.DBgetId() + "\narticul:"+  this.articul + "\nmodel:" + this.model + 
			 "\nbaseprice:"+this.baseprice +"\ndiscount:" + this.discount +
			 "\ntitle:" + this.title +   "\ntext:"+this.text +"\nname:" + this.name +
			    "\ndescription:"+this.description +"\ntagurl:" + this.tagurl +
			     "\nkeywords:"+this.keywords +"\nmainimgurl:" + this.mainimgurl +
			     "\nurl:" + this.url;
		  }
			
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id")); 
		this.articul= entityValues.get("articul").toString();
		this.model= entityValues.get("model").toString();
		this.baseprice=(Long) entityValues.get("baseprice");
		this.discount=(Integer) entityValues.get("discount");
		this.title=  entityValues.get("title").toString();
		this.text=  entityValues.get("text").toString();
		this.name= entityValues.get("name").toString();	
		this.description= entityValues.get("description").toString();
		this.tagurl=(Long) entityValues.get("tagurl");
		this.keywords= entityValues.get("keywords").toString();
		this.mainimgurl= entityValues.get("mainimgurl").toString();
		this.url= entityValues.get("url").toString();
	}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("articul")) {this.articul= entityValues.get("articul").toString();return;}
		if (name.equals("model")) {this.model= entityValues.get("model").toString();return;}
		if (name.equals("baseprice")) {this.baseprice=(Long) entityValues.get("baseprice");return;}
		if (name.equals("discount")) {this.discount=(Integer) entityValues.get("discount");return;}
		if (name.equals("title")) {this.title=  entityValues.get("title").toString();return;}
		if (name.equals("text")) {this.text=  entityValues.get("text").toString();return;}
		if (name.equals("name")) {this.name= entityValues.get("name").toString();return;}	
		if (name.equals("description")) {this.description= entityValues.get("description").toString();return;}		
		if (name.equals("tagurl")) {this.tagurl=(Long) entityValues.get("tagurl");return;}
		if (name.equals("keywords")) {this.keywords= entityValues.get("keywords").toString();return;}
		if (name.equals("mainimgurl")) {this.mainimgurl= entityValues.get("mainimgurl").toString();return;}
		if (name.equals("url")) {this.url= entityValues.get("url").toString();}
	}
	//(Date)
	public String getModel(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "model"); return model;}
	public Long getBaseprice(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "baseprice"); return baseprice;}
	public Integer getDiscount(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "discount"); return discount;}
	public String getTitle(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "title"); return title;}
	public String getText(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "text"); return text;}
	public String getName(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "name"); return name;}
	public String getDescription(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "description"); return description;}
	public Long getTagurl(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "tagurl"); return tagurl;}
	public String getKeywords(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "keywords"); return keywords;}
	public String getMainimgurl(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "mainimgurl"); return mainimgurl;}
	public String getUrl(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "url"); return url;}
	
	  public String getArticul() {
			return articul;
		}
		public String getModel() {
			return model;}
		public Long getBaseprice() {
			return baseprice;}
		public Integer getDiscount() {
			return discount;}
		public String getTitle() {
			return title;}
		public String getText() {
			return text;}
		public String getName() {
			return name;}
		public String getDescription() {
			return description;}
		public Long getTagurl() {
			return tagurl;}
		public String getKeywords() {
			return keywords;}
		public String getMainimgurl() {
			return mainimgurl;}
		public String getUrl() {
			return url;}
	
}
