package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tabs.Role;
import tabs.User;

public class MainServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8495654684288453975L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession(false);
		if(session != null) {
			User user = (User)session.getAttribute("sessionUser");
			if(user != null) {
				switch(user.getRole()) {
				case ADMIN: resp.sendRedirect(req.getContextPath() + "/backoffice.html"); break;
				case CLIENT: resp.sendRedirect(req.getContextPath() + "/index.html"); break;
				case PRODUCT: resp.sendRedirect(req.getContextPath() + "/backoffice.html"); break;
				case MANAGER: resp.sendRedirect(req.getContextPath() + "/backoffice.html"); break;
				case CASHIER: resp.sendRedirect(req.getContextPath() + "/backoffice.html"); break;
				case COURIER: resp.sendRedirect(req.getContextPath() + "/backoffice.html"); break;
				default: throw new EnumConstantNotPresentException(Role.class, user.getRole().toString());
				}
				return;
			}
		}
		resp.sendRedirect(req.getContextPath() + "/login.html");
	}
}
