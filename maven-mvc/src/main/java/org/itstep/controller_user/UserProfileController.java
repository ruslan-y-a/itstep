package org.itstep.controller_user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.itstep.de_services.ClientServiceImpl;
import org.itstep.de_services.CurrencyServiceImpl;
import org.itstep.de_services.ItemsServiceImpl;
import org.itstep.de_services.OrdersServiceImpl;
import org.itstep.de_services.UserServiceImpl;
import org.itstep.entities.Client;
import org.itstep.entities.Currency;
import org.itstep.entities.Delivery;
import org.itstep.entities.Items;
import org.itstep.entities.Orders;
import org.itstep.entities.Orderstatus;
import org.itstep.entities.User;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
public class UserProfileController {
	@Autowired private UserServiceImpl userService;
	@Autowired private ClientServiceImpl clientService;
	@Autowired private OrdersServiceImpl ordersService;
	@Autowired private CurrencyServiceImpl currencyService;
	@Autowired private ItemsServiceImpl itemsService;
	
	@RequestMapping(value = {"/users/page" }, method = RequestMethod.GET)
	public ModelAndView userpage(HttpServletRequest req) throws LogicException {
	         	   
		   HttpServletRequest request = (HttpServletRequest)req;		   
		   HttpSession session = request.getSession(false);
		   User user=null; 
			if(session != null) { user = (User)session.getAttribute("sessionUser");}	
			 Long uid = user.getId();			 		
			 Long id = clientService.readByUserId(uid,true);
			 Client client =clientService.findById(id);			 	
			 user = userService.findById(uid);			
			 List<Orders> orders = ordersService.allOrderedItems(id);
				for(Orders x: orders) {
					Currency currency=currencyService.findById(x.getCurrency().getId());
                     x.setSum((Long)Math.round(x.getSum()*currency.getRate()));
				}	
			 	
				ModelAndView mv = new ModelAndView();				
				mv.setViewName("users/page");
				mv.addObject("user", user);
				mv.addObject("client", client);
				mv.addObject("orders", orders);
			    return mv;				
	 }	    	    
////////////////////////////////////////////
	   @RequestMapping(value = { "/users/cart" }, method = RequestMethod.GET)
	    public ModelAndView usercart(HttpServletRequest req) throws LogicException {
		   
		   HttpServletRequest request = (HttpServletRequest)req;		   
		   HttpSession session = request.getSession(false);
		   User user=null; 
			if(session != null) { user = (User)session.getAttribute("sessionUser");}	
			 Long uid = user.getId();
			 Long id = clientService.readByUserId(uid, true);		
		   List<Orders> list = ordersService.orderdItems(id);			
		   for(Orders x: list) {
			  Currency currency=currencyService.findById(x.getCurrency().getId());
			  Long itId = x.getBaseitem().getItem().getId();
			  Items it = itemsService.findById(itId);		
			  it.setBaseprice((Long)Math.round(it.getBaseprice()*currency.getRate()));
			  x.getBaseitem().setItem(it);
			  x.getBaseitem().setBaseprice((Long)Math.round(x.getBaseitem().getBaseprice()*currency.getRate()));
		   }			
							   		  
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.addObject("list", list);
	        modelAndView.setViewName("users/cart");
	        return modelAndView;
	    }
////////////////////////////////////////////	
	   @RequestMapping(value = { "/users/checkout" }, method = RequestMethod.POST)
	    public String userscheckout(Model model, HttpServletRequest req) throws LogicException {
		   
		    String sclid; Long cid = null;
			String address, phoneno;
			List<Long> idOrders=new ArrayList<>();
			List<Orders> orders=new ArrayList<>();
			String idsString[] = req.getParameterValues("ordersid");
			if(idsString != null) {
				try {
					idOrders = new ArrayList<>(idsString.length);
					for(String id : idsString) {idOrders.add(Long.parseLong(id));}			
				} catch(NumberFormatException e) {
					throw new LogicException(e);
				}			
			}		
			 
			for (Long x: idOrders) {
					
			   Orders order = new Orders(); 
			   order.setId(x);
			   try {sclid = new String (req.getParameter("clientid"));}
			   catch (NullPointerException e){throw new IllegalArgumentException();}		 

			   try {cid = Long.parseLong(sclid);}
			   catch (NumberFormatException err) {throw new IllegalArgumentException();}
			   Client client= new Client(); client.setId(cid);
			   
			   address=req.getParameter("address");
	    	   if(address != null && !address.isBlank()) {client.setAddress(address);}		   
			   phoneno=req.getParameter("phoneno"); 
	    	   if(phoneno != null && !phoneno.isBlank()) {client.setPhoneno(phoneno);}   
	    
	    	   order.setClient(client);
	    	   
			   Delivery dType;	   
			   Integer deliveryid;
			   try {deliveryid= Integer.parseInt(req.getParameter("delivery"));} 
			   catch (NullPointerException | NumberFormatException err ) {deliveryid = null;} 		  
			   if(deliveryid == null || deliveryid<0) { dType = Delivery.POST;}
			   else {dType = Delivery.values()[deliveryid];} 
			   order.setActive(true);
			   order.setDelivery(dType);
			   order.setStatus(Orderstatus.ORDER);    	   
			   orders.add(order);		   		   
			}   
		
			ordersService.orderListUpdate(orders, false);	       
//	        model.addAttribute("priorities", Priority.values());
			return "redirect:/users/order";	       
	    }
///////////////////////////////////////////////////////////////////////////////
	   @RequestMapping(value = { "/users/order" }, method = RequestMethod.GET)
	    public ModelAndView userorder(HttpServletRequest req) throws LogicException {
		   
		   HttpServletRequest request = (HttpServletRequest)req;		   
		   HttpSession session = request.getSession(false);
		   User user=null; 
			if(session != null) { user = (User)session.getAttribute("sessionUser");}	
			 Long uid = user.getId();
			 Long id = clientService.readByUserId(uid, true);		
		
		    List<Orders> list = ordersService.search(Orderstatus.ORDER, id);			
		    for(Orders x: list) {
 			   Long itId = x.getBaseitem().getItem().getId();
			   Items it = itemsService.findById(itId);		
			   x.getBaseitem().setItem(it);			
		     }			
										   		  
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.addObject("list", list);
	        modelAndView.setViewName("users/order");
	        return modelAndView;
	    }
////////////////////////////////////////////	
	   @RequestMapping(value = { "/users/uncheckout" }, method = RequestMethod.POST)
	    public String usersuncheckout(Model model, HttpServletRequest req) throws LogicException {

		    String sclid; Long cid = null;
			String address, phoneno;
			List<Long> idOrders=new ArrayList<>();
			List<Orders> orders=new ArrayList<>();
			String idsString[] = req.getParameterValues("ordersid");
//			System.out.println("===================CHECKOUT)" + req.getParameterValues("ordersid"));
			if(idsString != null) {
				try {
					idOrders = new ArrayList<>(idsString.length);
					for(String id : idsString) {
						idOrders.add(Long.parseLong(id));					
					}			
				} catch(NumberFormatException e) {
					throw new LogicException(e);
				}			
			}		
			 
			for (Long x: idOrders) {
					
			  Orders order = new Orders(); 
			   order.setId(x);
			   try {sclid = new String (req.getParameter("clientid"));}
			   catch (NullPointerException e){throw new IllegalArgumentException();}		 
			   
//			   System.out.println("===================UNCHECKOUT client)" + sclid);
			   try {cid = Long.parseLong(sclid);}
			   catch (NumberFormatException err) {throw new IllegalArgumentException();}
			   Client client= new Client(); client.setId(cid);
			   
			   address=req.getParameter("address");
			   if(address != null && !address.isBlank()) {client.setAddress(address);}		   
			   phoneno=req.getParameter("phoneno"); 
			   if(phoneno != null && !phoneno.isBlank()) {client.setPhoneno(phoneno);}   
//			   System.out.println("===================CHECKOUT phoneno)" + phoneno);
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
		   
			return "redirect:/users/cart";	
	   }
/////////////////////////////////////////////////////////////	   
	   @RequestMapping(value = { "/users/deactiveorder" })
	    public String usersdeactiveorder(Model model, HttpServletRequest req) throws LogicException {
		   
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
		   String idsString[] = req.getParameterValues("id");	//order
		   if(idsString != null) {			
				idOrders = new ArrayList<>(idsString.length);
				for(String id : idsString) {
					try {
					  idOrders.add(Long.parseLong(id));
					} catch(NumberFormatException e) {throw new LogicException(e);}						
			 }			
		  }				 
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
		   
				return "redirect:/users/cart";	
		}	   
//////////////////////////////////////////////////////////	   
}
