package org.itstep.web.action.save;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.de_services.BaseitemService;
//import org.itstep.de_services.BaseitemServiceImpl;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Color;
import org.itstep.entities.Currency;
import org.itstep.entities.Items;
import org.itstep.entities.Size;
import org.itstep.service.LogicException;
import org.itstep.web.action.ActionException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class BaseitemSaveAction extends BaseAction {
	@Autowired		
	BaseitemService baseitemService;
	
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
 	   
 	   Baseitem baseitem = new Baseitem();    	  
 	 
 	   String name;
 	   Long baseprice;
 	   Integer quantity;	
 	   
 	   Long item;
 	   Long color;
 	   Long size;
 	   Long currency;
 	 
	   name=req.getParameter("name"); baseitem.setName(name);  
	   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}

	   try {baseprice= Long.parseLong(req.getParameter("baseprice")); }
	   catch (NullPointerException | NumberFormatException err) {baseprice =null;}
	     if(baseprice == null || baseprice<0) {baseprice=0L; }
	     baseitem.setBaseprice(baseprice);  	

	   try { quantity= Integer.parseInt(req.getParameter("quantity"));}
        catch (NullPointerException | NumberFormatException err) {quantity =null;}
	   if(quantity == null || quantity<0) {quantity=0; }; 
	   baseitem.setQuantity(quantity); 

	   item= Long.parseLong(req.getParameter("items")); 
 	   if(item == null || item<=0) {throw new IllegalArgumentException();}
 	   baseitem.setItem(new Items()); baseitem.getItem().setId(item);
 
 	   color= Long.parseLong(req.getParameter("color")); 
 	   if(color == null || color<=0) {throw new IllegalArgumentException();}
 	   baseitem.setColor(new Color()); baseitem.getColor().setId(color);

 	   size= Long.parseLong(req.getParameter("sizeid")); 
 	   if(size == null || size<=0) {throw new IllegalArgumentException();}
 	   baseitem.setSize(new Size()); baseitem.getSize().setId(size);

 	   currency= Long.parseLong(req.getParameter("currency")); 
 	   if(currency == null || currency<=0) {throw new IllegalArgumentException();}
 	   baseitem.setCurrency(new Currency()); baseitem.getCurrency().setId(currency);
 	
 	   baseitem.setId(id); 	    	   
 	   baseitemService.save(baseitem);

	   return new Result("/baseitem/list");
	    } catch(IllegalArgumentException e) {
	    	e.printStackTrace();
	    throw new ActionException(e, 400);
	     }
  }
}	
