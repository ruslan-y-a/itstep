package web;

//import java.io.File;
//import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.DBService;
import service.LogicException;
import tabs.Entity;
import tabs.Mapper;
import ui.Factory;

//@WebServlet("/list.html")
public class ListServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7050022628045967075L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(Factory factory = new Factory()) {
			DBService service = factory.getDBService();
			//int i1=req.getRequestURI().indexOf("WEB-INF/")+7;
			//int i2=req.getRequestURI().indexOf("/list.html");
			//String ss=req.getRequestURI().substring(i1, i2);
			String ss=new String(Mapper.getDBName(req.getRequestURI()));
			List<Entity> products = service.read(ss);						
			req.setAttribute("service", service);
			req.setAttribute("client", products);
			req.setAttribute("items", service.read("items"));
			req.setAttribute("dbcolor", service.read("color"));
			req.setAttribute("dbsize", service.read("size"));
			req.setAttribute("category", service.read("category"));
			req.setAttribute("classification", service.read("classification"));
			req.setAttribute("itemcatgory", service.read("itemcatgory"));
			
			String str; 
	    
				str= new String("/WEB-INF/jsp/"+ss+"/list.jsp");

		//	str= new String("/WEB-INF/jsp/listing/"+ ss+ ".jsp");}			
	//		File file = new File("c:/Users/Admin/eclipse-workspace/DBtest/web/"+str);
			//if (!file.exists()) {
			//	str = new String("/WEB-INF/jsp/listing/list.jsp");


			req.getRequestDispatcher(str).forward(req, resp);
		} catch(LogicException  e) {
			throw new ServletException(e);
		}
	}
}
