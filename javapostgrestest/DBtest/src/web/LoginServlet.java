package web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ui.Factory;
import tabs.User;
import service.LogicException;
import service.UserService;

public class LoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5911808970620921775L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String login = req.getParameter("login");
			String password = req.getParameter("password");
			if(login == null || login.isBlank() || password == null) {
				throw new IllegalArgumentException();
			}
			try(Factory factory = new Factory()) {
				UserService service = factory.getUserService();
				User user = service.authenticate(login, password);
				if(user != null) {
					HttpSession session = req.getSession();
					session.setAttribute("sessionUser", user);
					resp.sendRedirect(req.getContextPath() + "/user.html");
				} else {
					resp.sendRedirect(req.getContextPath() + "/login.html?message=" + URLEncoder.encode("Wrong login or password", "UTF-8"));
				}
			} catch(LogicException e) {
				throw new ServletException(e);
			}
		} catch(IllegalArgumentException e) {
			resp.sendError(400);
		}
	}
}
