package org.itstep.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itstep.de_services.ClientServiceImpl;
import org.itstep.de_services.CurrencyServiceImpl;
import org.itstep.de_services.OrdersServiceImpl;
//import org.itstep.de_services.SaleService;
import org.itstep.de_services.SaleServiceImpl;
import org.itstep.entities.Client;
import org.itstep.entities.Currency;
import org.itstep.entities.Delivery;
import org.itstep.entities.Orders;
import org.itstep.entities.Orderstatus;
import org.itstep.entities.Sale;
import org.itstep.help.Params;
import org.itstep.service.LogicException;
//import org.itstep.service.SaleService;
//import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping(value = "/sale")
public class SaleController{
	@Autowired private SaleServiceImpl saleService;
	@Autowired private OrdersServiceImpl ordersService;
	@Autowired private ClientServiceImpl clientService;
	@Autowired private CurrencyServiceImpl currencyService;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Sale> listUser = saleService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sale/list");
        modelAndView.addObject("list", listUser);
	    return modelAndView;
	}
	
	@RequestMapping("/edit")
	public ModelAndView editSale(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView("sale/edit");
	    if (id!=null) {
	     Sale sale = saleService.findById(id);		   	    	 	   
	 	   
	     mav.addObject("entity", sale);
	    }
	    List<Orders> listOrders = ordersService.findAll();
	    List<Currency> listCurrency = currencyService.findAll();	    
	    mav.addObject("orders", listOrders);
	    mav.addObject("currency", listCurrency);
	    return mav;
	}
/*	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveSale(@ModelAttribute("sale") Sale sale) throws LogicException {
		saleService.save(sale);
	    return "redirect:/sale/list";
	} */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveSale(HttpServletRequest req) throws LogicException {
		Sale sale = getSale(req);
		//System.out.println(sale);
		saleService.save(sale);
	    return "redirect:/sale/list";
	}
	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteSale(@RequestParam long id) throws LogicException {
		saleService.delete(id);
	    return "redirect:/sale/list";       
	}
/*	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteSale(@RequestParam long []id) throws LogicException {
		for (long i:id) {saleService.delete(i);}		
	    return "redirect:/";       
	} */
////////////////////////////////////////////////////////////////
	private Sale getSale(HttpServletRequest req) throws LogicException {
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
//		   System.out.println("================datetime)"+datetime);	  	   
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
		//   System.out.println("=========================="+sOrder);
		   try {lOrder = Long.parseLong(sOrder);
		   if (lOrder==0) {
		         sOrder=req.getParameter("orders1"); 	 
		         lOrder = Long.parseLong(sOrder);
		      }
		   }
			   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}	
		   if (lOrder==0) {
			   sOrder=req.getParameter("orderid");
			//   System.out.println("=========================="+sOrder);
			    	 Long sOrderid = null;
			    	 try {sOrderid = Long.parseLong(sOrder);}
					   catch (IllegalArgumentException e2) {   throw new IllegalArgumentException();}
			    	 lOrder=sOrderid;			   
		   }
		   //sale.setOrder(new Orders()); sale.getOrder().setId(lOrder);	 
		   Orders orders = ordersService.findById(lOrder);
		   Client client =  clientService.findById(orders.getClient().getId());
		   Currency currency = orders.getCurrency();
		   orders.setActive(false);
		   if (!returned) {
			   Long sum; //Double tsum;
			   double krate = currencyService.findById(currency.getId()).getRate();
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
		   return sale;
	}
}
