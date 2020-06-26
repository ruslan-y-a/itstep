package web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.UserServiceImpl;
import web.action.ActionException;

import service.LogicException;
import tabs.Role;
//import entities.Orderstatus;
import entities.User;
import web.action.BaseAction;

public class UserSaveAction extends BaseAction {

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
	    	   
	    	   UserServiceImpl userService = (UserServiceImpl)getService(); 
	    		
	    	   userService.save(user);	    	    
	    	//	System.out.println("-------------SAVED user:" + user);
				return new Result("/users/list");

		} catch(IllegalArgumentException e) {
			throw new ActionException(e, 400);
		}
	}

}
