package org.itstep.web.action;

import org.itstep.de_services.BaseService;

public abstract class BaseAction implements Action {
	private BaseService<?> baseService;
	public String url;
	
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	protected BaseService<?> getService() {return baseService;}
	public void setService(BaseService<?> baseService) {this.baseService = baseService;}
	
	
	
}