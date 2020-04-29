package tabs;

import java.sql.ResultSet;

import postgres.DaoException;
import sqlSetGet.SqlGetterL;
//import sqlSetGet.SqlGetterO;
//import sqlSetGet.SqlSetterArr;
import sqlSetGet.SqlSetterL;

public class Itemcatgory  extends Entity {
	public Long items;
	public Long classification;
	
	public Itemcatgory() {
		super("itemcatgory");
		entityValues.put("id", null);
		entityValues.put("items", null);
		entityValues.put("classification", null);	
	
		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("items", new SqlSetterL());
		tabSetter.put("classification", new SqlSetterL());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("items", new SqlGetterL());
		tabGetter.put("classification", new SqlGetterL());
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nitems:"+ this.items
				 + "\nclassification:"+ this.classification; }					
	    	
	public Long getItems() {
		return items;}
	public Long getClassification() {
		return classification;}
	public Long getItems(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "items"); return items;}
	public Long getClassification(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "classification"); return classification;}
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));
		this.items= (Long) entityValues.get("items");
		this.classification=(Long) entityValues.get("classification");		
	}

	@Override
	public void cast(String name) {		
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id")); return;}
		if (name.equals("items")) {this.items= (Long) entityValues.get("items"); return;}
		if (name.equals("classification")) {this.classification=(Long) entityValues.get("classification"); return;}
	}

}