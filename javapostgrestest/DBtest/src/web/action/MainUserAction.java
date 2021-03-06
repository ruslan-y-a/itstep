package web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;
import service.LogicException;
import tabs.Role;

public class MainUserAction implements Action {
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {	
		HttpSession session = req.getSession(false);
		if(session != null) {
			User user = (User)session.getAttribute("sessionUser");
		//	System.out.println("MAIN_USER_ACTION:"+ user);
			if(user != null) {
				switch(user.getRole()) {
				case ADMIN: return new Result("/backoffice"); 
				case CLIENT: return new Result("/index");
				case PRODUCT: return new Result("/backoffice"); 
				case MANAGER: return new Result("/backoffice"); 
				case CASHIER: return new Result("/backoffice"); 
				case COURIER: return new Result("/backoffice"); 
				default: throw new EnumConstantNotPresentException(Role.class, user.getRole().toString());
				}			
			}
		}
		return new Result("/login");				
	}		
	
}
