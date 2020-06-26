package tabs;

import java.sql.ResultSet;

import postgres.DaoException;
import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlSetterL;

public class Itemtagurl  extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5468725207716525861L;
	public Long items;
	public Long itemtagurl;
	
	public Itemtagurl() {
		super("itemtagurl");
		entityValues.put("id", null);
		entityValues.put("items", null);
		entityValues.put("itemtagurl", null);	
	
		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("items", new SqlSetterL());
		tabSetter.put("itemtagurl", new SqlSetterL());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("items", new SqlGetterL());
		tabGetter.put("itemtagurl", new SqlGetterL());
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nitems:"+ this.items
				 + "\nitemtagurl:"+ this.itemtagurl; }					
	    	

	public Long getItems() {
		return items;}

	public Long getItemtagurl() {
		return itemtagurl;}

	public Long getItems(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "items"); return items;}
	public Long getItemtagurl(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "itemtagurl"); return itemtagurl;}
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));
		this.items= (Long) entityValues.get("items");
		this.itemtagurl=(Long) entityValues.get("itemtagurl");		
	}

	@Override
	public void cast(String name) {		
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id")); return;}
		if (name.equals("items")) {this.items= (Long) entityValues.get("items"); return;}
		if (name.equals("itemtagurl")) {this.itemtagurl=(Long) entityValues.get("itemtagurl"); return;}
	}

}