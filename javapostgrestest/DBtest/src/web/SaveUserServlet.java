package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import postgres.DaoException;
import service.DBService;
import service.LogicException;
import tabs.User;
import ui.Factory;

public class SaveUserServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2717945169636529958L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try {
    	   
    	   String sid; 
    	   try {sid = new String (req.getParameter("id"));}
    	   catch (NullPointerException e){sid ="";}
    	   
   		   Long id = null;
    	   try {id = Long.parseLong(sid);}
  		   catch (IllegalArgumentException err) {}
    	   User user = new User();    	  
    	 	     	  
    	   String name=req.getParameter("name"); user.put("name", name);
    	   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}
    	   
    	   String password=req.getParameter("password"); user.put("password", password);
    	   if(password == null || password.isBlank()) {throw new IllegalArgumentException();}
    	   
    	   String email=req.getParameter("email"); user.put("email", email);
    	   if(email == null || email.isBlank()) {throw new IllegalArgumentException();}
    	   
    	   Integer roleid= Integer.parseInt(req.getParameter("roleid")); user.put("roleid", roleid);
    	   if(roleid == null || roleid<0) {roleid=1; }; 
    	     
    	   user.setDBName("users");
    	   user.DBsetId(id); 
    	   user.put("id", id); 
    	   
    	  try(Factory factory = new Factory()) {
    		   DBService service = factory.getDBService();    		   
			   service.save(user);
			   resp.sendRedirect("list.html"); 
				
   	      } catch(LogicException e) {
			 throw new ServletException(e);
		  }
    	   
       } catch(NumberFormatException | DaoException e) {
		  throw new ServletException(e);
	   }

    }
}	 