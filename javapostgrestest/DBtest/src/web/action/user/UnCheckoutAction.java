package web.action.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.OrdersServiceImpl;
import entities.Client;
import entities.Delivery;
import entities.Orders;
import entities.Orderstatus;
import service.LogicException;
//import web.action.Action.Result;
//import web.action.Action;
import web.action.ActionException;
import web.action.BaseAction;
//import web.action.Action.Result;

public class UnCheckoutAction extends BaseAction {
	
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
	OrdersServiceImpl ordersServiceImpl= (OrdersServiceImpl)getService();
	ordersServiceImpl.orderListUpdate(orders, true);
	
	  return new Result("/users/cart");
}
}