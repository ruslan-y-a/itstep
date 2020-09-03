package org.itstep.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.service.LogicException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class MainAction implements Action {
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {			
		return new Result("/index");				
	}		
	
}
