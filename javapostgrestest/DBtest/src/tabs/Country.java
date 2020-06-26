package tabs;

import java.sql.ResultSet;

import postgres.DaoException;
import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlGetterS;
import sqlSetGet.SqlSetterL;
import sqlSetGet.SqlSetterS;

public class Country extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7180491719122931601L;
	private String name;
	private Currency currency;
	
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
	public Currency getCurrency() {
		return currency;}
	public String getName(ResultSet r) throws DaoException   {
		this.getNameFromTab(r, "name"); return name;}
	public Currency getCurrency(ResultSet r) throws DaoException   {
		this.getNameFromTab(r, "currency"); return currency;}
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));
		this.name=entityValues.get("name").toString();		
		Currency currency = new Currency(); currency.DBsetId((Long) entityValues.get("currency")); this.currency = currency;
	}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("name")) {this.name=entityValues.get("name").toString();return;}
		if (name.equals("currency")) {Currency currency = new Currency(); currency.DBsetId((Long) entityValues.get("currency")); this.currency = currency;}
	}

}