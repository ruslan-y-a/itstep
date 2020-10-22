package org.itstep.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "category", schema="public")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	
	private String name;
	private Long parentid;
	private String parentname;
	@ManyToOne()
	@JoinColumn(name="webpages")
	private Webpages webpages;

	public Webpages getWebpages() {
		return webpages;
	}
	public void setWebpages(Webpages webpages) {
		this.webpages = webpages;
	}
	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

	public Long getParentid() {
		return parentid;
	}

	public void setParentid(Long parentid) {
		this.parentid = parentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "[" + getId() + "] " + name + "\"parentid\"" +parentid;
	}
}
