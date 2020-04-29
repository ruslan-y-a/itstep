package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DBService;
import service.LogicException;
import tabs.Entity;
import ui.Factory;
@WebServlet("/list.html")
public class ListServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7050022628045967075L;

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
			int i1=req.getRequestURI().indexOf("WEB-INF/")+7;
			int i2=req.getRequestURI().indexOf("/list.html");
			String ss=req.getRequestURI().substring(i1, i2);
			List<Entity> products = service.read(ss);					
			req.setAttribute("client", products);
			req.getRequestDispatcher("/WEB-INF/view/list.html").forward(req, resp);
		} catch(LogicException | ClassNotFoundException e) {
			throw new ServletException(e);
		}
	}
}
