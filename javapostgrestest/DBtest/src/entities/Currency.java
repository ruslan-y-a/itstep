package entities;


public class Currency extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8060947453049770818L;
	private String name;
	private Double rate;
	
	
	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\nname:"+ this.name + "\nrate:"+ this.rate; }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Double getRate() {
		return rate;
	}


	public void setRate(Double rate) {
		this.rate = rate;
	}		
			
	

}