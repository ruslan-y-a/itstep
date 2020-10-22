package org.itstep.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
//import org.itstep.de_services.SizeServiceImpl;
import org.itstep.entities.Size;
import org.itstep.service.LogicException;
import org.itstep.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping(value = "/size")
public class SizeController {
	//@Autowired private SizeServiceImpl sizeService;
	@Autowired private SizeService sizeService;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Size> listUser = sizeService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("size/list");
        modelAndView.addObject("list", listUser);
	    return modelAndView;
	}
	
	@RequestMapping("/edit")
	public ModelAndView editSize(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView("size/edit");
	    if (id!=null) {
	     Size size = sizeService.findById(id);	    
	     mav.addObject("entity", size);	 
	    }
	    return mav;
	}
/*	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveSize(@ModelAttribute("size") Size size) throws LogicException {
		sizeService.save(size);
	    return "redirect:/";
	}	*/
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveSize(HttpServletRequest req) throws LogicException {
		Size size= getSize(req);
		sizeService.save(size);
	    return "redirect:/size/list";
	}

	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteSize(@RequestParam long id) throws LogicException {
		sizeService.delete(id);
	    return "redirect:/size/list";       
	}
/*	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteSize(@RequestParam long []id) throws LogicException {
		for (long i:id) {sizeService.delete(i);}		
	    return "redirect:/";       
	} */
/////////////////////////////////////////////////////
	private Size getSize(HttpServletRequest req) {
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
		   return size;
	}
}
