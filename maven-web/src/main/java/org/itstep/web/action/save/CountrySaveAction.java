package org.itstep.web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.de_services.CountrySevice;
//import org.itstep.de_services.CountrySeviceImpl;
//import entities.Color;
import org.itstep.entities.Country;
import org.itstep.entities.Currency;
//import entities.Webpages;
import org.itstep.service.LogicException;
import org.itstep.web.action.ActionException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
//import web.action.Action.Result;
@Component
@Scope("prototype")
public class CountrySaveAction extends BaseAction {
	@Autowired	
	CountrySevice countrySevice;
	
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
   countrySevice.save(country);

   return new Result("/country/list");
   } catch(IllegalArgumentException e) {
  throw new ActionException(e, 400);
}

}
}	
