package web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.WebpagesServiceImpl;
import entities.Webpages;
import service.LogicException;
import web.action.ActionException;
import web.action.BaseAction;

public class WebpagesSaveAction extends BaseAction {

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		try {
	    	   
	    	   String sid; 
	    	   try {sid = new String (req.getParameter("id"));}
	    	   catch (NullPointerException e){sid ="";}
	    	 
	   		   Long id = null;
	    	   try {id = Long.parseLong(sid);}
	  		   catch (NullPointerException | NumberFormatException err) {id =null;}
	    	   Webpages webpages = new Webpages();  
	    	   
	    	   
	    	   String url=req.getParameter("url");  if(url == null) {url="";} webpages.setUrl(url);	    	   
	    	   
	    	   String title=req.getParameter("title"); if(title == null) {title="";}  webpages.setTitle(title); 	    	   
	    	   
	    	   String description=req.getParameter("description");  if(description == null) {description="";}
	    	   webpages.setDescription(description);	    	   
	    	   
	    	   String keywords=req.getParameter("keywords"); if(keywords == null) {keywords="";} 
	    	   
	    	   webpages.setKeywords(keywords);
	    	   String text=req.getParameter("text"); if(text == null) {text="";} 
	    	   webpages.setText(text);
	    	   
	    	   String h1=req.getParameter("h1"); if(h1 == null) {h1="";} 
	    	   webpages.setH1(h1); 
	    	   
	    	   String srobots=req.getParameter("robots"); 	    	   	    	  
	    	   Integer robots= Integer.parseInt(srobots); 
	    	   if(robots == null || robots==0) { webpages.setIndex(); }
	    	   else {webpages.setRobots(robots); }
	    	    	    	   	    	   
	    	   webpages.setId(id); 	    	   
	    	   
	    	   WebpagesServiceImpl webpagesService = (WebpagesServiceImpl)getService();
	    	   System.out.println("SAVING ==================)"); 
	    	   webpagesService.save(webpages);	    	    
	    	//	System.out.println("-------------SAVED user:" + user);
				return new Result("/webpages/list");

		} catch(IllegalArgumentException e) {
			throw new ActionException(e, 400);
		}
	}

}
