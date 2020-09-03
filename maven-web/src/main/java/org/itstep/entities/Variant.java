package org.itstep.entities;

public class Variant  extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -119350861183686149L;
	private String name;
	private String dbname;
	
	public Variant(String dbname) {		
		this.dbname = dbname;
	}
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\nname:"+  this.name; 	}			 
	
    public static Variant getVariant(String name) {
    	if (name.equals("size")) {return new Size();}
    	if (name.equals("color")) {return new Color();}
    	return null;
    } 
	
	
}