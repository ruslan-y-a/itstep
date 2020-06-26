package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DBService;
import service.LogicException;
import tabs.Entity;
import tabs.Mapper;
import ui.Factory;

public class EditServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7133050297076759461L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(Factory factory = new Factory()) {
		 DBService service = factory.getDBService();	
		 req.setAttribute("service", service);
		 req.setAttribute("items", service.read("items"));
			req.setAttribute("dbcolor", service.read("color"));
			req.setAttribute("dbsize", service.read("size"));
			req.setAttribute("category", service.read("category"));
			req.setAttribute("classification", service.read("classification"));
			req.setAttribute("itemcatgory", service.read("itemcatgory"));
			
			
		 String id = req.getParameter("id");
	//	 req.setAttribute("uri", req.getContextPath());
		 if(id != null && !id.isBlank()) { 
			
		  	Entity entity = service.read(Mapper.getDBName(req.getRequestURI()), Long.parseLong(id)); 
		  	if(entity == null) {
				throw new IllegalArgumentException();}
		  //	entity.cast();
		  	req.setAttribute("entity", entity);
		 }
	
		 req.getRequestDispatcher(Mapper.addServlet(req.getRequestURI())).forward(req, resp);	
		} catch(LogicException e) {
			throw new ServletException(e);
		} catch(IllegalArgumentException e) {
			resp.sendError(404);
		}
	}
}

