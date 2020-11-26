package org.itstep.entities;

import java.util.List;


import org.itstep.help.Helper;

public class Tagcloud {
	
	private Long id;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
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