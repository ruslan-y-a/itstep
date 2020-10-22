package org.itstep.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.itstep.de_services.BaseitemServiceImpl;
import org.itstep.de_services.ClientServiceImpl;
import org.itstep.de_services.CurrencyServiceImpl;
//import org.itstep.de_services.OrdersService;
import org.itstep.de_services.OrdersServiceImpl;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Client;
import org.itstep.entities.Currency;
import org.itstep.entities.Delivery;
import org.itstep.entities.Orders;
import org.itstep.entities.Orderstatus;
import org.itstep.help.DateHelp;
import org.itstep.service.LogicException;
//import org.itstep.service.OrdersService;
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
@RequestMapping(value = "/orders")
public class OrdersController {
	@Autowired
    private OrdersServiceImpl ordersService;	
	@Autowired private BaseitemServiceImpl baseitemService;
	@Autowired private ClientServiceImpl clientService;
	@Autowired private CurrencyServiceImpl currencyService;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Orders> list = ordersService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orders/list");
        modelAndView.addObject("list", list);
	    return modelAndView;
	}
	
	@RequestMapping("/edit")
	public ModelAndView editOrders(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView("orders/edit");
	    if (id!=null) {
	     Orders orders = ordersService.findById(id);
	     mav.addObject("entity", orders);
	    }
	    List<Baseitem> list = baseitemService.findAll();
	    List<Client> listC = clientService.findAll();
	    List<Currency> listCur = currencyService.findAll();	    	 
	    mav.addObject("baseitem", list);
	    mav.addObject("client", listC);
	    mav.addObject("currency", listCur);
	    return mav;
	}
	/*
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrders(@ModelAttribute("orders") Orders orders) throws LogicException {
		ordersService.save(orders);
	    return "redirect:/";
	} */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveOrders(HttpServletRequest req) throws LogicException {
		Orders orders = getOrders(req);
		ordersService.save(orders);
	    return "redirect:/orders/list";
	}
	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteOrders(@RequestParam long id) throws LogicException {
		ordersService.delete(id);
	    return "redirect:/orders/list";       
	}
	/*@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteOrders(@RequestParam long []id) throws LogicException {
		for (long i:id) {ordersService.delete(i);}		
	    return "redirect:/";       
	}

	@RequestMapping("/search")
	public ModelAndView search(@RequestParam String keyword) {
	    List<Currency> result = currencyService.search(keyword);
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("country/search");	    
        modelAndView.addObject("result", result);	 
	    return modelAndView;    
	}	
	@RequestMapping("/new")
	public String newCustomerForm(Map<String, Object> model) {
		Currency country = new Currency();
	    model.put("country", country);
	    return "country/new";
	}	
	*/
/////////////////////////////////////////////////////
	private Orders getOrders(HttpServletRequest req) throws LogicException {
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
//		   System.out.println("=================>sdateexpired)"+sdateexpired);
		   
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
		   
		   if (orders.getStatus()==Orderstatus.SOLD) {System.out.println("ATTENTION! Orderstatus.SOLD! id: "+ id + " WRONG BEHAVIOR!");}
		   	 	   
		   orders.setId(id); 	    	   	   	    
		   return orders;
	}
}
