package web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import de_services.CategoryServiceImpl;
import de_services.ImgServiceImpl;
//import entities.Category;
import entities.Img;
//import entities.Webpages;
import service.LogicException;
import web.action.ActionException;
import web.action.BaseAction;
//import web.action.Action.Result;

public class ImgSaveAction extends BaseAction {
	
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
   Img img = new Img();	  

   String title=req.getParameter("title");  
   if(title == null || title.isBlank()) {title="";}	    	   	    	   
   img.setTitle(title);
   
   String alt=req.getParameter("alt"); 
   if(alt == null || alt.isBlank()) {alt="";}	    
   img.setAlt(alt); 
      
   String url=req.getParameter("url"); 
   if(url == null || url.isBlank()) {url="";}
   img.setUrl(url); 
   
   img.setId(id); 	    	   
   ImgServiceImpl imgServiceImpl= (ImgServiceImpl)getService();
 
   imgServiceImpl.save(img);


   return new Result("/img/list");
    } catch(IllegalArgumentException e) {
    throw new ActionException(e, 400);
     }

  }
}	
