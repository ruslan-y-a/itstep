package org.itstep.controller;

import org.itstep.entities.Classification;
//import org.itstep.entities.Size;
import org.itstep.entities.Tagcloud;
import org.itstep.entities.Webpages;
import org.itstep.help.Params;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
//import org.springframework.beans.BeansException;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itstep.de_services.ClassificationServiceImpl;
//import org.itstep.de_services.TagcloudService;
import org.itstep.de_services.TagcloudServiceImpl;
import org.itstep.de_services.WebpagesServiceImpl;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping(value = "/tagcloud")
public class TagcloudController {
	@Autowired
	  private TagcloudServiceImpl tagcloudService;
	@Autowired private WebpagesServiceImpl webpagesService;
	@Autowired private ClassificationServiceImpl classificationService;

	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Tagcloud> listUser = tagcloudService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("tagcloud/list");
        modelAndView.addObject("list", listUser);
	    return modelAndView;
	}
	
		@RequestMapping("/edit")
		public ModelAndView editTagcloud(@RequestParam(required = false) Long id) throws LogicException {
		    ModelAndView mav = new ModelAndView("tagcloud/edit");
		    if (id!=null) {
		      Tagcloud tagcloud = tagcloudService.findById(id);
		      mav.addObject("entity", tagcloud);
		    }
		    List<Webpages> listWebpages = webpagesService.findAll();	
		    List<Classification> list = classificationService.findAll();			    	
		    mav.addObject("webpages", listWebpages);	
		    mav.addObject("classification", list);	
		    return mav;
		}
	/*	@RequestMapping(value = "/save", method = RequestMethod.POST)
		public String saveTagcloud(@ModelAttribute("tagcloud") Tagcloud tagcloud) throws LogicException {
			tagcloudService.save(tagcloud);
		    return "redirect:/";
		}	*/
		@RequestMapping(value = "/save", method = RequestMethod.POST)
		public String saveTagcloud(HttpServletRequest req) throws LogicException {
			Tagcloud tagcloud = getTagcloud(req);
			tagcloudService.save(tagcloud);
		    return "redirect:/tagcloud/list";
		}
		
		@RequestMapping(value ="/delete", method = RequestMethod.POST)
		public String deleteTagcloud(@RequestParam long id) throws LogicException {
			tagcloudService.delete(id);
		    return "redirect:/tagcloud/list";       
		}
/////////////////////////////////////////////////////////
		private Tagcloud getTagcloud(HttpServletRequest req) {
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
			   
			   Long iwebpages; 	    	  	 
			   iwebpages= Long.parseLong(req.getParameter("webpages")); 
		 	   if(iwebpages == null || iwebpages<=0) {throw new IllegalArgumentException();}
		 	   tagcloud.setWebpages(new Webpages()); tagcloud.getWebpages().setId(iwebpages); 	 
		 	  tagcloud.getWebpages().setEntity(Params.WP_TAGCLOUD);  
		 	  tagcloud.getWebpages().setEntityid(id);		 	  		 	 
			  return tagcloud;
		}
}
