package org.itstep.web.action.catalog;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.itstep.de_services.BaseitemService;
import org.itstep.de_services.ClientService;
import org.itstep.de_services.CurrencyService;
import org.itstep.de_services.ItemsService;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Client;
import org.itstep.entities.Currency;
//import de_services.OrdersService;
import org.itstep.entities.Items;
import org.itstep.entities.User;
import org.itstep.service.LogicException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
//import web.action.Action.Result;
@Component
@Scope("prototype")
public class ProductListAction extends BaseAction {
	@Autowired			
	BaseitemService baseitemService;		
	@Autowired		
	ClientService clientService;
	@Autowired		
	CurrencyService currencyService;
	@Autowired
	ItemsService itemsService;
	
/*    public void setBaseitemService(BaseitemService baseitemService) {this.baseitemService = baseitemService;}
	public void setClientService(ClientService clientService) {this.clientService = clientService;}    
	public void setCurrencyService(CurrencyService currencyService) {this.currencyService = currencyService;}  */
	
	@Override
   public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
	
		  HttpServletRequest request = (HttpServletRequest)req;	
		   
		   HttpSession session = request.getSession(false);
		   User user=null; Long uid=null; Client client=null; 
			if(session != null) {
				 user = (User)session.getAttribute("sessionUser");
				 if (user!=null) {uid = user.getId();	
				 client=clientService.readByUserId(uid);}
			}  	       		
    
    	String sid; 
  	   try {sid = new String (req.getParameter("id"));}
  	   catch (NullPointerException e){throw new IllegalArgumentException();}
  	 
 		   Long id = null;
  	     try {id = Long.parseLong(sid);}
		   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}
  	  
  	   String scurrency; 
  	   try {scurrency = new String (req.getParameter("currency"));}
  	   catch (NullPointerException e){scurrency="";}
  	 
 		  Long currency = null;
  	     try {
  	    	currency = Long.parseLong(scurrency);}
		   catch (NullPointerException | NumberFormatException err) {}
        Double kurs=1d;
                    
        List<Currency> currencies = currencyService.findAll();
        final Long currency1 =currency;
        if (currency1!=null) {kurs = currencies.stream().filter(x-> x.getId()==currency1).findFirst().get().getRate();}         //filter(x-> x.getId()==currency).limit(1).    }     
        
         
  	     List<Baseitem> list= baseitemService.readItemRow(id);
  	   //System.out.println("===================P L A  list)" +  list+ " id "+id);
  	     if (list!=null) {
  	     if (kurs!=1d) {
  	    	 for (Baseitem b: list) {
  	    		 b.setBaseprice((Long)Math.round(b.getBaseprice()*kurs ));  	    	
  	    	   }  	    
  	       }
  	     }  		      
  		Items item= itemsService.read(id);  		
  	//	System.out.println("====================P L A  item " + item.getMyimg()); 
  	    item.setBaseprice((Long)Math.round(item.getBaseprice()*kurs));

  	    
	  req.setAttribute("currencies", currencies);		   
  	   req.setAttribute("client", client);
  	   req.setAttribute("list", list);
  	     req.setAttribute("item", item);
		return null;
    	
   }
}
