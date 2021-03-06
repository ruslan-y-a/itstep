package web.action.save;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.ClientServiceImpl;
//import de_services.UserServiceImpl;
import entities.Client;
import entities.Country;
import entities.Items;
import entities.User;
//import entities.Webpages;
import service.LogicException;
//import tabs.Role;
import web.action.ActionException;
import web.action.BaseAction;
//import web.action.Action.Result;

public class ClientSaveAction extends BaseAction {

	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		try {
	    	   
	    	   String sid; 
	    	   try {sid = new String (req.getParameter("id"));}
	    	   catch (NullPointerException e){sid ="";}
	    	 
	   		   Long id = null;
	    	   try {id = Long.parseLong(sid);}
	  		   catch (NullPointerException | NumberFormatException err) {id =null;}
	    	   Client client = new Client();    	  
	    
	    	   String country=req.getParameter("country"); 
	    	   Long icountry = null;
	    	   try {icountry = Long.parseLong(country);}
	  		   catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}	    	   
	    	   client.setCountry(new Country()); client.getCountry().setId(icountry);
	    	   
	    	   
	    	   String address=req.getParameter("address"); client.setAddress(address);
	    	   if(address == null || address.isBlank()) {throw new IllegalArgumentException();}
	    	   	    	   	    	  	    	   	    	   
	    	   String creationdate=req.getParameter("creationdate"); Date date;
	    	   if(creationdate == null || creationdate.isBlank()) {
	    		   date = new Date();
	    		   } else {	    			   
	    			   SimpleDateFormat format = new SimpleDateFormat();
	    	    	   format.applyPattern("dd.MM.yyyy");
	    			   try { date= format.parse(creationdate);}
	    	    	   catch ( ParseException err) {
	    	    		   format.applyPattern("dd-MM-yyyy");
	    	    		   try { date= format.parse(creationdate);}
	    	    		   catch ( ParseException err1) {
	    	    		   date = new Date();	    	}    		   
	    	    	   }; 
	    		   } 	   
	    	   client.setCreationdate(date);   
	    	     
	    	   
	    	   String user=req.getParameter("user"); 
	    	   Long iuser = null;
	    	   try {iuser = Long.parseLong(user);}
	  		   catch (NullPointerException | NumberFormatException err) {iuser =null;}
	    	   if(iuser == null) {throw new IllegalArgumentException();}
	    	   client.setUser(new User()); client.getUser().setId(iuser);
	    	   	    	  	    	  
	    	   String bonuspoints=req.getParameter("bonuspoints");   ;
	    	   Integer ibonuspoints;
	    	   if(bonuspoints == null || bonuspoints.isBlank()) {ibonuspoints=0;}
	    	   else {
	    	   try {ibonuspoints = Integer.parseInt(bonuspoints);}
	  		   catch (NullPointerException | NumberFormatException err) {ibonuspoints=0;}
	    	   }; client.setBonuspoints(ibonuspoints);
	    	   	    	   
	    	   String phoneno=req.getParameter("phoneno"); 
	    	   if(phoneno == null || phoneno.isBlank()) {phoneno="";}
	    	   client.setPhoneno(phoneno);
	    	   Items item;
	    	   String sTtems[]=  req.getParameterValues("recentitems"); 	    	   
	    	   if (sTtems!=null) {
	    	   ArrayList<Items> itemIds = new ArrayList<>(); 
	    	   for(int i=0; i<sTtems.length;i++) {
	    		   try { 	    			 
	    			   item = new Items(); item.setId(Long.parseLong(sTtems[i])); 	    		    
	    			   itemIds.add(item);
	    		      } catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}
	    		   }	    	       	 	    	   
	    	   client.setRecentitems(itemIds);
	    	   }
	    	   client.setId(id); 	    	   
	    	   
	    	   ClientServiceImpl clientService = (ClientServiceImpl)getService(); 
	    		
	    	   clientService.save(client);	    	    
	    	//	System.out.println("-------------SAVED user:" + user);
				return new Result("/client/list");

		} catch(IllegalArgumentException e) {
			throw new ActionException(e, 400);
		}
	}

}