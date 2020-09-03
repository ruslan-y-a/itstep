package org.itstep.web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.de_services.CurrencyService;
//import de_services.CountrySeviceImpl;
//import org.itstep.de_services.CurrencyServiceImpl;
//import entities.Country;
import org.itstep.entities.Currency;
import org.itstep.service.LogicException;
import org.itstep.web.action.ActionException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
//import web.action.Action.Result;
@Component
@Scope("prototype")
public class CurrencySaveAction extends BaseAction {
	@Autowired		
	CurrencyService currencyService;
	
	
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
   currencyService.save(currency);

   return new Result("/currency/list");
   } catch(IllegalArgumentException e) {
  throw new ActionException(e, 400);
}

}
}	
