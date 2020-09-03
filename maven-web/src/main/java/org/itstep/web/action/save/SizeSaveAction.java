package org.itstep.web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.de_services.SizeService;
//import de_services.ColorServiceImpl;
//import org.itstep.de_services.SizeServiceImpl;
//import entities.Color;
import org.itstep.entities.Size;
import org.itstep.service.LogicException;
import org.itstep.web.action.ActionException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
//import web.action.Action.Result;
@Component
@Scope("prototype")
public class SizeSaveAction extends BaseAction {
	@Autowired		
	SizeService sizeService;
	
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
 
   Size size = new Size();	  

   String name=req.getParameter("name"); size.setName(name);
   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}	    	   	    	
   	   	    	   
   size.setId(id); 	    	   
   sizeService.save(size);

   return new Result("/size/list");
   } catch(IllegalArgumentException e) {
  throw new ActionException(e, 400);
}

}
}	

