package org.itstep.entities;

public class Category extends Entity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3449097732629054565L;
	private String name;
	private Long parentid;
	private String parentname;
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
