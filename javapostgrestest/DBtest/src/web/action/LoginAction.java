package web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;
import service.LogicException;
import tabs.Role;
import de_services.UserService;

public class LoginAction implements Action {
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		if(login == null && password == null) {
			req.setAttribute("preious_uri", req.getRequestURL());
			//System.out.println("\\\\\\\\\\\\\\\\\\\\\\req.getParameter(\"preious_uri\")"+req.getParameter("preious_uri"));			
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
