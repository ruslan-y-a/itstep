package org.itstep.web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.de_services.UserService;
//import org.itstep.de_services.UserServiceImpl;
import org.itstep.web.action.ActionException;

import org.itstep.service.LogicException;
import org.itstep.tabs.Role;
//import entities.Orderstatus;
import org.itstep.entities.User;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class UserSaveAction extends BaseAction {
	@Autowired	
	UserService userService;
	
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		try {
	    	   
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
	    	   userService.save(user);	    	    
	    	//	System.out.println("-------------SAVED user:" + user);
				return new Result("/users/list");

		} catch(IllegalArgumentException e) {
			throw new ActionException(e, 400);
		}
	}

}
