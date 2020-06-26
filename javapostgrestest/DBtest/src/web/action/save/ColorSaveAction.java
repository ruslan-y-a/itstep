package web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import de_services.CategoryServiceImpl;
import de_services.ColorServiceImpl;
//import entities.Category;
import entities.Color;
//import entities.Webpages;
import service.LogicException;
import web.action.ActionException;
import web.action.BaseAction;
//import web.action.Action.Result;

public class ColorSaveAction extends BaseAction {
	
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
   ColorServiceImpl colorServiceImpl= (ColorServiceImpl)getService();

   colorServiceImpl.save(color);

   return new Result("/color/list");
   } catch(IllegalArgumentException e) {
  throw new ActionException(e, 400);
}

}
}	
