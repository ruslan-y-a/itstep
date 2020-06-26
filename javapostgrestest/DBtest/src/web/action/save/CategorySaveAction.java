package web.action.save;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.CategoryServiceImpl;
import service.LogicException;
import entities.Category;
import entities.Webpages;
import web.action.ActionException;
import web.action.BaseAction;

public class CategorySaveAction extends BaseAction {
				
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
	try {
			   
  			////////////////////////////////////////
  			   String sid; 
	    	   try {sid = new String (req.getParameter("id"));}
	    	   catch (NullPointerException e){sid ="";}
	    	 
	   		   Long id = null; Long parentid=null;
	    	   try {id = Long.parseLong(sid);}
	    	   catch (NullPointerException | NumberFormatException err) {id =null;}
	    	   Category category = new Category();	  
	    	
	    	   String name=req.getParameter("name"); category.setName(name);
	    	   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}	    	   	    	
	    	   
	    	   try { parentid= Long.parseLong(req.getParameter("parentid")); 
	    	   }  catch (NullPointerException | NumberFormatException err) {parentid =null;} 
	    	   if (parentid!= null && parentid==0) {parentid=null;}
	    	   
	    	   Boolean nochild; 
	    	   try { nochild= Boolean.parseBoolean(req.getParameter("nochild")); 
	    	   }  catch (NullPointerException | NumberFormatException err) {nochild =false;}
	    	   
	    	   
	    	   Long webpages;
	    	   try { webpages= Long.parseLong(req.getParameter("webpages")); 
	    	   }  catch (NullPointerException | NumberFormatException err) {webpages =null;} 
	    	   if (webpages!=null && webpages==0) {webpages=null;}
	    	   if (webpages!=null) {
	    	   category.setWebpages(new Webpages());
	    	   category.getWebpages().setId(webpages); }
	   	    	 	    	   
	    	   if (parentid!= null && parentid>0) {
	    		   category.setParentid(parentid);
	    	   } else {	   
	    	//	   System.out.println("=========parentid)"+parentid);
	    		    if (nochild) {category.setParentid(id);}
	    		   }
	    	    	 
	    	   	    	   
	    	   category.setId(id); 	    	   
	    	   CategoryServiceImpl categoryServiceImpl= (CategoryServiceImpl)getService();
	    
	    	  categoryServiceImpl.save(category);

	    	//  Result result = new Result("/category/list");
	    	
			return new Result("/category/list");
    	} catch(IllegalArgumentException e) {
		throw new ActionException(e, 400);
	   }

    }
}	
