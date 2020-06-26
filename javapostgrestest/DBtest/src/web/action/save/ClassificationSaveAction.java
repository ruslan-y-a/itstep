package web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.ClassificationServiceImpl;
import entities.Classification;
import service.LogicException;
import web.action.ActionException;
import web.action.BaseAction;

public class ClassificationSaveAction extends BaseAction {
	
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
   Classification classification = new Classification();	  

   String name=req.getParameter("name"); classification.setName(name);
   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}	    	   	    	
   
   try { parentid= Long.parseLong(req.getParameter("parentid")); 
   }  catch (NullPointerException | NumberFormatException err) {parentid =null;} 
   if (parentid!=null && parentid==0) {parentid=null;}
   
   Long categoryid;
   try { categoryid= Long.parseLong(req.getParameter("categoryid")); 
   }  catch (NullPointerException | NumberFormatException err) {categoryid =null;} 
   if (categoryid!=null && categoryid==0) {categoryid=null;}
//  System.out.println("=========categoryid)"+categoryid);
   
   Boolean nochild; 
   try { nochild= Boolean.parseBoolean(req.getParameter("nochild")); 
   }  catch (NullPointerException | NumberFormatException err) {nochild =false;}
              
  
   if (parentid!= null && parentid>0) {
	   classification.setParentid(parentid);
   } else {	   
	  // System.out.println("=========parentid)"+parentid);
	    if (nochild) {classification.setParentid(id);}
	   }   
   
   classification.setCategoryid(categoryid);
   classification.setId(id); 	    	   
   ClassificationServiceImpl classificationServiceImpl= (ClassificationServiceImpl)getService();

   classificationServiceImpl.save(classification);

return new Result("/classification/list");
} catch(IllegalArgumentException e) {
throw new ActionException(e, 400);
}

}
}	
