package web.action.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de_services.ClientService;
import de_services.CurrencyService;
import de_services.OrdersService;
import de_services.UserServiceImpl;
import entities.Client;
import entities.Currency;
//import entities.Items;
import entities.Orders;
import entities.User;
import service.LogicException;
import web.action.BaseAction;
//import web.action.Action.Result;

public class UserPageAction extends BaseAction {
	private OrdersService ordersService;	
	private ClientService clientService;
	private CurrencyService currencyService;
	public OrdersService getOrdersService() {return ordersService;}
	public void setOrdersService(OrdersService ordersService) {this.ordersService = ordersService;}   	
	public ClientService getClientService() {return clientService;}
	public void setClientService(ClientService clientService) {this.clientService = clientService;}
	public CurrencyService getCurrencyService() {return currencyService;}
	public void setCurrencyService(CurrencyService currencyService) {this.currencyService = currencyService;}
	
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
			 
			 UserServiceImpl userService = (UserServiceImpl)getService();
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