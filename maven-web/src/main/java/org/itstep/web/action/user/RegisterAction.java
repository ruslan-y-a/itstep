package org.itstep.web.action.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.de_services.CountrySevice;
import org.itstep.de_services.UserService;
import org.itstep.entities.Country;
import org.itstep.entities.User;
import org.itstep.service.LogicException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
//import web.action.Action.Result;
@Component
@Scope("prototype")
public class RegisterAction extends BaseAction {
	@Autowired	
	CountrySevice countrySevice;
	@Autowired	
	UserService userService;		
	
/*	public CountrySevice getCountrySevice() {return countrySevice;}
	public void setCountrySevice(CountrySevice countrySevice) {this.countrySevice = countrySevice;}
	public UserService getUserService() {return userService;}
	public void setUserService(UserService userService) {this.userService = userService;} */

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		List<Country> countries = countrySevice.findAll();
		List<User> users = userService.findAll();
		req.setAttribute("countries", countries);
		req.setAttribute("users", users);
		return null;
	}	
}
