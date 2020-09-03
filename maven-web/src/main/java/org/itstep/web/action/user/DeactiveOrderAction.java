package org.itstep.web.action.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.itstep.de_services.ClientService;
import org.itstep.de_services.OrdersService;
//import org.itstep.de_services.OrdersServiceImpl;
import org.itstep.entities.Client;
import org.itstep.entities.Delivery;
import org.itstep.entities.Orders;
import org.itstep.entities.Orderstatus;
import org.itstep.entities.User;
import org.itstep.service.LogicException;
import org.itstep.web.action.ActionException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DeactiveOrderAction extends BaseAction {
	@Autowired		
	ClientService clientService;
	@Autowired
	OrdersService ordersService;
	
/*	public ClientService getClientService() {return clientService;}
	public void setClientService(ClientService clientService) {this.clientService = clientService;} */
	
@Override
public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
	 
	// System.out.println("================!!!!!!!!!!!!!!!!!!!!!!)" );
	
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
			//	 System.out.println("================orders id)" + id);
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
	// System.out.println("================3)" + 3);
	ordersService.orderListUpdate(orders, false);
	
	return new Result("/users/cart");
}
}