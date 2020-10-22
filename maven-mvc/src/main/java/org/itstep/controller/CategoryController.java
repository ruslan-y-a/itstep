package org.itstep.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itstep.service.WebpagesService;
import org.itstep.service.CategoryService;
//import org.itstep.de_services.CategoryServiceImpl;
//import org.itstep.de_services.WebpagesServiceImpl;
import org.itstep.entities.Category;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.itstep.entities.Webpages;
import org.itstep.help.Params;

@Controller
@Scope("prototype")
@RequestMapping(value = "/category")
public class CategoryController {
	//@Autowired private CategoryServiceImpl categoryService;
	//@Autowired private WebpagesServiceImpl webpagesService;
	@Autowired private CategoryService categoryService;
	@Autowired private WebpagesService webpagesService;
		
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Category> list = categoryService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("category/list");
        modelAndView.addObject("list", list);
	    return modelAndView;
	}
	@RequestMapping("/edit")
	public ModelAndView editCategory(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("category/edit");
	    if (id!=null) {
	     Category category = categoryService.findById(id);
	     mav.addObject("entity", category);
	    }
	    List<Webpages> webpages = webpagesService.findAll();	
	    List<Category> list = categoryService.findAll();	    
	    mav.addObject("webpages", webpages);
	    mav.addObject("list", list);	 
	    return mav;
	}
/*	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCategory(@ModelAttribute("category") Category category) throws LogicException {
		categoryService.save(category);
	    return "redirect:/";
	}*/
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCategory(HttpServletRequest req) throws LogicException {
		Category category = getCategory(req);
		categoryService.save(category);
	    return "redirect:/category/list";
	}
	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteCategory(@RequestParam long id) throws LogicException {
		categoryService.delete(id);
	    return "redirect:/category/list";       
	}
/*	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteCategory(@RequestParam long []id) throws LogicException {
		for (long i:id) {categoryService.delete(i);}		
	    return "redirect:/";       
	}
	*/
///////////////////////////////////////////////////////////////////////////
	private Category getCategory(HttpServletRequest req) {
		   String sid; 
    	   try {sid = new String (req.getParameter("id"));}
    	   catch (NullPointerException e){sid ="";}
    	 
   		   Long id = null; Long parentid=null;
    	   try {id = Long.parseLong(sid);}
    	   catch (NullPointerException | NumberFormatException err) {id =null;}
    	   Category category = new Category();	  
    	
    	   String name=req.getParameter("name"); category.setName(name);
    	   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}	    	   	    	
    	   
    	   try { parentid= Long.parseLong(req.getParameter("parentid")); 
    	   }  catch (NullPointerException | NumberFormatException err) {parentid =null;} 
    	   if (parentid!= null && parentid==0) {parentid=null;}
    	   
    	   Boolean nochild; 
    	   try { nochild= Boolean.parseBoolean(req.getParameter("nochild")); 
    	   }  catch (NullPointerException | NumberFormatException err) {nochild =false;}
    	   
    	   
    	   Long webpages;
    	   try { webpages= Long.parseLong(req.getParameter("webpages")); 
    	   }  catch (NullPointerException | NumberFormatException err) {webpages =null;} 
    	   if (webpages!=null && webpages==0) {webpages=null;}
    	   if (webpages!=null) {
    	   category.setWebpages(new Webpages());
    	   category.getWebpages().setId(webpages);
    	   category.getWebpages().setEntity(Params.WP_CATEGORY); 
    	   category.getWebpages().setEntityid(id);
    	   }
   	    	 	    	   
    	   if (parentid!= null && parentid>0) {
    		   category.setParentid(parentid);
    	   } else {	   
    		    if (nochild) {category.setParentid(id);}
    		   }    	    	     	   	    	  
    	   category.setId(id);
    	   return category; 
	}
	
}
