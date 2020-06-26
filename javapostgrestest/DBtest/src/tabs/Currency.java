package tabs;

import java.sql.ResultSet;

import postgres.DaoException;
import sqlSetGet.SqlGetterDb;
import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlGetterS;
import sqlSetGet.SqlSetterDb;
import sqlSetGet.SqlSetterL;
import sqlSetGet.SqlSetterS;

public class Currency extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8060947453049770818L;
	private String name;
	private Double rate;
	
	public Currency() {
		super("currency");
		entityValues.put("id", null);
		entityValues.put("name", null);
		entityValues.put("rate", null);		

		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("name", new SqlSetterS());
		tabSetter.put("rate", new SqlSetterDb());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("name", new SqlGetterS());
		tabGetter.put("rate", new SqlGetterDb());
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nname:"+ this.name + "\nrate:"+ this.rate; }		
			
	public String getName() {
		return name;}
	public Double getRate() {
		return rate;}
	
	public String getName(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "name"); return name;}
	public Double getRate(ResultSet r) throws DaoException  {
		this.getNameFromTab(r, "rate"); return rate;}
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id"));
		this.name=entityValues.get("name").toString();
		this.rate= (Double) entityValues.get("rate");
	}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("name")) {this.name=entityValues.get("name").toString();return;}
		if (name.equals("rate")) {this.rate= (Double) entityValues.get("rate");}
	}

}