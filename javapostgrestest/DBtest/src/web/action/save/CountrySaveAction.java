package web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.CountrySeviceImpl;
//import entities.Color;
import entities.Country;
import entities.Currency;
//import entities.Webpages;
import service.LogicException;
import web.action.ActionException;
import web.action.BaseAction;
//import web.action.Action.Result;

public class CountrySaveAction extends BaseAction {
	
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
 
   Country country = new Country();	  

   String name=req.getParameter("name"); country.setName(name);
   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}	    	   	    	
   	   	    	   
   Long currency;
   try { currency= Long.parseLong(req.getParameter("currency")); 
   }  catch (NullPointerException | NumberFormatException err) {currency =null;} 
   if (currency==0) {currency=null;}
   
   country.setCurrency(new Currency());
   country.getCurrency().setId(currency);	
   
   country.setId(id); 	    	   
   CountrySeviceImpl countryServiceImpl= (CountrySeviceImpl)getService();

   countryServiceImpl.save(country);

   return new Result("/country/list");
   } catch(IllegalArgumentException e) {
  throw new ActionException(e, 400);
}

}
}	
