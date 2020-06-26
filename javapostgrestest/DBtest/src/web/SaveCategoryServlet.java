package web;

import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import help.Helper;
import postgres.DaoException;
import service.DBService;
import service.LogicException;
import tabs.Category;
//import tabs.Entity;
//import tabs.Mapper;
//import tabs.User;
import ui.Factory;

public class SaveCategoryServlet  extends HttpServlet {
	
	private Long categorySave(Category category) throws ServletException {
		try(Factory factory = new Factory()) {
			 category.setDBName("category");
			 DBService service = factory.getDBService();    		   
			 return service.save(category);			   				
	      } catch(LogicException e) {
			 throw new ServletException(e);
		  }   
	}
	private void categoryUpdate(Category category) throws ServletException {
		try(Factory factory = new Factory()) {
			 category.setDBName("category");
			 DBService service = factory.getDBService();    		   
			 service.update(category);			   				
	      } catch(LogicException e) {
			 throw new ServletException(e);
		  }   
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 2723945169636529958L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try {
    	       	   
    	   Category category = new Category(); //Mapper.getEntity("category");
    	   String name=null;  try {name=new String (req.getParameter("name"));} catch (NullPointerException e) {}
    	   String sparentid=null;  try {sparentid=new String (req.getParameter("parentid"));} catch (NullPointerException e) {}    	   
    	   String parentname=null;  try {parentname=new String (req.getParameter("parentname"));} catch (NullPointerException e) {}    	   
    	   String sid=null;  try {sid=new String (req.getParameter("id")).trim();} catch (NullPointerException e) {}     	   
    	   String categories =null;  try {categories=new String (req.getParameter("categoriesid")).trim();} catch (NullPointerException e) {}
    	   if (categories!= null && categories.length()<=3) {categories=null;}
    	   
    	   if ((name == null || name.isBlank()) && (parentname == null || parentname.isBlank())  && (sparentid == null || sparentid.isBlank())) {
    		   throw new IllegalArgumentException();}
    	   if ((sparentid == null || sparentid.isBlank()) && (parentname == null || parentname.isBlank())) {throw new IllegalArgumentException();}
    	   
    	   Long parentid=null;
    	   Long id=null;    	       	       	 
    	   
    	   if (parentname != null && !parentname.isBlank()) {
    		   category = new Category();
    		   category.put("name",parentname);
    		   
    		   try {parentid = Long.parseLong(sparentid);}
      		   catch (NumberFormatException err) {}  
    		   
    		   if (parentid!=null && parentid!=0) {category.put("id",parentid); category.DBsetId(parentid); }
    		   parentid= categorySave(category);
    		   
    		   if (parentname.equals(name)) {    			  
    			   category.put("id",parentid);
    			   category.put("parentid",parentid);
    			   category.put("name",parentname);
    			   category.DBsetId(parentid); 
    			   categoryUpdate(category);
    			   //resp.sendRedirect("list.html");
    			   name=null;
    		   }
    		   
    		   if (parentname.equals(categories)) {    			  
    			   category.put("id",parentid);
    			   category.put("parentid",parentid);
    			   category.put("name",parentname);
    			   category.DBsetId(parentid); 
    			   categoryUpdate(category);
    			   //resp.sendRedirect("list.html");
    			   categories=null;
    		   }
    		   
    	   } else {    		        		 
        		   try {parentid = Long.parseLong(sparentid);}
          		   catch (NumberFormatException err) {throw new IllegalArgumentException();}           	   
    	   }       	       	       	 
    	       	    	       	   
   // 	   System.out.println("categories :" + categories);
    //	   System.out.println("sid :" + sid);
    	   
    	   if (categories == null && sid!=null) {
    		  
        	   try {id = Long.parseLong(sid);}
      		   catch (NumberFormatException err) {throw new IllegalArgumentException();}   
    		   category = new Category();
    		   category.DBsetId(id); 
        	   category.put("id",id);
        	   category.put("name",name);   	 
        	   category.put("parentid",parentid); 	  
        	   categorySave(category);   
        	 
    	   } 
    	   
    	  if (categories != null && sid==null) {
    		  
    	   String [] cats; 
    		 try {
    			 cats = categories.split(Helper.findDelimiter(categories));
    		 } catch (PatternSyntaxException e) {cats = new String [0];cats[0]=categories;}
    		 
    		 try(Factory factory = new Factory()) { 
    			
     		    for (int i=0;i<cats.length;i++) {
    			   category = new Category();
    			   category.put("parentid",parentid);
    			   category.put("name",cats[i]);    			  
    			   categorySave(category);
    		     } 
     		  
    		 } catch(LogicException e) {
    			 throw new ServletException(e);
    		 } 
    	  
    	  }    	       	    	    	       	   
    	   
    	  
       String addClassification=null;  
       try {addClassification=new String (req.getParameter("addClassification"));} catch (NullPointerException e) {}
   	   if (addClassification!=null && addClassification.equals("yes")) {
   		   try(Factory factory = new Factory()) {    				
   				 DBService service = factory.getDBService();    		   
   				 service.updateClassificationFromCategory();			   				
   		      } catch(LogicException e) {
   				 throw new ServletException(e); }       		   
   	   }
    	  
    	  
    	   resp.sendRedirect("list.html");
       } catch(NumberFormatException | DaoException e) {
		  throw new ServletException(e);
	   }

    }
}	 