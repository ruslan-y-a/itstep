package web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import de_services.ColorServiceImpl;
import de_services.SizeServiceImpl;
//import entities.Color;
import entities.Size;
import service.LogicException;
import web.action.ActionException;
import web.action.BaseAction;
//import web.action.Action.Result;

public class SizeSaveAction extends BaseAction {
	
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
   SizeServiceImpl sizeServiceImpl= (SizeServiceImpl)getService();

   sizeServiceImpl.save(size);

   return new Result("/size/list");
   } catch(IllegalArgumentException e) {
  throw new ActionException(e, 400);
}

}
}	

