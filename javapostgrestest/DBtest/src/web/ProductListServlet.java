package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ui.Factory;
import service.DBService;
import service.LogicException;
import tabs.Entity;


public class ProductListServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5862689299528234078L;

	@Override
	public void init() throws ServletException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch(ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(Factory factory = new Factory()) {
			DBService service = factory.getDBService();
			List<Entity> products = service.read("client");					
			req.setAttribute("client", products);
			req.getRequestDispatcher("/WEB-INF/client/list.html").forward(req, resp);
		} catch(LogicException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}
}
