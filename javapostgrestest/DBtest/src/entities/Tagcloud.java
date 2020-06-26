package entities;

import java.util.List;

import help.Helper;

public class Tagcloud extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2014558826175709177L;
	private List<Classification> classification;
	private Webpages webpages;
	
	public List<Classification> getClassification() {
		return classification;
	}
	public void setClassification(List<Classification> classification) {
		this.classification = classification;
	}
	public Webpages getWebpages() {
		return webpages;
	}
	public void setWebpages(Webpages webpages) {
		this.webpages = webpages;
	}



	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\nwebpages" + webpages + "\nclassification:"+ Helper.objToLongArray(classification); }		
			
	

}