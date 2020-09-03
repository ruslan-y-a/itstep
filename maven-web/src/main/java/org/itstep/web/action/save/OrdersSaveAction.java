package org.itstep.web.action.save;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.de_services.ClientService;
import org.itstep.de_services.OrdersService;
//import de_services.CurrencyService;
//import org.itstep.de_services.OrdersServiceImpl;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Client;
import org.itstep.entities.Currency;
import org.itstep.entities.Delivery;
import org.itstep.entities.Orders;
import org.itstep.entities.Orderstatus;
import org.itstep.help.DateHelp;
//import help.Params;
import org.itstep.service.LogicException;
import org.itstep.web.action.ActionException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class OrdersSaveAction extends BaseAction {
	@Autowired			
	ClientService clientService;
	@Autowired			
	OrdersService ordersService;

/*    public ClientService getClientService() {return clientService;}
	public void setClientService(ClientService clientService) {this.clientService = clientService;} */

@Override
public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
	try {
		  
		////////////////////////////////////////
		String sid; 
 	   try {sid = new String (req.getParameter("id"));}
 	   catch (NullPointerException e){sid ="";}
 	  
		   Long id = null;
 	   try {id = Long.parseLong(sid);}
		   catch (IllegalArgumentException err) {}
 	    	   
 	    Orders orders = new Orders();    	  

 		Integer number;
 		Date datetime;
 		Date dateexpired;
 		Long baseitem;
 		Long client;
 		Integer quantity;
 	//	Long sum;
 		Long currency; 		
 		boolean active; 		
 //		 System.out.println("=================>1)");
 		SimpleDateFormat format; 		
        Integer maxNumber = ordersService.getMaxNumber();
 //       System.out.println("=================>2)");	 		
 		try {number= Integer.parseInt(req.getParameter("number"));} 
 		catch (NumberFormatException  | NullPointerException e) {number = null;} 
  	   if(number == null) {number=maxNumber;}
  	   else if (id==null && number<=maxNumber) {number=maxNumber;}  	   
  	   orders.setNumber(number); 
  //	 System.out.println("=================>3)");
  	 String sdatetime=req.getParameter("datetime");  	 
	   if(sdatetime == null || sdatetime.isBlank()) {		   
		   orders.setDatetime(new Date());
		   } else {
			   format = new SimpleDateFormat();
	    	   format.applyPattern("yyyy-MM-dd, HH:mm:ss");
	    	   try { datetime= format.parse(sdatetime);}
	    	   catch ( ParseException err) {throw new IllegalArgumentException();}
	    	   orders.setDatetime(datetime);
		   } 	   
	//  System.out.println("=================>sdatetime)"+orders.getDatetime());
	//   System.out.println("=================>datetime)"+orders.getDatetime());
	    	   	  
	   String sdateexpired=req.getParameter("dateexpired");
	   if(sdateexpired == null || sdateexpired.isBlank()) {
		   dateexpired = DateHelp.getFuture(new Date(), 3);
		   } else {	    			   
			   format = new SimpleDateFormat();
	    	   format.applyPattern("yyyy-MM-dd");
			   try { dateexpired= format.parse(sdateexpired);}
	    	   catch ( ParseException err) {
	    		   format.applyPattern("dd-MM-yyyy");
	    		   try { dateexpired= format.parse(sdateexpired);}
	    		   catch ( ParseException err1) {
	    			   throw new IllegalArgumentException();}    		   
	    	   }; 
		   } 	   
//	   System.out.println("=================>sdateexpired)"+sdateexpired);
//	   System.out.println("=================>dateexpired)"+dateexpired);
	   
	   orders.setDateexpired(dateexpired); 	     	  
	   String sbaseitem=req.getParameter("baseitem"); 	  
	   try {baseitem = Long.parseLong(sbaseitem);}
		   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}	
	   orders.setBaseitem(new Baseitem()); orders.getBaseitem().setId(baseitem);	 	     
	//  System.out.println("=================>1)");
	   String sclient=req.getParameter("client"); 	  
	   try {client = Long.parseLong(sclient);}
		   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}	
	   orders.setClient(new Client()); orders.getClient().setId(client);	 	   
	//   System.out.println("=================>2)");
	   quantity= Integer.parseInt(req.getParameter("quantity")); 
  	   if(quantity == null || quantity<=0) {throw new IllegalArgumentException();}
  	   orders.setQuantity(quantity);   	 

  	   /*
  	   Long price;
  	   price= Long.parseLong(req.getParameter("price")); 
	   if(price == null || price<=0) {throw new IllegalArgumentException();}
 
	   Integer discount;
	   try {discount= Integer.parseInt(req.getParameter("discount"));}
	   catch (NullPointerException | NumberFormatException err) {discount=0;}	    
	   if(discount == null || discount<=0) {discount=0;}	*/    	   
  //	 System.out.println("=================>3)");
	   Integer bonuspoints=0;	
	   String sbonuspoints=req.getParameter("bonuspoints");	   
	   try {bonuspoints= Integer.parseInt(sbonuspoints);} catch (NullPointerException | NumberFormatException e) {}		  	    	   	   
	  // System.out.println("=================>4)");
	   Double tsum;
	   tsum= Double.parseDouble(req.getParameter("totalsum"));	   
	   if(tsum == null || tsum<=0) {throw new IllegalArgumentException();} 		   	   
	   orders.setSum(Math.round(tsum));
	   orders.setBonuspoints(bonuspoints);	   		   		    	     	   
	   
	   String sCurrency=req.getParameter("currency"); 		   
	   try {currency = Long.parseLong(sCurrency);}
		   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}	
	   orders.setCurrency(new Currency()); orders.getCurrency().setId(currency);
	//   System.out.println("=================>2)");    	   
	   try { active= Boolean.parseBoolean(req.getParameter("active"));}
	   catch (NullPointerException err) {active =false;}
	   orders.setActive(active);  
	   
	   Integer statusid;
	   try {statusid= Integer.parseInt(req.getParameter("status")); } 
	   catch (NullPointerException | NumberFormatException err ) {statusid = null;}
	   if(statusid == null || statusid<0) {orders.setStatus(Orderstatus.BASKET); }
	   else {orders.setStatus(Orderstatus.values()[statusid]);} 	   	   
	   
	   Integer deliveryid;
	   try {deliveryid= Integer.parseInt(req.getParameter("delivery"));} 
	   catch (NullPointerException | NumberFormatException err ) {deliveryid = null;} 
	   if(deliveryid == null || deliveryid<0) { orders.setDelivery(Delivery.POST);}
	   else {orders.setDelivery(Delivery.values()[deliveryid]);} 
	   
	   if (orders.getStatus()==Orderstatus.SOLD) {
	     System.out.println("ATTENTION! WRONG BEHAVIOR!");	      	      
	   }
	   	 	   
	   orders.setId(id); 	    	   	   	   
	   ordersService.save(orders);	   
	   return new Result("/orders/list");
	    } catch(IllegalArgumentException e) {
	    throw new ActionException(e, 400);
	     }
  }
}	
