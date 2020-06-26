package web;

import java.io.IOException;
import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ui.Factory;
import service.LogicException;
//import tabs.Entity;
import tabs.Mapper;
import service.DBService;

public class SaveServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2699958908575372461L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
			 
		String str= new String(req.getRequestURI());	
		String dbName = new String(Mapper.getDBName(str));		
		String sid = new String (req.getParameter("id"));
		Long id = null;
		 try {id = Long.parseLong(sid);}
		 catch (IllegalArgumentException err) {}
		// System.out.println("ID !!!!!!!!!!!!!!!!!!!! -  " + id);
		ArrayList<String> fields = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
				
	    for (String field:Mapper.getEntityFieldsforUri(req.getRequestURI())) {
	    	fields.add(field); values.add(req.getParameter(field));}	    	    
	    
		try(Factory factory = new Factory()) {
			DBService service = factory.getDBService();						  
			//  System.out.println("ID !!!!!!!!!!!!!!!!!!!! -  " + id);
			  service.save(dbName,id, fields,values);			
			req.getRequestDispatcher("list.html").forward(req, resp);
		} catch(LogicException  | IOException e) {
			throw new ServletException(e);
		}
	}	
		
}

/*
   Map<String,String> map = new HashMap<String,String>();
	    for (String field:Mapper.getEntityFieldsforUri(req.getRequestURI())) {
	    	map.put(field, req.getParameter(field));}	    	    
	    
		try(Factory factory = new Factory()) {
			DBService service = factory.getDBService();	
			service.create(Mapper.getDBName(str), map);
			//req.getRequestDispatcher("/" + str + "/list.html").forward(req, resp);
			req.getRequestDispatcher("list.html").forward(req, resp);
		} catch(LogicException  | IOException e) {
			throw new ServletException(e);
		}
 */ 
