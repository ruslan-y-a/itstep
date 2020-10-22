package org.itstep.controller_user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.itstep.de_services.ClientService;
//import org.itstep.de_services.ClientServiceImpl;
import org.itstep.de_services.CountrySevice;
//import org.itstep.de_services.CountrySeviceImpl;
import org.itstep.de_services.UserService;
//import org.itstep.de_services.UserServiceImpl;
import org.itstep.entities.Client;
import org.itstep.entities.Country;
import org.itstep.entities.Role;
import org.itstep.entities.User;
import org.itstep.help.Params;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
public class UserAccountController {
	@Autowired private UserService userService;
	@Autowired private CountrySevice countrySevice;	
	@Autowired private ClientService clientService;
	
	   @RequestMapping(value = {"/login" }, method = RequestMethod.GET)
	    public ModelAndView loginGet(HttpServletRequest req) {
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.addObject("preious_uri", req.getRequestURL());
	        modelAndView.setViewName("login");
	        return modelAndView;
	    }
	   @RequestMapping(value = {"/logout" })
	    public ModelAndView logout(HttpServletRequest req) {
	        ModelAndView modelAndView = new ModelAndView();
	        HttpSession session = req.getSession(false);
			if(session != null) {session.invalidate();}
	        modelAndView.setViewName("index");	   
	        return modelAndView;
	    }
	   @RequestMapping(value = {"/login" }, method = RequestMethod.POST)
	    public String loginPost(Model model, HttpServletRequest req) {
	        ModelAndView modelAndView = new ModelAndView();
	        String login = req.getParameter("login");
			String password = req.getParameter("password");
			User user;
		  try {
			user = userService.authenticate(login, password);
	        String uri=null;
			if(user != null) {			
				HttpSession session = req.getSession();
				session.setAttribute("sessionUser", user);
	            if (user.getRole()==Role.CLIENT) {
	            	uri= req.getParameter("preious_uri");
	        		if (uri!= null && !uri.isBlank()) {
	        			if (uri.indexOf("jsp")>=0) {
	        			  uri=uri.substring(uri.indexOf("jsp")+3);
	            		  uri=uri.substring(0,uri.indexOf(".jsp"));
	        			}
	        		}                	
	            }		   
	     //       System.out.println("=====================================login URI)" + uri);
				if (uri!=null && !uri.isBlank()) {return "redirect:" + uri;}				
			//	  System.out.println("=====================================getUserPath(user))" + getUserPath(user));
				return "redirect:" + getUserPath(user);
			 }
			    modelAndView.addObject("login_error", "Wrong login or pass");
				return "redirect:/login";	
		   } catch (LogicException e) {
			  modelAndView.addObject("login_error", "Wrong login or pass");
				return "redirect:/login";	
		   }					   				
	   }	
	   
	   private String getUserPath(User user) {
			switch(user.getRole()) {
			case ADMIN: return "/backoffice"; 
			case CLIENT: return "/index";
			case PRODUCT: return "/backoffice"; 
			case MANAGER: return "/backoffice"; 
			case CASHIER: return "/backoffice"; 
			case COURIER: return "/backoffice"; 
			default: return "/index";
			}	   
	   }
	////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
	   @RequestMapping(value = {"/register" }, method = RequestMethod.GET)
	    public ModelAndView register(HttpServletRequest req) throws LogicException {
	        ModelAndView modelAndView = new ModelAndView();
	        
	        List<Country> countries = countrySevice.findAll();
			List<User> users = userService.findAll();
			 modelAndView.addObject("countries", countries);
			 modelAndView.addObject("users", users);	        	        
	         modelAndView.setViewName("register");
	        return modelAndView;
	    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////	   
	   @RequestMapping(value = {"/registeruser" }, method = RequestMethod.POST)
	    public String registeruser(Model model, HttpServletRequest req) throws LogicException {
	      	        
	       				
	    		User user = new User();   
	    		user.setRole(Role.CLIENT); 	    	
	    		String login=req.getParameter("login"); user.setLogin(login);
	      	    if(login == null || login.isBlank()) {throw new IllegalArgumentException();}  	   
	      	     String name=req.getParameter("name"); user.setName(name);
	      	    if(name == null || name.isBlank()) {throw new IllegalArgumentException();}  	   
	      	    String password=req.getParameter("password"); user.setPassword(password);
	      	    if(password == null || password.isBlank()) {throw new IllegalArgumentException();}  	   
	      	    String email=req.getParameter("email"); user.setEmail(email);
	      	    if(email == null || email.isBlank()) {throw new IllegalArgumentException();}
////////////////////////////////////////////////////////////////////////////////	    		
	      	   Client client = new Client();    	  
	    	    
	    	   String country=req.getParameter("country"); 
	    	   Long icountry = null;
	    	   try {icountry = Long.parseLong(country);}
	    		   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}	    	   
	    	   client.setCountry(new Country()); client.getCountry().setId(icountry);
	      	    
	    	   String address=req.getParameter("address"); client.setAddress(address);
	    	   if(address == null || address.isBlank()) {throw new IllegalArgumentException();}	   
	    	   Date date = new Date();
	    	   client.setCreationdate(date);   
	    	   
	    	   client.setBonuspoints(Params.REGISTER_BONUS);
	    	   
	    	   String phoneno=req.getParameter("phoneno"); 
	    	   if(phoneno == null || phoneno.isBlank()) {throw new IllegalArgumentException();}
	    	   client.setPhoneno(phoneno);	   
	      	  
	    		Long nuid=userService.save(user);  user.setId(nuid);	  	    	    
	      	    client.setUser(user); clientService.save(client);
	      	 
	      	    user = userService.authenticate(login, password);			
	    		if(user != null) {		
	    			HttpSession session = req.getSession();
	    			session.setAttribute("sessionUser", user);			
	    		} else {
	    			throw new LogicException(null);
	    		}	      	   	      	   	    	 	
	    		return "redirect:/users/page";	       
	    }
}
