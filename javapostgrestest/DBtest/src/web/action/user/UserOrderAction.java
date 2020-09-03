package web.action.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de_services.ItemsService;
import de_services.OrdersService;
import de_services.ClientService;
import entities.Items;
import entities.Orders;
import entities.Orderstatus;
import entities.User;
import service.LogicException;
//import web.action.Action.Result;
//import web.action.Action;
import web.action.BaseAction;
//import web.action.Action.Result;

public class UserOrderAction extends BaseAction {
	ItemsService itemsService;
	ClientService clientService;
	public ItemsService getItemsService() {
		return itemsService;}
	public void setItemsService(ItemsService itemsService) {
		this.itemsService = itemsService;}    		
	public ClientService getClientService() {
		return clientService;}
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;}
	
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
		
		List<Orders> list = ordersService.search(Orderstatus.ORDER, id);			
		for(Orders x: list) {
			Long itId = x.getBaseitem().getItem().getId();
			Items it = itemsService.read(itId);		
			x.getBaseitem().setItem(it);			
		}			
		
		req.setAttribute("list", list);
		return null;
	}
}