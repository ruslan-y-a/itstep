package org.itstep.web.action.save;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.de_services.CategoryService;
//import org.itstep.de_services.CategoryServiceImpl;
import org.itstep.service.LogicException;
import org.itstep.entities.Category;
import org.itstep.entities.Webpages;
import org.itstep.help.Params;
import org.itstep.web.action.ActionException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class CategorySaveAction extends BaseAction {
	@Autowired		
	CategoryService categoryService;
	
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
	    	   category.getWebpages().setId(webpages);
	    	   category.getWebpages().setEntity(Params.WP_CATEGORY); 
	    	   category.getWebpages().setEntityid(id);
	    	   }
	   	    	 	    	   
	    	   if (parentid!= null && parentid>0) {
	    		   category.setParentid(parentid);
	    	   } else {	   
	    	//	   System.out.println("=========parentid)"+parentid);
	    		    if (nochild) {category.setParentid(id);}
	    		   }
	    	    	 
	    	   	    	   
	    	   category.setId(id); 	    	   	    
	    	  categoryService.save(category);

	    	//  Result result = new Result("/category/list");
	    	
			return new Result("/category/list");
    	} catch(IllegalArgumentException e) {
		throw new ActionException(e, 400);
	   }

    }
}	
