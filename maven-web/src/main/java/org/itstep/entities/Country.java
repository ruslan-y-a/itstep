package org.itstep.entities;


public class Country extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7180491719122931601L;
	private String name;
	private Currency currency;
	
	
	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\nname:"+ this.name + "\ncurrency:"+ this.currency; }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Currency getCurrency() {
		return currency;
	}


	public void setCurrency(Currency currency) {
		this.currency = currency;
	}		
				
	

}