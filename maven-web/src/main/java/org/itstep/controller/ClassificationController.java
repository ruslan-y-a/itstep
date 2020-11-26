package org.itstep.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itstep.service.ClassificationService;
//import org.itstep.de_services.ClassificationServiceImpl;
import org.itstep.entities.Classification;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping(value = "/classification")
public class ClassificationController {
	//@Autowired private ClassificationServiceImpl classificationService;
	@Autowired private ClassificationService classificationService;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Classification> list = classificationService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("classification/list");
        modelAndView.addObject("list", list);
	    return modelAndView;
	}
	
	@RequestMapping("/edit")
	public ModelAndView editClassification(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("classification/edit");
	    if (id!=null) {
	     Classification classification = classificationService.findById(id);
	     mav.addObject("entity", classification);
	    }
	    List<Classification> list = classificationService.findAll();	    
	    mav.addObject("list", list);
	    return mav;
	}	
/*	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveClassification(@ModelAttribute("classification") Classification classification) throws LogicException {
		classificationService.save(classification);
	    return "redirect:/";
	}*/
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveClassification(HttpServletRequest req) throws LogicException {
		Classification classification = getClassification(req);
		classificationService.save(classification);
	    return "redirect:/classification/list";
	}
	@RequestMapping("/delete")
	public String deleteClassification(@RequestParam long id) throws LogicException {
		classificationService.delete(id);
	    return "redirect:/classification/list";       
	}
/*	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteClassification(@RequestParam long []id) throws LogicException {
		for (long i:id) {classificationService.delete(i);}		
	    return "redirect:/";       
	}
	*/
	/////////////////////////////////////////////////////////////////
	
	private Classification getClassification(HttpServletRequest req) {
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
		   
		   Boolean nochild; 
		   try { nochild= Boolean.parseBoolean(req.getParameter("nochild")); 
		   }  catch (NullPointerException | NumberFormatException err) {nochild =false;}
		              
		  
		   if (parentid!= null && parentid>0) {
			   classification.setParentid(parentid);
		   } else {	   
			    if (nochild) {classification.setParentid(id);}
			   }   
		   
		   classification.setCategoryid(categoryid);
		   classification.setId(id); 	    	   
		   return classification;
		   
	}
	
}
