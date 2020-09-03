package org.itstep.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.itstep.entities.User;
import org.itstep.service.LogicException;
import org.itstep.tabs.Role;

import org.itstep.web.action.ActionException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.itstep.web.action.Action.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.itstep.de_services.UserService;
@Component
@Scope("prototype")
public class LoginAction implements Action {
	@Autowired		
	private UserService userService;

/*	public void setUserService(UserService userService) {this.userService = userService;}*/

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		if(login == null && password == null) {
			req.setAttribute("preious_uri", req.getRequestURL());
		//	System.out.println("\\\\\\\\\\\\\\\\\\\\\\req.getParameter(\"preious_uri\")"+req.getParameter("preious_uri"));	
		//	System.out.println("login password"+"login" + " "+  "password");
			return null;			
		}
		String uri=null;		
		try {
			if(login == null || login.isBlank() || password == null) {
				throw new IllegalArgumentException();
			}
			User user = userService.authenticate(login, password);			
			if(user != null) {			
				HttpSession session = req.getSession();
				session.setAttribute("sessionUser", user);
                if (user.getRole()==Role.CLIENT) {
                	uri= req.getParameter("preious_uri");
            		if (uri!= null && !uri.isBlank()) {
            			uri=uri.substring(uri.indexOf("jsp")+3);
                		uri=uri.substring(0,uri.indexOf(".jsp"));	
            		}                	
                }	
				if (uri!=null && !uri.isBlank()) {return new Result(uri);}
				return new Result("/user");
			} else {
				return new Result("/login").add("message", "Wrong login or pass");
			}
		} catch(IllegalArgumentException e) {
			throw new ActionException(e, 400);
		}
	}
}