package web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.LogicException;

public class MainAction implements Action {
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {			
		return new Result("/index");				
	}		
	
}
