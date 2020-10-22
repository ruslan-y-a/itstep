package org.itstep.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
//import org.itstep.de_services.UserServiceImpl;
import org.itstep.entities.User;
import org.itstep.service.LogicException;
import org.itstep.service.UserService;
import org.itstep.entities.Role;

@Controller
@Scope("prototype")
//@RequestMapping(value = "/users")
public class UserController {
	//@Autowired private UserServiceImpl userService;
	@Autowired private UserService userService;
	
	@RequestMapping(value = { "/users", "/users/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<User> listUser = userService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/list");
        modelAndView.addObject("list", listUser);
	    return modelAndView;
	}
	
	@RequestMapping("/users/edit")
	public ModelAndView editUser(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("users/edit");
	   if (id!=null) {
	     User user = userService.findById(id);
	     mav.addObject("entity", user);
	   }
	    return mav;
	}
	
/*	@RequestMapping(value = "/users/save", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("users") User user) throws LogicException {
		userService.save(user);
	    return "redirect:/users/list";
	}	*/
	@RequestMapping(value = "/users/save", method = RequestMethod.POST)
	public String saveUser(HttpServletRequest req) throws LogicException {
		User user= getUser(req);
		userService.save(user);
	    return "redirect:/users/list";
	}

	@RequestMapping(value ="/users/delete", method = RequestMethod.POST)
	public String deleteUser(@RequestParam long id) throws LogicException {
		userService.delete(id);
	    return "redirect:/users/list";       
	}
	/*	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteCustomerForm(@RequestParam long []id) throws LogicException {
		for (long i:id) {userService.delete(i);}		
	    return "redirect:/";       
	}
	
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam String keyword) {
	    List<User> result = userService.search(keyword);
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users/search");	    
        modelAndView.addObject("result", result);	 
	    return modelAndView;    
	}
	@RequestMapping("/new")
	public String newCustomerForm(Map<String, Object> model) {
		User user = new User();
	    model.put("user", user);
	    return "users/new";
	}
	*/
/////////////////////////////////////////////////////////
	private User getUser(HttpServletRequest req) {
		   String sid; 
    	   try {sid = new String (req.getParameter("id"));}
    	   catch (NullPointerException e){sid ="";}
    	 
   		   Long id = null;
    	   try {id = Long.parseLong(sid);}
  		   catch (NullPointerException | NumberFormatException err) {id =null;}
    	   User user = new User();    	  
    	//   System.out.println("SAVING )" +user);
    	   String login=req.getParameter("login"); user.setLogin(login);
    	   if(login == null || login.isBlank()) {throw new IllegalArgumentException();}
    	   
    	   String name=req.getParameter("name"); user.setName(name);
    	   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}
    	   
    	   String password=req.getParameter("password"); user.setPassword(password);
    	   if(password == null || password.isBlank()) {throw new IllegalArgumentException();}
    	   
    	   String email=req.getParameter("email"); user.setEmail(email);
    	   if(email == null || email.isBlank()) {throw new IllegalArgumentException();}
    	   
    	   Integer roleid;	    	   
    	   try {roleid= Integer.parseInt(req.getParameter("role")); } 
    	   catch (NullPointerException | NumberFormatException err ) {roleid = null;}
    	   if(roleid == null || roleid<0) { user.setRole(Role.CLIENT); }
    	   else {user.setRole(Role.values()[roleid]);} 
    	 	   	    	   
    	   user.setId(id); 	    	       		    	   
			return user;
	}
}
