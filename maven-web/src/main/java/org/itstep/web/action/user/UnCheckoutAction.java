package org.itstep.web.action.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.de_services.OrdersService;
//import org.itstep.de_services.OrdersServiceImpl;
import org.itstep.entities.Client;
import org.itstep.entities.Delivery;
import org.itstep.entities.Orders;
import org.itstep.entities.Orderstatus;
import org.itstep.service.LogicException;
//import web.action.Action.Result;
//import web.action.Action;
import org.itstep.web.action.ActionException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UnCheckoutAction extends BaseAction {
	@Autowired	
	OrdersService ordersService;
	
	
@Override
public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
	 
	String sclid; Long cid = null;
	String address, phoneno;
	List<Long> idOrders=new ArrayList<>();
	List<Orders> orders=new ArrayList<>();
	String idsString[] = req.getParameterValues("ordersid");
//	System.out.println("===================CHECKOUT)" + req.getParameterValues("ordersid"));
	if(idsString != null) {
		try {
			idOrders = new ArrayList<>(idsString.length);
			for(String id : idsString) {
				idOrders.add(Long.parseLong(id));					
			}			
		} catch(NumberFormatException e) {
			throw new ActionException(e, 400);
		}			
	}		
	 
	for (Long x: idOrders) {
			
	  Orders order = new Orders(); 
	   order.setId(x);
	   try {sclid = new String (req.getParameter("clientid"));}
	   catch (NullPointerException e){throw new IllegalArgumentException();}		 
	   
//	   System.out.println("===================UNCHECKOUT client)" + sclid);
	   try {cid = Long.parseLong(sclid);}
	   catch (NumberFormatException err) {throw new IllegalArgumentException();}
	   Client client= new Client(); client.setId(cid);
	   
	   address=req.getParameter("address");
	   if(address != null && !address.isBlank()) {client.setAddress(address);}		   
	   phoneno=req.getParameter("phoneno"); 
	   if(phoneno != null && !phoneno.isBlank()) {client.setPhoneno(phoneno);}   
//	   System.out.println("===================CHECKOUT phoneno)" + phoneno);
	   order.setClient(client);
	   
	   Delivery dType;	   
	   Integer deliveryid;
	   try {deliveryid= Integer.parseInt(req.getParameter("delivery"));} 
	   catch (NullPointerException | NumberFormatException err ) {deliveryid = null;} 		  
	   if(deliveryid == null || deliveryid<0) { dType = Delivery.POST;}
	   else {dType = Delivery.values()[deliveryid];} 
	  
	   order.setDelivery(dType);
	   order.setActive(true);
	   order.setStatus(Orderstatus.BASKET);    	   
	   orders.add(order);		   		   
	}   
	//   System.out.println("===================CHECKOUT dType)");
	
	ordersService.orderListUpdate(orders, true);
	
	  return new Result("/users/cart");
}
}