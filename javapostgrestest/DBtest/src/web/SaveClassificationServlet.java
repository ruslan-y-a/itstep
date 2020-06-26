package web;

import java.io.IOException;
import java.util.regex.PatternSyntaxException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import help.Helper;
import postgres.DaoException;
import service.DBService;
import service.LogicException;
import tabs.Classification;
import ui.Factory;

public class SaveClassificationServlet extends HttpServlet {
	
	private Long classificationSave(Classification classification) throws ServletException {
		try(Factory factory = new Factory()) {
			classification.setDBName("classification");
			 DBService service = factory.getDBService();    		   
			 return service.save(classification);			   				
	      } catch(LogicException e) {
			 throw new ServletException(e);
		  }   
	}
	private void classificationUpdate(Classification classification) throws ServletException {
		try(Factory factory = new Factory()) {
			classification.setDBName("classification");
			 DBService service = factory.getDBService();    		   
			 service.update(classification);			   				
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
    	   
    	   Classification classification = new Classification(); //Mapper.getEntity("category");
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
    		   classification = new Classification();
    		   classification.put("name",parentname);
    		   
    		   try {parentid = Long.parseLong(sparentid);}
      		   catch (NumberFormatException err) {}  
    		   
    		   if (parentid!=null && parentid!=0) {classification.put("id",parentid); classification.DBsetId(parentid); }
    		   parentid= classificationSave(classification);
    		   
    		   if (parentname.equals(name)) {    			  
    			   classification.put("id",parentid);
    			   classification.put("parentid",parentid);
    			   classification.put("name",parentname);
    			   classification.DBsetId(parentid); 
    			   classificationUpdate(classification);
    			   //resp.sendRedirect("list.html");
    			   name=null;
    		   }
    		   
    		   if (parentname.equals(categories)) {    			  
    			   classification.put("id",parentid);
    			   classification.put("parentid",parentid);
    			   classification.put("name",parentname);
    			   classification.DBsetId(parentid); 
    			   classificationUpdate(classification);
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
        	   classification = new Classification();
        	   classification.DBsetId(id); 
        	   classification.put("id",id);
        	   classification.put("name",name);   	 
        	   classification.put("parentid",parentid); 	  
        	   classificationSave(classification);  
        	 
    	   } 
    	   
    	  if (categories != null && sid==null) {
    		  
    	   String [] cats; 
    		 try {
    			 cats = categories.split(Helper.findDelimiter(categories));
    		 } catch (PatternSyntaxException e) {cats = new String [0];cats[0]=categories;}
    		 
    		 try(Factory factory = new Factory()) { 
    			
     		    for (int i=0;i<cats.length;i++) {
     		       classification = new Classification();
     		       classification.put("parentid",parentid);
     		       classification.put("name",cats[i]);    			  
     		       classificationSave(classification);  
    		     } 
     		  
    		 } catch(LogicException e) {
    			 throw new ServletException(e);
    		 } 
    	  
    	  }    	       	    	    	       	   
    	   
    	   resp.sendRedirect("list.html");
       } catch(NumberFormatException | DaoException e) {
		  throw new ServletException(e);
	   }

    }
}	 