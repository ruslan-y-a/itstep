package entities;

public class Img extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9087723422328289017L;
	private String title;
	private String alt;
	private String url;
	
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAlt() {
		return alt;
	}


	public void setAlt(String alt) {
		this.alt = alt;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\ntitle:"+ this.title 
				 + "\nalt:"+ this.alt+ "\nurl:"+ this.url; }					
	
	

}