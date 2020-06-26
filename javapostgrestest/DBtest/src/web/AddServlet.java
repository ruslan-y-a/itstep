package web;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DBService;
import service.LogicException;
import tabs.Mapper;
import ui.Factory;



public class AddServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7133050297076759460L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(Factory factory = new Factory()) {
		 DBService service = factory.getDBService();	
		 req.setAttribute("service", service);
		 req.getRequestDispatcher(Mapper.addServlet(req.getRequestURI())).forward(req, resp);	
		} catch(LogicException e) {
			throw new ServletException(e);
		}
	}
}
