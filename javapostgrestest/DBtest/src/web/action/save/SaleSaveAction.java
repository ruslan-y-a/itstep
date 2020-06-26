package web.action.save;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.OrdersService;
import de_services.SaleServiceImpl;
import entities.Client;
import entities.Currency;
import entities.Orders;
import entities.Orderstatus;
import entities.Sale;
import service.LogicException;
import web.action.ActionException;
import web.action.BaseAction;

public class SaleSaveAction extends BaseAction {
	private OrdersService ordersService;
			
public SaleSaveAction(OrdersService ordersService) {		
		this.ordersService = ordersService;	
	}
public SaleSaveAction() {			
}

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
 	    	   
 	    Sale sale = new Sale();    	  
 		Date datetime;
 		Long  lOrder;
 		
 		SimpleDateFormat format;
 		SaleServiceImpl saleServiceImpl= (SaleServiceImpl)getService();
 		 		  	   
  	 String sdatetime=req.getParameter("datetime"); 
	   if(sdatetime == null || sdatetime.isBlank()) {
		   datetime = new Date();
		   } else {	    			   
			   format = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");					   		
			   try { 
				   datetime= format.parse(sdatetime);
				   }
	    	   catch ( ParseException err) {	
	    		   err.printStackTrace(); System.out.println(err);
	    		   throw new IllegalArgumentException(); //   datetime = new Date();	   
	    		   }    		   	    	   
		   } 	   
	   sale.setDatetime(datetime); 	   
	   System.out.println("================datetime)"+datetime);
	   System.out.println("================sdatetime)"+sdatetime);
  	   
	   String sCurrency=req.getParameter("currency"); 		
	   Long lCurrency;
	   try {lCurrency = Long.parseLong(sCurrency);}
		   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}	
	   sale.setCurrency(new Currency()); sale.getCurrency().setId(lCurrency);
	   
	   Boolean returned;
	   try { returned= Boolean.parseBoolean(req.getParameter("returned"));}
	   catch (NullPointerException err) {returned =false;}
	   sale.setReturned(returned);  
	   
	   String sOrder=req.getParameter("orders"); 	  
	   try {lOrder = Long.parseLong(sOrder);}
		   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}	
	   //sale.setOrder(new Orders()); sale.getOrder().setId(lOrder);	 
	   Orders orders = ordersService.read(lOrder);
	   Client client =  orders.getClient();
	   if (!returned) {
		   orders.setStatus(Orderstatus.SOLD); sale.setOrder(orders);		  
		  int respoints =  Math.round(orders.getSum()/100-orders.getBonuspoints());
		  client.setBonuspoints((client.getBonuspoints() + (respoints>0?respoints:0)));
		  orders.setClient(client);
	   } else {
		   orders.setActive(false);
		   client.setBonuspoints(client.getBonuspoints() + orders.getBonuspoints());
		   orders.setClient(client);
	   }		   	      		   		    
	   
	   sale.setId(id); 	    	   	   	   
	   saleServiceImpl.save(sale);

	   return new Result("/sale/list");
	    } catch(IllegalArgumentException e) {
	    throw new ActionException(e, 400);
	     }
  }
}	
