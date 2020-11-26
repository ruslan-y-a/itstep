package org.itstep.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import org.itstep.de_services.ColorServiceImpl;
import org.itstep.entities.Color;
import org.itstep.service.ColorService;
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
@RequestMapping(value = "/color")
public class ColorController{
	//@Autowired private ColorServiceImpl colorService;
	@Autowired private ColorService colorService;
		
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Color> list = colorService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("color/list");
        modelAndView.addObject("list", list);
	    return modelAndView;
	}
	
	@RequestMapping("/edit")
	public ModelAndView editColor(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("color/edit");
	    if (id!=null) { 
	      Color color = colorService.findById(id);
	      mav.addObject("entity", color);
	    }
	    return mav;
	}
/*	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveColor(@ModelAttribute("color") Color color) throws LogicException {
		colorService.save(color);
	    return "redirect:/color/list";
	}
	*/	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveColor(HttpServletRequest req) throws LogicException {
		Color color = getColor(req);
		colorService.save(color);
	    return "redirect:/color/list";
	}
	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteColor(@RequestParam long id) throws LogicException {
		colorService.delete(id);
	    return "redirect:/color/list";       
	}
/*	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteColor(@RequestParam long []id) throws LogicException {
		for (long i:id) {colorService.delete(i);}		
	    return "redirect:/";       
	}
	*/
//////////////////////////////////////////////////////////////////
	private Color getColor(HttpServletRequest req) {
		  String sid; 
		   try {sid = new String (req.getParameter("id"));}
		   catch (NullPointerException e){sid ="";}
		 
		   Long id = null; 
		   try {id = Long.parseLong(sid);}
		   catch (NullPointerException | NumberFormatException err) {id =null;}
		 
		   Color color = new Color();	  

		   String name=req.getParameter("name"); color.setName(name);
		   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}	    	   	    	
		   	   	    	   
		   color.setId(id); 	    	   		   
		   return color;
	}
}
