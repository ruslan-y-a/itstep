package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import postgres.DaoException;
import service.DBService;
import service.LogicException;
import tabs.Classification;
import tabs.Entity;
import tabs.Itemcatgory;
import ui.Factory;

public class SaveParamsServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -526004724002510847L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		Itemcatgory itemcatgory;
		Classification classification;		
		Long itclassif;
		Long itemid = Long.parseLong(req.getParameter("itemid"));
		try(Factory factory = new Factory()) {		 
		   if (itemid==null || itemid==0) {throw new IllegalArgumentException();}
 		   DBService service = factory.getDBService();
 		  service.delete("itemcatgory", "items", itemid);
 		   List<Entity> list=service.read("classification");		
 		   for (Entity entity: list) {
 			  classification=(Classification)entity;
 			  try {itclassif = Long.parseLong(req.getParameter(classification.getName()));}
 			  catch (NullPointerException | NumberFormatException e) {itclassif = null;}
 			  if (itclassif!=null) { 				 
 				itemcatgory= new Itemcatgory();
 				itemcatgory.put("classification", itclassif);
 				itemcatgory.put("items", itemid);
 				service.save(itemcatgory);
 			  } 
 		   }
 		   
 		  resp.sendRedirect("list.html"); 
		} catch (LogicException | IllegalArgumentException | DaoException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		
		
	}	
	
}
