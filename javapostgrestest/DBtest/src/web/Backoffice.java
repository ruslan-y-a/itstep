package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Backoffice extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7722179853995256455L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/jsp/main/backoffice.jsp").forward(req, resp);
		
	}
}
