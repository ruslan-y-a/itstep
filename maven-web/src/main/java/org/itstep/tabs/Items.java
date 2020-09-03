package org.itstep.tabs;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.itstep.help.Helper;
import org.itstep.postgres.DaoException;
import org.itstep.sqlSetGet.SqlGetterB;
import org.itstep.sqlSetGet.SqlGetterI;
import org.itstep.sqlSetGet.SqlGetterL;
import org.itstep.sqlSetGet.SqlGetterO;
import org.itstep.sqlSetGet.SqlGetterS;
import org.itstep.sqlSetGet.SqlSetterArr;
import org.itstep.sqlSetGet.SqlSetterB;
import org.itstep.sqlSetGet.SqlSetterI;
import org.itstep.sqlSetGet.SqlSetterL;
import org.itstep.sqlSetGet.SqlSetterO;
import org.itstep.sqlSetGet.SqlSetterS;

public class Items extends Entity {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 6404924983245486686L;
	   private String articul;
	   private String model;
	   private Category category;
	   private Long baseprice;
	   private Integer discount;	
	   private String name;   
	   private List<Classification> classification;
	   private List<Img> img;
	   private boolean active;
	   private Webpages webpages;
	
	 public Items() {	  		  
		    super("items");
		    entityValues.put("id", null);
		    entityValues.put("articul", null);
		    entityValues.put("model", null);
		    entityValues.put("category", null);
		    entityValues.put("baseprice", null);
		    entityValues.put("discount", null);			
		    entityValues.put("name", null);
		    entityValues.put("classification", null);
		    entityValues.put("img", null);
		    entityValues.put("active", null);
		    entityValues.put("webpages", null);		    
		    
		    tabSetter.put("id", new SqlSetterL());
		    tabSetter.put("articul", new SqlSetterS());
		    tabSetter.put("model", new SqlSetterS());
		    tabSetter.put("category", new SqlSetterL());
		    tabSetter.put("baseprice", new SqlSetterL());
		    tabSetter.put("discount", new SqlSetterI());		    
		    tabSetter.put("name", new SqlSetterS());
		    tabSetter.put("classification", new SqlSetterArr());
		    tabSetter.put("img", new SqlSetterArr());
		    tabSetter.put("active", new SqlSetterB());
		    tabSetter.put("webpages", new SqlSetterL());		    
		    
		    tabGetter.put("id", new SqlGetterL());
		    tabGetter.put("articul", new SqlGetterS());
		    tabGetter.put("model", new SqlGetterS());
		    tabGetter.put("category", new SqlGetterL());
		    tabGetter.put("baseprice", new SqlGetterL());
		    tabGetter.put("discount", new SqlGetterI());  		   
		    tabGetter.put("name", new SqlGetterS());
		    tabGetter.put("classification", new SqlGetterO());  
		    tabGetter.put("img", new SqlGetterO());  
		    tabGetter.put("active", new SqlGetterB());
		    tabGetter.put("webpages", new SqlGetterL());		    
		    
		  }
		  @Override
		  public String toString() {		  
			 return "id:" + this.DBgetId() + "\narticul:"+  this.articul + "\nmodel:" + this.model + 
			 "\ncategory:" + this.category+ "\nbaseprice:"+this.baseprice +"\ndiscount:" + this.discount +
			  "\nname:" + this.name +"\nclassification:"  + this.classification + this.name +"\nimg:"  + this.img + "\nactive:"  + this.active  + "\nwebpages:"  + this.webpages;
		  }
			
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id")); 
		this.articul= entityValues.get("articul").toString();
		this.model= entityValues.get("model").toString();
		Category category = new Category(); category.DBsetId((Long) entityValues.get("category")); this.category = category;
		this.baseprice=(Long) entityValues.get("baseprice");
		this.discount=(Integer) entityValues.get("discount");	
		this.name= entityValues.get("name").toString();	
		this.active= (Boolean)entityValues.get("active");		
		Webpages webpages = new Webpages(); webpages.DBsetId((Long) entityValues.get("webpages")); this.webpages = webpages;
		
		ArrayList<Long> iList= Helper.objToLongArrayList(entityValues.get("classification"));	
	    ArrayList<Classification> Litems= new ArrayList<>();					
		iList.forEach((x) -> {
			Classification cl = new Classification();
			cl.DBsetId(x);
			Litems.add(cl);
		  });
		this.classification=Litems;
		
		iList= Helper.objToLongArrayList(entityValues.get("img"));	
	    ArrayList<Img> Litems2= new ArrayList<>();					
		iList.forEach((x) -> {
			Img cl = new Img();
			cl.DBsetId(x);
			Litems2.add(cl);
		  });
		this.img=Litems2;
	}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("articul")) {this.articul= entityValues.get("articul").toString();return;}
		if (name.equals("model")) {this.model= entityValues.get("model").toString();return;}
		if (name.equals("category")) {
			Category category = new Category(); category.DBsetId((Long) entityValues.get("category")); this.category = category; return;}
		if (name.equals("baseprice")) {this.baseprice=(Long) entityValues.get("baseprice");return;}
		if (name.equals("discount")) {this.discount=(Integer) entityValues.get("discount");return;}	
		if (name.equals("name")) {this.name= entityValues.get("name").toString();return;}			
		if (name.equals("active")) {this.active= (Boolean)entityValues.get("active"); return;}
		if (name.equals("webpages")) {
			Webpages webpages = new Webpages(); webpages.DBsetId((Long) entityValues.get("webpages")); this.webpages = webpages; return;}
		if (name.equals("classification")) {
			ArrayList<Long> iList= Helper.objToLongArrayList(entityValues.get("classification"));	
		    ArrayList<Classification> Litems= new ArrayList<>();					
			iList.forEach((x) -> {
				Classification cl = new Classification();
				cl.DBsetId(x);
				Litems.add(cl);
			  });
			this.classification=Litems; 
			
			return;}		
		if (name.equals("img")) {

			ArrayList<Long> iList = Helper.objToLongArrayList(entityValues.get("img"));	
		    ArrayList<Img> Litems2= new ArrayList<>();					
			iList.forEach((x) -> {
				Img cl = new Img();
				cl.DBsetId(x);
				Litems2.add(cl);
			  });
			this.img=Litems2;
			
			return;}		
		
		
	}
	//(Date)
	public String getModel(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "model"); return model;}
	public Category getCategory(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "category"); return category;}
	public Long getBaseprice(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "baseprice"); return baseprice;}
	public Integer getDiscount(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "discount"); return discount;}
	public String getName(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "name"); return name;}
	public List<Classification> getClassification(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "classification"); return classification;}
	public List<Img> getImg(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "img"); return img;}
	public Boolean getActive(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "active"); return active;}
	public Webpages getWebpages(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "webpages"); return webpages;}	
	
	  public String getArticul() {
			return articul;}
		public String getModel() {
			return model;}
		public Category getCategory() {
			return category;}
		public Long getBaseprice() {
			return baseprice;}
		public Integer getDiscount() {
			return discount;}		
		public String getName() {
			return name;}
		public List<Classification> getClassification() {
			return classification;}
		public List<Img> getImg() {
			return img;}		
		public Boolean getActive() {
			return active;}
		public Webpages getWebpages() {
			return webpages;}		
	
}
