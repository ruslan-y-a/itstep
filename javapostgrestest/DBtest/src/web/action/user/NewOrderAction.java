package web.action.user;


//import java.io.IOException;
//import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de_services.ClientService;
//import de_services.ItemsService;
import de_services.OrdersService;
import entities.Delivery;
//import entities.Client;
import entities.User;
import service.LogicException;
import web.action.BaseAction;
//import web.action.Action.Result;


public class NewOrderAction extends BaseAction{
	ClientService clientService;
	public ClientService getClientService() {
		return clientService;}
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;}    
	
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		   
		
		   HttpServletRequest request = (HttpServletRequest)req;	
	//	   HttpServletResponse response = (HttpServletResponse)resp;
		   
		   HttpSession session = request.getSession(false);
		   User user=null; 
			if(session != null) {
				 user = (User)session.getAttribute("sessionUser");
			} 
			/*if (session==null || user ==null) {
				try {
					response.sendRedirect(request.getContextPath() + "/login.html?message=" + URLEncoder.encode("Login First!", "UTF-8"));
				} catch (IOException e) {e.printStackTrace();}
			}*/
			Long uid=null;
			 if (user!=null) {uid = user.getId();}			
			
			 OrdersService ordersService = (OrdersService) getService();
			 
   			   String sid; 
	    	   try {sid = new String (req.getParameter("baseitem"));}
	    	   catch (NullPointerException e){throw new IllegalArgumentException();}
	    	 
	   		   Long id = null;
	    	   try {id = Long.parseLong(sid);}
	  		   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}
	    		  
	    	   String susebonuspoints=null; Boolean useBonus=false;
	    	   try {susebonuspoints = new String (req.getParameter("usebonuspoints"));}
	    	   catch (NullPointerException e){;}
	    	   try {useBonus = Boolean.parseBoolean(susebonuspoints);}
	    	   catch (NullPointerException e) {}
	    	   	    	
	    	   String scurrency=null; 
	    	   try {scurrency = new String (req.getParameter("ordercurrency"));}
	    	   catch (NullPointerException e){}
	    	   Long currency = 1L;
	    	   try {currency = Long.parseLong(scurrency);}
	  		   catch (NullPointerException | NumberFormatException err) {}
	    //	   System.out.println("======---===---==scurrency)"+scurrency);
   			   String squantity=null; 
	    	   try {squantity = new String (req.getParameter("quantity"));}
	    	   catch (NullPointerException e){}
	    	 
	   		   Integer quantity = 1;
	    	   try {quantity = Integer.parseInt(squantity);}
	  		   catch (NullPointerException | NumberFormatException err) {}	    	   	    	  
	    	   
	    	   
	    	   String sdelivery=null; 
	    	   try {sdelivery = new String (req.getParameter("delivery"));}
	    	   catch (NullPointerException e){}
	    	   Integer idelivery = 1;
	    	   try {idelivery = Integer.parseInt(sdelivery);}
	  		   catch (NullPointerException | NumberFormatException err) {}	  
	    	 
	    	   Delivery delivery = Delivery.values()[idelivery];
	    	  
	    	   
	    	   ordersService.newOrder(id, uid, useBonus, quantity, currency, delivery);	       	 		
		//
	    	   return new Result("/users/cart");
	}	
}
