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
//import org.itstep.de_services.CountryServiceImpl;
//import org.itstep.de_services.CurrencyServiceImpl;
import org.itstep.service.CountryService;
import org.itstep.service.CurrencyService;
import org.itstep.entities.Country;
import org.itstep.entities.Currency;
import org.itstep.service.LogicException;

@Controller
@Scope("prototype")
@RequestMapping(value = "/country")
public class CountryController {
	//@Autowired private CurrencyServiceImpl currencyService;
	//@Autowired private CountryServiceImpl countryService;
	@Autowired private CurrencyService currencyService;
	@Autowired private CountryService countryService;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Country> list = countryService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("country/list");
        modelAndView.addObject("list", list);
	    return modelAndView;
	}
	
	@RequestMapping("/edit")
	public ModelAndView editCountry(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("country/edit");
        if (id!=null) {
         Country country = countryService.findById(id);
         mav.addObject("entity", country);
        }
        List<Currency> list = currencyService.findAll();	    
	    mav.addObject("currency", list);
	    return mav;
	}
/*	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCountry(@ModelAttribute("country") Country country) throws LogicException {
		countryService.save(country);
	    return "redirect:/";
	} */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCountry(HttpServletRequest req) throws LogicException {
		Country country = getCountry(req);
		countryService.save(country);
	    return "redirect:/country/list";
	}
	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteCountry(@RequestParam long id) throws LogicException {
		countryService.delete(id);
	    return "redirect:/country/list";       
	}
	/*	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteCountry(@RequestParam long []id) throws LogicException {
		for (long i:id) {countryService.delete(i);}		
	    return "redirect:/";       
	}
	
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam String keyword) {
	    List<Country> result = countryService.search(keyword);
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("country/search");	    
        modelAndView.addObject("result", result);	 
	    return modelAndView;    
	}	
	@RequestMapping("/new")
	public String newCustomerForm(Map<String, Object> model) {
		Country country = new Country();
	    model.put("country", country);
	    return "country/new";
	}
	*/
/////////////////////////////////////////////
	private Country getCountry(HttpServletRequest req) {
		  String sid; 
		   try {sid = new String (req.getParameter("id"));}
		   catch (NullPointerException e){sid ="";}
		 
			   Long id = null; 
		   try {id = Long.parseLong(sid);}
		   catch (NullPointerException | NumberFormatException err) {id =null;}
		 
		   Country country = new Country();	  

		   String name=req.getParameter("name"); country.setName(name);
		   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}	    	   	    	
		   	   	    	   
		   Long currency;
		   try { currency= Long.parseLong(req.getParameter("currency")); 
		   }  catch (NullPointerException | NumberFormatException err) {currency =null;} 
		   if (currency==0) {currency=null;}
		   
		   country.setCurrency(new Currency());
		   country.getCurrency().setId(currency);	
		   
		   country.setId(id); 	    	   
		   return country;
	}
}
