package tabs;

import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlGetterS;
import sqlSetGet.SqlSetterL;
import sqlSetGet.SqlSetterS;

public class Variant  extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7645285328832551175L;
	private String name;
	
	public String getName() {
		return name;}

	public Variant(String str) {	  		  
	    super(str);
	    entityValues.put("id", null);
	    entityValues.put("name", null);    
	    tabSetter.put("id", new SqlSetterL());
	    tabSetter.put("name", new SqlSetterS());
	    tabGetter.put("id", new SqlGetterL());
	    tabGetter.put("name", new SqlGetterS());
	}

	@Override
	  public String toString() {		  
		 return "id:" + this.DBgetId() + "\nname:"+  this.name; 
	}			 
	
	@Override
	public void cast() {
		this.DBsetId((Long) entityValues.get("id")); 
		this.name= entityValues.get("name").toString();}

	@Override
	public void cast(String name) {
		if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;} 
		if (name.equals("name")) {this.name= entityValues.get("name").toString();}
	}   
	
	
}