package web.action.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de_services.ClientService;
import de_services.OrdersServiceImpl;
import entities.Client;
import entities.Delivery;
import entities.Orders;
import entities.Orderstatus;
import entities.User;
import service.LogicException;
import web.action.ActionException;
import web.action.BaseAction;
//import web.action.Action.Result;
//import web.action.Action.Result;

public class DeactiveOrderAction extends BaseAction {
	ClientService clientService;
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
		 Long cid = clientService.readByUserId(uid, true);
	
	String address, phoneno;
	List<Long> idOrders=new ArrayList<>();
	List<Orders> orders=new ArrayList<>();	
	String idsString[] = req.getParameterValues("orderid");
	
	if(idsString != null) {
		
			idOrders = new ArrayList<>(idsString.length);
			for(String id : idsString) {
				try {
				  idOrders.add(Long.parseLong(id));
				} catch(NumberFormatException e) {
					throw new ActionException(e, 400);
			}			
		
		}			
	}		
	 
	/*
	try {sclid = new String (req.getParameter("clientid"));}
	   catch (NullPointerException e){throw new IllegalArgumentException();}		 	   
	   try {cid = Long.parseLong(sclid);}
	   catch (NumberFormatException err) {throw new IllegalArgumentException();}
	  */
	 Client client= new Client(); client.setId(cid);	
	 
	for (Long x: idOrders) {
			
	   Orders order = new Orders(); 
	   order.setId(x);	   	   
	   
	   address=req.getParameter("address");
	   if(address != null && !address.isBlank()) {client.setAddress(address);}		   
	   phoneno=req.getParameter("phoneno"); 
	   if(phoneno != null && !phoneno.isBlank()) {client.setPhoneno(phoneno);}   	  
	  	   
	   Delivery dType;	   
	   Integer deliveryid;
	   try {deliveryid= Integer.parseInt(req.getParameter("delivery"));} 
	   catch (NullPointerException | NumberFormatException err ) {deliveryid = null;} 		  
	   if(deliveryid == null || deliveryid<0) { dType = Delivery.POST;}
	   else {dType = Delivery.values()[deliveryid];} 
	  	   
	   order.setClient(client);
	   order.setDelivery(dType);
	   order.setStatus(Orderstatus.ORDER);
	   order.setActive(false);
	   orders.add(order);		   	
	  
	}   
//	 System.out.println("================3)" + 3);
	OrdersServiceImpl ordersServiceImpl= (OrdersServiceImpl)getService();
	ordersServiceImpl.orderListUpdate(orders, false);
	 //System.out.println("================3)" + 3);
	return new Result("/users/cart");
}
}