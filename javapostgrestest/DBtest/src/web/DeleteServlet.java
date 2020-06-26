package web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ui.Factory;
import service.LogicException;
import tabs.Mapper;
import service.DBService;

public class DeleteServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7647053104722889872L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String idsString[] = req.getParameterValues("id");
		if(idsString != null) {
			try {
				List<Long> ids = new ArrayList<>(idsString.length);
				for(String id : idsString) {
					ids.add(Long.parseLong(id));}
				
				try(Factory factory = new Factory()) {					
					DBService service = factory.getDBService();
					service.delete(Mapper.getDBName(req.getRequestURI()),ids);
				} catch(LogicException e) {
					throw new ServletException(e);
				}
			} catch(NumberFormatException e) {
				resp.sendError(400);
				return;
			}
		}
		resp.sendRedirect("list.html"); /* req.getContextPath() + */
	}
}