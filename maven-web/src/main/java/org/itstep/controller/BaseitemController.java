package org.itstep.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itstep.de_services.BaseitemServiceImpl;
//import org.itstep.de_services.ColorServiceImpl;
//import org.itstep.de_services.SizeServiceImpl;
import org.itstep.de_services.CurrencyServiceImpl;
import org.itstep.de_services.ItemsService;
import org.itstep.service.ColorService;
import org.itstep.service.SizeService;
import org.itstep.entities.Baseitem;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.itstep.entities.Color;
import org.itstep.entities.Currency;
import org.itstep.entities.Items;
import org.itstep.entities.Size;


@Controller
@Scope("prototype")
@RequestMapping(value = "/baseitem")
public class BaseitemController{
	@Autowired private BaseitemServiceImpl baseitemService;
	//@Autowired private BaseitemService baseitemService;
	//@Autowired private ColorServiceImpl colorService;
	//@Autowired private SizeServiceImpl sizeService;
	@Autowired private ColorService colorService;
	@Autowired private SizeService sizeService;
	@Autowired private CurrencyServiceImpl currencyService;
	@Autowired private ItemsService itemsService;	

	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Baseitem> list = baseitemService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("baseitem/list");
        modelAndView.addObject("list", list);
	    return modelAndView;
	}
	
	@RequestMapping("/edit")
	public ModelAndView editBaseitem(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("baseitem/edit");
	    if (id!=null) { 
	      Baseitem baseitem = baseitemService.findById(id);
		  mav.addObject("entity", baseitem);
	    }
		List<Color> list= colorService.findAll();
	    List<Size> listS= sizeService.findAll();
	    List<Currency> listC= currencyService.findAll();
	    List<Items> listI = itemsService.findAll();
	    mav.addObject("color", list);
	    mav.addObject("size", listS);	
	    mav.addObject("currency", listC);	
	    mav.addObject("items", listI);
	    return mav;
	}	
	/*
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveBaseitem(@ModelAttribute("baseitem") Baseitem baseitem) throws LogicException {
		Baseitem baseitem = saveBaseitem(req);
		baseitemService.save(baseitem);
		BaseitemServiceImpl baseitemServiceImpl= (BaseitemServiceImpl)getService();
	    return "redirect:/";
	}
 */	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveBaseitem(HttpServletRequest req) throws LogicException {
		Baseitem baseitem = getBaseitem(req);
		baseitemService.save(baseitem);		
	    return "redirect:/baseitem/list";
	}
	
	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteBaseitem(@RequestParam long id) throws LogicException {
		baseitemService.delete(id);
	    return "redirect:/baseitem/list";       
	}
	/*@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteBaseitemArray(@RequestParam long []id) throws LogicException {
		for (long i:id) {baseitemService.delete(i);}		
	    return "redirect:/";       
	}
	*/
///////////////////////////////////////////////////////////////////////
	private Baseitem getBaseitem(HttpServletRequest req) {
		String sid; 
	 	   try {sid = new String (req.getParameter("id"));}
	 	   catch (NullPointerException e){sid ="";}
	 	   
			   Long id = null;
	 	   try {id = Long.parseLong(sid);}
			   catch (IllegalArgumentException err) {}
	 	   
	 	   Baseitem baseitem = new Baseitem();    	  
	 	 
	 	   String name;
	 	   Long baseprice;
	 	   Integer quantity;	
	 	   
	 	   Long item;
	 	   Long color;
	 	   Long size;
	 	   Long currency;
	 	 
		   name=req.getParameter("name"); baseitem.setName(name);  
		   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}

		   try {baseprice= Long.parseLong(req.getParameter("baseprice")); }
		   catch (NullPointerException | NumberFormatException err) {baseprice =null;}
		     if(baseprice == null || baseprice<0) {baseprice=0L; }
		     baseitem.setBaseprice(baseprice);  	

		   try { quantity= Integer.parseInt(req.getParameter("quantity"));}
	        catch (NullPointerException | NumberFormatException err) {quantity =null;}
		   if(quantity == null || quantity<0) {quantity=0; }; 
		   baseitem.setQuantity(quantity); 

		   item= Long.parseLong(req.getParameter("items")); 
	 	   if(item == null || item<=0) {throw new IllegalArgumentException();}
	 	   baseitem.setItem(new Items()); baseitem.getItem().setId(item);
	 
	 	   color= Long.parseLong(req.getParameter("color")); 
	 	   if(color == null || color<=0) {throw new IllegalArgumentException();}
	 	   baseitem.setColor(new Color()); baseitem.getColor().setId(color);

	 	   size= Long.parseLong(req.getParameter("sizeid")); 
	 	   if(size == null || size<=0) {throw new IllegalArgumentException();}
	 	   baseitem.setSize(new Size()); baseitem.getSize().setId(size);

	 	   currency= Long.parseLong(req.getParameter("currency")); 
	 	   if(currency == null || currency<=0) {throw new IllegalArgumentException();}
	 	   baseitem.setCurrency(new Currency()); baseitem.getCurrency().setId(currency);
	 	
	 	   baseitem.setId(id); 	    	   	 	   
		   return baseitem;
	}
	
}
