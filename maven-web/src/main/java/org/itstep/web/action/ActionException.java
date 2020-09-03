package org.itstep.web.action;

import org.itstep.service.LogicException;

public class ActionException extends LogicException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7242214954884932083L;
	private int code;

	public ActionException(Throwable cause, int code) {
		super(cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
