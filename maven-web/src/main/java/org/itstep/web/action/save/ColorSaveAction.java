package org.itstep.web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.de_services.ColorService;
//import de_services.CategoryServiceImpl;
//import org.itstep.de_services.ColorServiceImpl;
//import entities.Category;
import org.itstep.entities.Color;
//import entities.Webpages;
import org.itstep.service.LogicException;
import org.itstep.web.action.ActionException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
//import web.action.Action.Result;
@Component
@Scope("prototype")
public class ColorSaveAction extends BaseAction {
	@Autowired	
	ColorService colorService;
	
@Override
public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
try {
   
	////////////////////////////////////////
	   String sid; 
   try {sid = new String (req.getParameter("id"));}
   catch (NullPointerException e){sid ="";}
 
	   Long id = null; 
   try {id = Long.parseLong(sid);}
   catch (NullPointerException | NumberFormatException err) {id =null;}
 
   Color color = new Color();	  

   String name=req.getParameter("name"); color.setName(name);
   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}	    	   	    	
   	   	    	   
   color.setId(id); 	    	   
   colorService.save(color);

   return new Result("/color/list");
   } catch(IllegalArgumentException e) {
  throw new ActionException(e, 400);
}

}
}	
