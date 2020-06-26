package web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import de_services.CountrySeviceImpl;
import de_services.CurrencyServiceImpl;
//import entities.Country;
import entities.Currency;
import service.LogicException;
import web.action.ActionException;
import web.action.BaseAction;
//import web.action.Action.Result;

public class CurrencySaveAction extends BaseAction {
	
@Override
public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
try {
   
	////////////////////////////////////////
	   String sid; 
   try {sid = new String (req.getParameter("id"));}
   catch (NullPointerException e){sid ="";}
 
	   Long id = null; 
   try {id = Long.parseLong(sid);}
   catch (NullPointerException | NumberFormatException err) {id =null;}
 
   Currency currency = new Currency();	  

   String name=req.getParameter("name"); currency.setName(name);
   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}	    	   	    	
   	   	    	   
   Double rate;
   try { rate= Double.parseDouble(req.getParameter("rate")); 
   }  catch (NullPointerException | NumberFormatException err) {rate =1d;}   
   
   currency.setRate(rate); 
   
   currency.setId(id); 	    	   
   CurrencyServiceImpl currencyServiceImpl= (CurrencyServiceImpl)getService();

   currencyServiceImpl.save(currency);

   return new Result("/currency/list");
   } catch(IllegalArgumentException e) {
  throw new ActionException(e, 400);
}

}
}	
