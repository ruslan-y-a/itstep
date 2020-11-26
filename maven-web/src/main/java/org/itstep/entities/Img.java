package org.itstep.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "img", schema="public")
public class Img {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
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