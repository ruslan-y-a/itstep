package org.itstep.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import org.itstep.entities.Client;
import org.itstep.entities.Country;
import org.itstep.entities.Items;
import org.itstep.entities.User;
import org.itstep.service.LogicException;
/*
import org.itstep.de_services.ClientService;
import org.itstep.de_services.CountryService;
import org.itstep.de_services.ItemsService;
import org.itstep.de_services.UserService;
*/
import org.itstep.de_services.ClientServiceImpl;
import org.itstep.de_services.CountryServiceImpl;
import org.itstep.de_services.ItemsServiceImpl;
import org.itstep.de_services.UserServiceImpl;

@Controller
@Scope("prototype")
@RequestMapping(value = "/client")
public class ClientController{
	@Autowired private ClientServiceImpl clientService;
	@Autowired private ItemsServiceImpl itemsService;
	@Autowired private CountryServiceImpl countryService;
	@Autowired private UserServiceImpl userService;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Client> list = clientService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("client/list");
        modelAndView.addObject("list", list);
	    return modelAndView;
	}

	@RequestMapping("/edit")
	public ModelAndView editClient(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("client/edit");
	    if (id!=null) {
	     Client client = clientService.findById(id);	  
	     mav.addObject("entity", client);
	    }    
	    List<Country> countries = countryService.findAll();
	    List<User> users = userService.findAll();
	    List<Items> items = itemsService.findAll();	    
	    mav.addObject("country", countries);	
	    mav.addObject("users", users);	
	    mav.addObject("items", items);		    
	    return mav;
	}	
/*	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveClient(@ModelAttribute("client") Client client) throws LogicException {
		clientService.save(client);
	    return "redirect:/";
	} */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveClient(HttpServletRequest req) throws LogicException {
		Client client = getClient(req); 
		clientService.save(client);
	    return "redirect:/client/list";
	}
	@RequestMapping("/delete")
	public String deleteCustomerForm(@RequestParam long id) throws LogicException {
		clientService.delete(id);
	    return "redirect:/client/list";       
	}
	/*		@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteCustomerForm(@RequestParam long []id) throws LogicException {
		for (long i:id) {clientService.delete(i);}		
	    return "redirect:/";       
	}
	
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam String keyword) {
	    List<Client> result = clientService.search(keyword);
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("client/search");	    
        modelAndView.addObject("result", result);	 
	    return modelAndView;    
	}
	
	@RequestMapping("/new")
	public String newCustomerForm(Map<String, Object> model) {
		Client client = new Client();
	    model.put("client", client);
	    return "client/new";
	}
	*/
///////////////////////////////////////////////////////////////////////////////	
	private Client getClient(HttpServletRequest req) {
		 String sid; 
  	   try {sid = new String (req.getParameter("id"));}
  	   catch (NullPointerException e){sid ="";}
  	 
 		   Long id = null;
  	   try {id = Long.parseLong(sid);}
		   catch (NullPointerException | NumberFormatException err) {id =null;}
  	   Client client = new Client();    	  
  
  	   String country=req.getParameter("country"); 
  	   Long icountry = null;
  	   try {icountry = Long.parseLong(country);}
		   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}	    	   
  	   client.setCountry(new Country()); client.getCountry().setId(icountry);
  	   
  	   
  	   String address=req.getParameter("address"); client.setAddress(address);
  	   if(address == null || address.isBlank()) {throw new IllegalArgumentException();}
  	   	    	   	    	  	    	   	    	   
  	   String creationdate=req.getParameter("creationdate"); Date date;
  	   if(creationdate == null || creationdate.isBlank()) {
  		   date = new Date();
  		   } else {	    			   
  			   SimpleDateFormat format = new SimpleDateFormat();
  	    	   format.applyPattern("dd.MM.yyyy");
  			   try { date= format.parse(creationdate);}
  	    	   catch ( ParseException err) {
  	    		   format.applyPattern("dd-MM-yyyy");
  	    		   try { date= format.parse(creationdate);}
  	    		   catch ( ParseException err1) {
  	    		   date = new Date();	    	}    		   
  	    	   }; 
  		   } 	   
  	   client.setCreationdate(date);     	     
  	   
  	   String user=req.getParameter("user"); 
  	   Long iuser = null;
  	   try {iuser = Long.parseLong(user);}
		   catch (NullPointerException | NumberFormatException err) {iuser =null;}
  	   if(iuser == null) {throw new IllegalArgumentException();}
  	   client.setUser(new User()); client.getUser().setId(iuser);
  	   	    	  	    	  
  	   String bonuspoints=req.getParameter("bonuspoints");   ;
  	   Integer ibonuspoints;
  	   if(bonuspoints == null || bonuspoints.isBlank()) {ibonuspoints=0;}
  	   else {
  	   try {ibonuspoints = Integer.parseInt(bonuspoints);}
		   catch (NullPointerException | NumberFormatException err) {ibonuspoints=0;}
  	   }; client.setBonuspoints(ibonuspoints);
  	   	    	   
  	   String phoneno=req.getParameter("phoneno"); 
  	   if(phoneno == null || phoneno.isBlank()) {phoneno="";}
  	   client.setPhoneno(phoneno);
  	   Items item;
  	   String sTtems[]=  req.getParameterValues("recentitems"); 	    	   
  	   if (sTtems!=null) {
  	   ArrayList<Items> itemIds = new ArrayList<>(); 
  	   for(int i=0; i<sTtems.length;i++) {
  		   try { 	    			 
  			   item = new Items(); item.setId(Long.parseLong(sTtems[i])); 	    		    
  			   itemIds.add(item);
  		      } catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}
  		   }	    	       	 	    	   
  	   client.setRecentitems(itemIds);
  	   }
  	   client.setId(id); 	    	      		  	  
			return client;
	}
}
