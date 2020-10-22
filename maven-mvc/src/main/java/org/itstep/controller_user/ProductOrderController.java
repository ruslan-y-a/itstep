package org.itstep.controller_user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.itstep.de_services.BaseitemService;
import org.itstep.de_services.ClientService;
//import org.itstep.de_services.CurrencyService;
import org.itstep.service.CurrencyService;
import org.itstep.de_services.ItemsService;
import org.itstep.de_services.OrdersService;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Client;
import org.itstep.entities.Currency;
import org.itstep.entities.Delivery;
import org.itstep.entities.Items;
import org.itstep.entities.User;
import org.itstep.entities.Webpages;
import org.itstep.service.LogicException;
import org.itstep.service.WebpagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
public class ProductOrderController {
	@Autowired OrdersService ordersService;
	@Autowired BaseitemService baseitemService;		
	@Autowired ClientService clientService;
	@Autowired CurrencyService currencyService;
	@Autowired ItemsService itemsService;
	@Autowired WebpagesService webpagesService;
	
	   @RequestMapping(value = { "/product/order" }, method = RequestMethod.POST)
	    public String userscheckout(Model model, HttpServletRequest req) throws LogicException {
		 
		           HttpServletRequest request = (HttpServletRequest)req;			   
				   HttpSession session = request.getSession(false);
				   User user=null; 
					if(session != null) { user = (User)session.getAttribute("sessionUser");} 
					Long uid=null; if (user!=null) {uid = user.getId();}									 
					 
		   			   String sid; 
			    	   try {sid = new String (req.getParameter("baseitem"));}
			    	   catch (NullPointerException e){throw new IllegalArgumentException();}
			    	 
			   		   Long id = null;
			    	   try {id = Long.parseLong(sid);}
			  		   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}
			    		  
			    	   String susebonuspoints=null; Boolean useBonus=false;
			    	   try {susebonuspoints = new String (req.getParameter("usebonuspoints"));}
			    	   catch (NullPointerException e){}
			    	   try {useBonus = Boolean.parseBoolean(susebonuspoints);}
			    	   catch (NullPointerException e) {}
			    	   	    	
			    	   String scurrency=null; 
			    	   try {scurrency = new String (req.getParameter("ordercurrency"));}
			    	   catch (NullPointerException e){}
			    	   Long currency = 1L;
			    	   try {currency = Long.parseLong(scurrency);}
			  		   catch (NullPointerException | NumberFormatException err) {}
			   
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
		   
			return "redirect:/users/cart";	       
	    }
/////////////////////////////////////////////////////////	
	   @RequestMapping(value = {"/product/list/{productId}","/product/list"}, method = RequestMethod.GET)
	    public ModelAndView productlist(HttpServletRequest req,@PathVariable(required = false) Long productId) throws LogicException {
	        ModelAndView modelAndView = new ModelAndView();
	        
	  	  HttpServletRequest request = (HttpServletRequest)req;			  
		  HttpSession session = request.getSession(false);
		   User user=null; Long uid=null; Client client=null; 
			if(session != null) {
				 user = (User)session.getAttribute("sessionUser");
				 if (user!=null) {uid = user.getId();	
				 client=clientService.readByUserId(uid);}
			}  	       		
			Long id = null;	
	  if (productId!=null) {id = productId;}
	  else {
   	    String sid;  
 	   try {sid = new String (req.getParameter("id"));}
 	   catch (NullPointerException e){throw new IllegalArgumentException();}
 	 		   
 	     try {id = Long.parseLong(sid);}
		   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}
	  }
 	     
 	   String scurrency; 
 	   try {scurrency = new String (req.getParameter("currency"));}
 	   catch (NullPointerException e){scurrency="";}
 	 
		  Long currency = null;
 	     try {currency = Long.parseLong(scurrency);}
		   catch (NullPointerException | NumberFormatException err) {}
       Double kurs=1d;
                   
       List<Currency> currencies = currencyService.findAll();
       final Long currency1 =currency;
       if (currency1!=null) {kurs = currencies.stream().filter(x-> x.getId()==currency1).findFirst().get().getRate();}         //filter(x-> x.getId()==currency).limit(1).    }     
       
  //     System.out.println("=====================================prod list1)"+ id);
 	     List<Baseitem> list= baseitemService.readItemRow(id);
 	     if (list!=null) {
 	     if (kurs!=1d) {
 	    	 for (Baseitem b: list) {
 	    		 b.setBaseprice((Long)Math.round(b.getBaseprice()*kurs ));  	    	
 	    	   }  	    
 	       }
 	     }  		      
 		Items item= itemsService.findById(id);  		 	
 	    item.setBaseprice((Long)Math.round(item.getBaseprice()*kurs));

 	      modelAndView.addObject("currencies", currencies);
 	      modelAndView.addObject("client", client);
 	      modelAndView.addObject("list", list);
 	      modelAndView.addObject("item", item); 	
	        
	        modelAndView.setViewName("/product/list");
	        return modelAndView;
	    }
/////////////////////////////////////////////////////////	
@RequestMapping(value = {"/product/{productString}"}, method = RequestMethod.GET)
public ModelAndView productString(HttpServletRequest req,@PathVariable String productString) throws LogicException {
  ModelAndView modelAndView = new ModelAndView();

  HttpServletRequest request = (HttpServletRequest)req;			  
  HttpSession session = request.getSession(false);
  User user=null; Long uid=null; Client client=null; 
  if(session != null) {
    user = (User)session.getAttribute("sessionUser");
    if (user!=null) {uid = user.getId();	
    client=clientService.readByUserId(uid);}
   }  	
 // System.out.println("=====================================prod list2)"+ productString);
   Webpages webpages  = webpagesService.readEntityId(productString);
   Long id=webpages.getId();
//   String sid =id.toString();     

   String scurrency;  
   try {scurrency = new String (req.getParameter("currency"));}
   catch (NullPointerException e){scurrency="";}

   Long currency = null;
  try {currency = Long.parseLong(scurrency);}
  catch (NullPointerException | NumberFormatException err) {}
   Double kurs=1d;

   List<Currency> currencies = currencyService.findAll();
   final Long currency1 =currency;
   if (currency1!=null) {kurs = currencies.stream().filter(x-> x.getId()==currency1).findFirst().get().getRate();}         //filter(x-> x.getId()==currency).limit(1).    }     


   List<Baseitem> list= baseitemService.readItemRow(id);
  if (list!=null) {
      if (kurs!=1d) {
        for (Baseitem b: list) {
         b.setBaseprice((Long)Math.round(b.getBaseprice()*kurs ));  	    	
         }  	    
     }
  }  		      
  Items item= itemsService.findById(id);  		 	
  item.setBaseprice((Long)Math.round(item.getBaseprice()*kurs));

  modelAndView.addObject("currencies", currencies);
  modelAndView.addObject("client", client);
  modelAndView.addObject("list", list);
  modelAndView.addObject("item", item); 	

  modelAndView.setViewName("/product/list");
  return modelAndView;
  }

}
