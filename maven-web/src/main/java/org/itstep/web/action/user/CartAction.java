package org.itstep.web.action.user;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.itstep.de_services.ClientService;
import org.itstep.de_services.CurrencyService;
import org.itstep.de_services.ItemsService;
import org.itstep.de_services.OrdersService;
import org.itstep.entities.Currency;
import org.itstep.entities.Items;
import org.itstep.entities.Orders;
import org.itstep.entities.User;
import org.itstep.service.LogicException;
//import web.action.Action;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
//import web.action.Action.Result;
@Component
@Scope("prototype")
public class CartAction extends BaseAction {
	@Autowired		
	private ItemsService itemsService;
	@Autowired		
	private ClientService clientService;
	@Autowired		
	private CurrencyService currencyService;
	@Autowired	
	OrdersService ordersService;
	
/*	public ItemsService getItemsService() {return itemsService;}
	public void setItemsService(ItemsService itemsService) {this.itemsService = itemsService;}       	
	public ClientService getClientService() {return clientService;}
	public void setClientService(ClientService clientService) {this.clientService = clientService;}	
	public CurrencyService getCurrencyService() {return currencyService;}
	public void setCurrencyService(CurrencyService currencyService) {this.currencyService = currencyService;} */
	
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		   		 
		   HttpServletRequest request = (HttpServletRequest)req;		   
		   HttpSession session = request.getSession(false);
		   User user=null; 
			if(session != null) {
				 user = (User)session.getAttribute("sessionUser");
			}	
			 Long uid = user.getId();
			 Long id = clientService.readByUserId(uid, true);		
		List<Orders> list = ordersService.orderdItems(id);			
		for(Orders x: list) {
			Currency currency=currencyService.read(x.getCurrency().getId());
			Long itId = x.getBaseitem().getItem().getId();
			Items it = itemsService.read(itId);		
			it.setBaseprice((Long)Math.round(it.getBaseprice()*currency.getRate()));
			x.getBaseitem().setItem(it);
			x.getBaseitem().setBaseprice((Long)Math.round(x.getBaseitem().getBaseprice()*currency.getRate()));
		}			
		
		req.setAttribute("list", list);
		return null;
	}
}