package org.itstep.controller;

import java.util.List;
//import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
//import org.itstep.de_services.CurrencyService;
//import org.itstep.de_services.CurrencyServiceImpl;
import org.itstep.entities.Currency;
import org.itstep.service.CurrencyService;
import org.itstep.service.LogicException;

@Controller
@Scope("prototype")
@RequestMapping(value = "/currency")
public class CurrencyController{
	//@Autowired private CurrencyServiceImpl currencyService;
	@Autowired private CurrencyService currencyService;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView list() throws LogicException {
	    List<Currency> list = currencyService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("currency/list");
        modelAndView.addObject("list", list);
	    return modelAndView;
	}
	
	@RequestMapping("/edit")
	public ModelAndView editCurrency(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("currency/edit");
	    if (id!=null) { 
	      Currency currency = currencyService.findById(id);
	      mav.addObject("entity", currency);
	    }
	    return mav;
	}
/*	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCurrency(@ModelAttribute("country") Currency currency) throws LogicException {
		currencyService.save(currency);
	    return "redirect:/";
	} */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCurrency(HttpServletRequest req) throws LogicException {
		Currency currency = getCurrency(req);
		currencyService.save(currency);
	    return "redirect:/currency/list";
	}
	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteCurrency(@RequestParam long id) throws LogicException {
		currencyService.delete(id);
	    return "redirect:/currency/list";       
	}
	/*		@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteCurrency(@RequestParam long []id) throws LogicException {
		for (long i:id) {currencyService.delete(i);}		
	    return "redirect:/";       
	}

	@RequestMapping("/search")
	public ModelAndView search(@RequestParam String keyword) {
	    List<Currency> result = currencyService.search(keyword);
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("country/search");	    
        modelAndView.addObject("result", result);	 
	    return modelAndView;    
	}	
	@RequestMapping("/new")
	public String newCustomerForm(Map<String, Object> model) {
		Currency country = new Currency();
	    model.put("country", country);
	    return "country/new";
	}	
	*/
///////////////////////////////////////////////////////////
	private Currency getCurrency(HttpServletRequest req) {
		   String sid; 
		   try {sid = new String (req.getParameter("id"));}
		   catch (NullPointerException e){sid ="";}
		 
			   Long id = null; 
		   try {id = Long.parseLong(sid);}
		   catch (NullPointerException | NumberFormatException err) {id =null;}
		 
		   Currency currency = new Currency();	  

		   String name=req.getParameter("name"); currency.setName(name);
		   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}	    	   	    	
		   	   	    	   
		   Double rate;
		   try { rate= Double.parseDouble(req.getParameter("rate")); 
		   }  catch (NullPointerException | NumberFormatException err) {rate =1d;}   
		   
		   currency.setRate(rate); 
		   
		   currency.setId(id); 	    	   
		   return currency;
	}
}
