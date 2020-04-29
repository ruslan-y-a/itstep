package tabs;

import java.sql.ResultSet;

import postgres.DaoException;
//import sqlSetGet.SqlGetterDb;
import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlGetterS;
//import sqlSetGet.SqlSetterDb;
import sqlSetGet.SqlSetterL;
import sqlSetGet.SqlSetterS;

public class Country extends Entity {
	private String name;
	private Long currency;
	
	public Country() {
		super("country");
		entityValues.put("id", null);
		entityValues.put("name", null);
		entityValues.put("currency", null);		

		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("name", new SqlSetterS());
		tabSetter.put("currency", new SqlSetterL());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("name", new SqlGetterS());
		tabGetter.put("currency", new SqlGetterL());
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nname:"+ this.name + "\ncurrency:"+ this.currency; }		
				
	public String getName() {
		return name;}
	public Long getCurrency() {
		return currency;}
	public String getName(ResultSet r) throws DaoException   {
		this.getNameFromTab(r, "name"); return name;}
	public Long getCurrency(ResultSet r) throws DaoException   {
		this.getNameFromTab(r, "currency"); return currency;}
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));
		this.name=entityValues.get("name").toString();
		this.currency= (Long) entityValues.get("currency");
	}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("name")) {this.name=entityValues.get("name").toString();return;}
		if (name.equals("currency")) {this.currency= (Long) entityValues.get("currency");}
	}

}