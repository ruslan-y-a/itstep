package org.itstep.entities;

import java.io.Serializable;

abstract public class Entity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6805448938352423116L;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
