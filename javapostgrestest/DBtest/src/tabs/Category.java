package tabs;

import java.sql.ResultSet;

import postgres.DaoException;
import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlGetterS;
import sqlSetGet.SqlSetterL;
import sqlSetGet.SqlSetterS;

public class Category  extends Entity {
	private Long parentid;
	private String name;
	
	public Category() {
		super("category");
		entityValues.put("id", null);
		entityValues.put("parentid", null);
		entityValues.put("name", null);		

		tabSetter.put("id", new SqlSetterL());
		tabSetter.put("parentid", new SqlSetterL());
		tabSetter.put("name", new SqlSetterS());
		
	    tabGetter.put("id", new SqlGetterL());
		tabGetter.put("parentid", new SqlGetterL());
		tabGetter.put("name", new SqlGetterS());
	}
	
	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nparentid:"+ this.parentid + "\nname:"+ this.name;  
	}		
			
	public Long getParentid() {
		return parentid;}
	public String getName() {
		return name;}
	public Long getParentid(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "parentid"); return parentid;}
	public String getName(ResultSet r) throws DaoException {
		this.getNameFromTab(r, "name"); return name;}
	
	@Override
	public void cast() {
	  try {	
		this.DBsetId((Long) entityValues.get("id"));
		this.parentid=(Long) entityValues.get("parentid");
		this.name= entityValues.get("name").toString();
	  } catch(NullPointerException e) {
		  
	  }
	}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("parentid")) {this.parentid=(Long) entityValues.get("parentid");return;}
		if (name.equals("name")) {this.name= entityValues.get("name").toString();}
	}

}
