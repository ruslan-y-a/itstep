package entities;

public class Webpages extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6211220639783766885L;

	private static final String[] ROBOTSTYPE ={"index,follow","noindex,nofollow","noindex,follow"};
	
	private String url;
	private String title;
	private String description;
	private String keywords;
	private String h1;
	private String text;
	private String robots=ROBOTSTYPE[0];
			
	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\nurl:"+ this.url + "\ntitle:"+ this.title
				 + "\ndescription:"+ this.description + "\nkeywords:"+ this.keywords 
				 + "\nh1:"+ this.h1 + "\ntext:"+ this.text				 
				 + "\nrobots:"+ this.robots ; }

	public void setIndex() {
		this.robots=ROBOTSTYPE[0];
	}
	public void setNoindex() {
		this.robots=ROBOTSTYPE[1];
	}	
	public void setFollow() {
		this.robots=ROBOTSTYPE[2];
	}	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getH1() {
		return h1;
	}
	public void setH1(String h1) {
		this.h1 = h1;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getRobots() {
		return robots;
	}
	public void setRobots(Integer robots) {
		this.robots = ROBOTSTYPE[robots];
	}
	public void setRobots(String robots) {	
		this.robots = robots;
	}
}
