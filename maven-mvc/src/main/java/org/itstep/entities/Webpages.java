package org.itstep.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "webpages", schema="public")
public class Webpages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	private static final String[] ROBOTSTYPE ={"index,follow","noindex,nofollow","noindex,follow"};
	
	private String url;
	private String title;
	private String description;
	private String keywords;
	private String h1;
	private String text;
	private String robots=ROBOTSTYPE[0];
	private String entity;
	private Long entityid;
			
	public Long getEntityid() {return entityid;}
	public void setEntityid(Long entityid) {this.entityid = entityid;}
	public String getEntity() {return entity;}
	public void setEntity(String entity) {this.entity = entity;}

	@Override
	  public String toString() {		  
		 return "id:" + this.getId() + "\nurl:"+ this.url + "\ntitle:"+ this.title
				 + "\ndescription:"+ this.description + "\nkeywords:"+ this.keywords 
				 + "\nh1:"+ this.h1 + "\ntext:"+ this.text				 
				 + "\nrobots:"+ this.robots  + "\nentity:"+ this.entity +  "\nentityid:"+ this.entityid;}

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
