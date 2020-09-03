package web.action.save;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.ClientService;
import de_services.CurrencyService;
import de_services.OrdersService;
import de_services.SaleServiceImpl;
import entities.Client;
import entities.Currency;
import entities.Delivery;
import entities.Orders;
import entities.Orderstatus;
import entities.Sale;
import help.Params;
import service.LogicException;
import web.action.ActionException;
import web.action.BaseAction;

public class SaleSaveAction extends BaseAction {
	private OrdersService ordersService;
	private CurrencyService currencyService;	 
	private ClientService clientService;
	public CurrencyService getCurrencyService() {return currencyService;}
	public void setCurrencyService(CurrencyService currencyService) {this.currencyService = currencyService;}
    public SaleSaveAction(OrdersService ordersService) {this.ordersService = ordersService;}    
    public ClientService getClientService() {return clientService;}
	public void setClientService(ClientService clientService) {this.clientService = clientService;}
	
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
//	   System.out.println("================datetime)"+datetime);
	//   System.out.println("================sdatetime)"+sdatetime);
  	   
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
	   Client client =  clientService.read(orders.getClient().getId());
	   Currency currency = orders.getCurrency();
	   orders.setActive(false);
	   if (!returned) {
		   Long sum; //Double tsum;
		   double krate = currencyService.read(currency.getId()).getRate();
		   orders.setStatus(Orderstatus.SOLD);
		   if (client.getBonuspoints()<orders.getBonuspoints()) {orders.setBonuspoints(client.getBonuspoints());}
		   sum= (long)(Math.round(orders.getSum()/krate));
	//	  System.out.println("================sum)" + sum);
		   orders.setSum(sum-orders.getBonuspoints());
		//   System.out.println("================sum -orders.getBonuspoints())" + orders.getSum());
		   Integer oCbp=(client.getBonuspoints()==null?0:client.getBonuspoints());
		 //  System.out.println("================oCbp)" + oCbp);
		   oCbp = oCbp-orders.getBonuspoints();
		   oCbp=(oCbp<0?0:oCbp);
		//   System.out.println("================oCbp)" + oCbp);
		     if (orders.getDelivery()==Delivery.PICKUP) {	      
				   sum= (long)(Math.round(sum * Params.PICKUP_DISCOUNT / 100)); orders.setSum(orders.getSum()-sum);}
		//     System.out.println("================sum - PICKUP)" + orders.getSum());
		     oCbp=(int) (oCbp+Math.round((orders.getSum()/krate)*Params.KOEF_BP/100));
		//     System.out.println("================(orders.getSum()/krate))" + (orders.getSum()/krate));
		//     System.out.println("================oCbp)" + oCbp);
		     client.setBonuspoints(oCbp);
		     		
		  orders.setClient(client);		
	   } else {		   
		   client.setBonuspoints(client.getBonuspoints() + orders.getBonuspoints());
		   orders.setClient(client);
	   }		   	      		   		    
	   sale.setOrder(orders);
	   sale.setId(id); 	    	   	   	   
	   saleServiceImpl.save(sale);

	   return new Result("/sale/list");
	    } catch(IllegalArgumentException e) {
	    throw new ActionException(e, 400);
	     }
  }
}	
