package web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.User;
import service.LogicException;
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
			return null;
		}
		try {
			if(login == null || login.isBlank() || password == null) {
				throw new IllegalArgumentException();
			}
			User user = userService.authenticate(login, password);			
			if(user != null) {
			//	System.out.println("LOGIN_ACTION:"+ user);
				HttpSession session = req.getSession();
				session.setAttribute("sessionUser", user);
				return new Result("/user");
			} else {
				return new Result("/login").add("message", "Wrong login or pass");
			}
		} catch(IllegalArgumentException e) {
			throw new ActionException(e, 400);
		}
	}
}
