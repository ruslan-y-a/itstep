package web.action.user;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de_services.ClientService;
import de_services.CurrencyService;
import de_services.ItemsService;
import de_services.OrdersService;
import entities.Currency;
import entities.Items;
import entities.Orders;
import entities.User;
import service.LogicException;
//import web.action.Action;
import web.action.BaseAction;
//import web.action.Action.Result;

public class CartAction extends BaseAction {
	private ItemsService itemsService;
	private ClientService clientService;
	private CurrencyService currencyService;
	public ItemsService getItemsService() {
		return itemsService;}
	public void setItemsService(ItemsService itemsService) {
		this.itemsService = itemsService;}       	
	public ClientService getClientService() {
		return clientService;}
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;}
	
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
			 Long id = clientService.readByUserId(uid, true);
		OrdersService ordersService = (OrdersService) getService();		
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