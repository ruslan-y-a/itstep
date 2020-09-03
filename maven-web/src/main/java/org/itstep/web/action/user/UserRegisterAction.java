package org.itstep.web.action.user;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSession;

import org.itstep.de_services.ClientService;
import org.itstep.de_services.UserService;
//import org.itstep.de_services.UserServiceImpl;
import org.itstep.entities.Client;
import org.itstep.entities.Country;
import org.itstep.entities.User;
import org.itstep.service.LogicException;
import org.itstep.tabs.Role;
import org.itstep.web.action.ActionException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UserRegisterAction extends BaseAction {
	@Autowired
	ClientService clientService;
	@Autowired
	UserService userService;
/*	public ClientService getClientService() {return clientService;}
    public void setClientService(ClientService clientService) {this.clientService = clientService;}
    public void setUserService(UserService userService) {this.userService = userService;} */

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
	try {	
				
		User user = new User();   
		user.setRole(Role.CLIENT); 
	//	System.out.println("-----------------------UserRegisterAction)");
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
	   
	   String phoneno=req.getParameter("phoneno"); 
	   if(phoneno == null || phoneno.isBlank()) {throw new IllegalArgumentException();}
	   client.setPhoneno(phoneno);	   
  	  
		Long nuid=userService.save(user);  user.setId(nuid);
//		System.out.println("-----------------------UserRegisterAction user)"+ user);  	  	    	    
  	   client.setUser(user);
  //	   System.out.println("-----------------------UserRegisterAction nuid)"+ nuid);
  	   clientService.save(client);
  	 
  	     user = userService.authenticate(login, password);			
		if(user != null) {		
			HttpSession session = req.getSession();
			session.setAttribute("sessionUser", user);			
		} else {
			throw new ActionException(null, 400);
		}
  	   
  	   
	 	return new Result("/users/page");
      } catch(IllegalArgumentException e) {
	throw new ActionException(e, 400);
    }
  }

}