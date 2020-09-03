package org.itstep.web.action.catalog;

//import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.itstep.de_services.CurrencyService;
import org.itstep.de_services.ItemsService;
import org.itstep.entities.Currency;
import org.itstep.entities.Items;
import org.itstep.entities.ItemsSort;
import org.itstep.service.LogicException;
//import org.itstep.web.action.ActionException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SearchAction extends BaseAction {
	 	  
	  @Autowired		
	  CurrencyService currencyService;
	  @Autowired			  
	  ItemsService itemsService;	  
/*	  public SearchAction(String searchStr) {super(); this.searchStr = searchStr;}
	  public SearchAction() {super(); }    
	  public void setCurrencyService(CurrencyService currencyService) {this.currencyService = currencyService;} */
	  
	  @Override
	  public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
		     String uri = req.getRequestURI();
		     int spos =url.indexOf("="); 
		     url = url.substring(spos+1);
		//	System.out.println("========================search RESULT uri)" + uri);
		//	System.out.println("========================search RESULT searchStr)" + searchStr);

	   	ItemsSort itemsSort;
	   	 String name=req.getParameter("sort");  
	   	   if(name == null || name.isBlank()) {itemsSort = ItemsSort.NAMEASC;}	
	   	   else {itemsSort = ItemsSort.valueOf(name);}
	   	   
	   	   String spage; 
	    	   try {spage = new String (req.getParameter("page"));}
	    	   catch (NullPointerException e){spage ="";}     	   
	   		   Integer page = 1;
	    	   try {page = Integer.parseInt(spage);}
	   		   catch (IllegalArgumentException err) {}  
	   	   
	       	   String sSearch=null; 
	    	   try {sSearch = new String (req.getParameter("search"));}
	    	   catch (NullPointerException e){spage ="";}     	   
	   		  if (sSearch!= null && !sSearch.isBlank()) {url=sSearch;}	   
	    	   
	    	  String sitemsonpage; 
	   	   try {sitemsonpage = new String (req.getParameter("itemsonpage"));}
	   	   catch (NullPointerException e){sitemsonpage ="";}     	   
	   	   Integer itemsonpage = 20;
	   	   try {itemsonpage = Integer.parseInt(sitemsonpage);}
	  		   catch (IllegalArgumentException err) {}   	      	       
	    	   
	    	  List<Currency> currencies = currencyService.findAll();

	     	   String scurrency; 
	     	   try {scurrency = new String (req.getParameter("currency"));}
	     	   catch (NullPointerException e){scurrency="";}
	     	 
	    		  Long currency = null;
	     	     try {
	     	    	currency = Long.parseLong(scurrency);}
	   		   catch (NullPointerException | NumberFormatException err) {}
	     	   Double kurs=1d;
	     	 final Long currency1 =currency;
	        if (currency1!=null) {kurs = currencies.stream().filter(x-> x.getId()==currency1).findFirst().get().getRate();}
	     	   
	///////////////////////////////////////////////////////////  
	 /*      String idsString[] = req.getParameterValues("classification");
	       List<Integer> iClassif=null;
	       if(idsString != null) {
	         try {
	                iClassif = new ArrayList<>(idsString.length);
	              for(String id : idsString) {iClassif.add(Integer.parseInt(id));}
	             } catch(NumberFormatException e) {
	                 throw new ActionException(e, 400);
	            }
	        }	*/
	////////////////////////////////////////////////// 
	        
	   	   List<Items> list=null;
	   	/*  	 if (iClassif!=null) {   		 
	   		   list =itemsService.search(iClassif, itemsSort, itemsonpage, page);
		    } else {
	   		   list = itemsService.search(searchStr, itemsSort, itemsonpage, page);	  } */  
	   	 
	   	 list = itemsService.search(url, itemsSort, itemsonpage, page);	
	   	       
	   	   if (kurs!=1d) {
	   	    	 for (Items b: list) {b.setBaseprice((Long)Math.round(b.getBaseprice()*kurs ));}  	    
	   	     }    	   
	   	   
			   req.setAttribute("list", list);
			   req.setAttribute("currencies", currencies);
			   
			 //  System.out.println("========================search RESULT list)" + list);
			   
			   Result result=null;
			   if (uri.indexOf("/catalog/search")>=0) {result = null;}
			   else {result = new Result("/catalog/search");result.add("search", url);}
			    		   
			return result;
	   	
	  }
	}