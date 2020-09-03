package web.action.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.CountrySevice;
import de_services.UserService;
import entities.Country;
import entities.User;
import service.LogicException;
import web.action.BaseAction;
//import web.action.Action.Result;

public class RegisterAction extends BaseAction {
	CountrySevice countrySevice;
	UserService userService;		
	public CountrySevice getCountrySevice() {
		return countrySevice;}
	public void setCountrySevice(CountrySevice countrySevice) {
		this.countrySevice = countrySevice;}
	public UserService getUserService() {
		return userService;}
	public void setUserService(UserService userService) {
		this.userService = userService;}

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		List<Country> countries = countrySevice.findAll();
		List<User> users = userService.findAll();
		req.setAttribute("countries", countries);
		req.setAttribute("users", users);
		return null;
	}	
}
