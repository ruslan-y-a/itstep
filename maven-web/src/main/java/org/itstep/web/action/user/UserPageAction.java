package org.itstep.web.action.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.itstep.de_services.ClientService;
import org.itstep.de_services.CurrencyService;
import org.itstep.de_services.OrdersService;
import org.itstep.de_services.UserService;
//import org.itstep.de_services.UserServiceImpl;
import org.itstep.entities.Client;
import org.itstep.entities.Currency;
//import entities.Items;
import org.itstep.entities.Orders;
import org.itstep.entities.User;
import org.itstep.service.LogicException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
//import web.action.Action.Result;
@Component
@Scope("prototype")
public class UserPageAction extends BaseAction {
	@Autowired	
	private OrdersService ordersService;
	@Autowired	
	private ClientService clientService;
	@Autowired	
	private CurrencyService currencyService;
	@Autowired	
	UserService userService;
/*	public OrdersService getOrdersService() {return ordersService;}
	public void setOrdersService(OrdersService ordersService) {this.ordersService = ordersService;}   	
	public ClientService getClientService() {return clientService;}
	public void setClientService(ClientService clientService) {this.clientService = clientService;}
	public CurrencyService getCurrencyService() {return currencyService;}
	public void setCurrencyService(CurrencyService currencyService) {this.currencyService = currencyService;}  */
	
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		   
		 
		   HttpServletRequest request = (HttpServletRequest)req;		   
		   HttpSession session = request.getSession(false);
		   User user=null; 
			if(session != null) {
				 user = (User)session.getAttribute("sessionUser");
			}	
			 Long uid = user.getId();			 
			 //System.out.println("----------------------- clientService uid)" + clientService + " " + uid);
			 Long id = clientService.readByUserId(uid,true);
			 Client client =clientService.read(id);			 
	
			 user = userService.read(uid);
			 
			 List<Orders> orders = ordersService.allOrderedItems(id);
				for(Orders x: orders) {
					Currency currency=currencyService.read(x.getCurrency().getId());
                     x.setSum((Long)Math.round(x.getSum()*currency.getRate()));
				}	
			 
			 
			    req.setAttribute("user", user);
				req.setAttribute("client", client);
				req.setAttribute("orders", orders);
				return null;
			}
		}