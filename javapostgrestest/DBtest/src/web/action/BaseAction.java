package web.action;

import de_services.BaseService;

public abstract class BaseAction implements Action {
	private BaseService<?> baseService;

	protected BaseService<?> getService() {
		return baseService;
	}

	public void setService(BaseService<?> baseService) {
		this.baseService = baseService;
	}
}