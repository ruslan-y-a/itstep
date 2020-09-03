package web.action.catalog;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import de_services.BaseitemService;
import de_services.ClientService;
import de_services.CurrencyService;
import de_services.ItemsService;
import entities.Baseitem;
import entities.Client;
import entities.Currency;
//import de_services.OrdersService;
import entities.Items;
import entities.User;
import service.LogicException;
import web.action.BaseAction;
//import web.action.Action.Result;

public class ProductListAction extends BaseAction {
	BaseitemService baseitemService;		
	ClientService clientService;
	CurrencyService currencyService;
    public void setBaseitemService(BaseitemService baseitemService) {
		this.baseitemService = baseitemService;}
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;}    
	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;}
	@Override
   public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
	
		  HttpServletRequest request = (HttpServletRequest)req;	
	//	   HttpServletResponse response = (HttpServletResponse)resp;
		   
		   HttpSession session = request.getSession(false);
		   User user=null; Long uid=null; Client client=null; 
			if(session != null) {
				 user = (User)session.getAttribute("sessionUser");
				 if (user!=null) {uid = user.getId();	
				 client=clientService.readByUserId(uid);}
			}  	       		
	
    	ItemsService itemsService = (ItemsService) getService();	
    
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
