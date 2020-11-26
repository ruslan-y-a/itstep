package org.itstep.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.itstep.de_services.WebpagesServiceImpl;
import org.itstep.entities.Webpages;
import org.itstep.service.LogicException;
import org.itstep.service.WebpagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping(value = "/webpages")
public class WebpagesController {
	//@Autowired private WebpagesServiceImpl webpagesService;
	@Autowired private WebpagesService webpagesService;

	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Webpages> listUser = webpagesService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("webpages/list");
        modelAndView.addObject("list", listUser);
	    return modelAndView;
	}
	
	@RequestMapping("/edit")
	public ModelAndView editWebpages(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView("webpages/edit");
	    if (id!=null) {
	    Webpages webpages = webpagesService.findById(id);
	    mav.addObject("entity", webpages);
	    }
	    return mav;
	}
/*	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveWebpages(@ModelAttribute("webpages") Webpages webpages) throws LogicException {
		webpagesService.save(webpages);
	    return "redirect:/";
	}	*/
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveWebpages(HttpServletRequest req) throws LogicException {
		Webpages  webpages = getWebpages(req);
		webpagesService.save(webpages);
	    return "redirect:/webpages/list";
	}

	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteWebpages(@RequestParam long id) throws LogicException {
		webpagesService.delete(id);
	    return "redirect:/webpages/list";       
	}
/*	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteWebpages(@RequestParam long []id) throws LogicException {
		for (long i:id) {webpagesService.delete(i);}		
	    return "redirect:/";       
	}
	*/
////////////////////////////////////////////////////////
	private Webpages getWebpages(HttpServletRequest req) {
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
   	    	    	   	   
   	   String entity=req.getParameter("entity"); if(entity == null) {entity="";} 
   	   webpages.setEntity(entity);
   	   
   	   String sentityid=req.getParameter("entityid"); if(sentityid == null) {sentityid="";} 
   	   Long entityid=null;
   	   try {entityid = Long.parseLong(sentityid);}
 		   catch (NullPointerException | NumberFormatException err) {}
   	   webpages.setEntityid(entityid);	    	   	    	   
   	   webpages.setId(id); 	    	   
			return webpages;
	}
}
