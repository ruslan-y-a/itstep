package web.action.save;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.TagcloudServiceImpl;
import entities.Classification;
import entities.Tagcloud;
import entities.Webpages;
import help.Params;
import service.LogicException;
import web.action.ActionException;
import web.action.BaseAction;

public class TagcloudSaveAction extends BaseAction {
	
@Override
public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
	try {
		   
		////////////////////////////////////////
		String sid; 
 	   try {sid = new String (req.getParameter("id"));}
 	   catch (NullPointerException e){sid ="";}
 	   
		   Long id = null;
 	   try {id = Long.parseLong(sid);}
		   catch (IllegalArgumentException err) {}
 	   
 	  Tagcloud tagcloud = new Tagcloud();
 	   
 	  String classifications[]=  req.getParameterValues("classification"); 	    	   
	   ArrayList<Classification> iClassifications = new ArrayList<>(); 
	   for(int i=0; i<classifications.length;i++) {
		   try { 
			   Classification classification = new Classification(); classification.setId(Long.parseLong(classifications[i])); 	    		    
			   iClassifications.add(classification);
		      } catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}
		   }	    	       	 	    	   
	   tagcloud.setClassification(iClassifications);  	    	  
	   tagcloud.setId(id); 	    
	   
	   TagcloudServiceImpl tagcloudServiceImpl= (TagcloudServiceImpl)getService();	 
	   
	   Long iwebpages; 	    	  	 
	   iwebpages= Long.parseLong(req.getParameter("webpages")); 
 	   if(iwebpages == null || iwebpages<=0) {throw new IllegalArgumentException();}
 	   tagcloud.setWebpages(new Webpages()); tagcloud.getWebpages().setId(iwebpages); 	 
 	  tagcloud.getWebpages().setEntity(Params.WP_TAGCLOUD);  tagcloud.getWebpages().setEntityid(id);
 	  tagcloudServiceImpl.save(tagcloud);
 	 
	   return new Result("/tagcloud/list");
	    } catch(IllegalArgumentException e) {
	    throw new ActionException(e, 400);
	     }
  }
}	
