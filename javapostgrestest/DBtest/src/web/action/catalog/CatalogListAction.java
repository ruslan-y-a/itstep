package web.action.catalog;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.ClassificationService;
import de_services.CurrencyService;
import de_services.ItemsService;
import de_services.TagcloudService;
import de_services.WebpagesService;
import entities.Classification;
//import entities.Baseitem;
import entities.Currency;
//import entities.Entity;
import entities.Items;
import entities.ItemsSort;
import entities.Webpages;
import help.Params;
import service.LogicException;
//import web.action.ActionException;
import web.action.BaseAction;
//import web.action.Action.Result;
//import web.action.Action.Result;
//import web.action.Action.ResultType;

public class CatalogListAction extends BaseAction {
	private String url;
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	private CurrencyService currencyService;	
	private ClassificationService classificationService;
	private WebpagesService webpagesService;  
	public void setWebpagesService(WebpagesService webpagesService) {this.webpagesService = webpagesService;}
    public void setCurrencyService(CurrencyService currencyService) {this.currencyService = currencyService;}
	public void setClassificationService(ClassificationService classificationService) {this.classificationService = classificationService;}
	private TagcloudService tagcloudService;	
	public void setTagcloudService(TagcloudService tagcloudService) {this.tagcloudService = tagcloudService;}
	
	@Override
   public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
	
    	ItemsService itemsService = (ItemsService) getService();	

    //	System.out.println("================= CATLOG req.getRequestURI())"+req.getRequestURI());
    	
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
    	   
     	  String sitemsonpage; 
    	   try {sitemsonpage = new String (req.getParameter("itemsonpage"));}
    	   catch (NullPointerException e){sitemsonpage ="";}     	   
    	   Integer itemsonpage = 20;
    	   try {itemsonpage = Integer.parseInt(sitemsonpage);}
   		   catch (IllegalArgumentException err) {}

      	  String scategory; 
     	   try {scategory = new String (req.getParameter("scategory"));}
     	   catch (NullPointerException e){scategory ="";}     	   
     	   Integer category = null;
     	   try {category = Integer.parseInt(scategory);}
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

         List<Items> list=null;  
///////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
//System.out.println("================= CATLOG BEFORE");
/*   	   String idsString[] = req.getParameterValues("classification");

List<Integer> iClassif=null;
if(idsString != null) {

iClassif = new ArrayList<>(idsString.length);
for(String id : idsString) {
try {iClassif.add(Integer.parseInt(id));} catch(NumberFormatException e) {}
}	
}  */

/////////////////////////////////////////////////////////		  	     
//////////////CLASSIF/////////////////////////////////////////////		     
/////////////////////////////////////////////////////////
/*     String idsString[] = req.getParameterValues("classification");
List<Integer> iClassif=null; 
if(idsString != null) {
try {
iClassif = new ArrayList<>(idsString.length);
for(String id : idsString) {
iClassif.add(Integer.parseInt(id));}
} catch(NumberFormatException e) {
throw new ActionException(e, 400);
}
}
*/       
  //     System.out.println("CA==============================="); 
       
         Result result=null; 
    	  Long webpageID=null;
    	  List<Long> idCls =null; 
   	   if (url.startsWith("/catalog/list/")) 
   	    {
   		  url=url.substring("/catalog/list/".length());  
   		  result = new Result("/catalog/list", ResultType.FORWARD);
   		  result.add("page", spage);
   		  result.add("sort", name);
   		  result.add("itemsonpage", sitemsonpage);
   		  result.add("currency", scurrency);  
   		
   //		  System.out.println("CA===============================url)" + url);  
   		  
   	     Webpages webpages  = webpagesService.readEntityId(url);    	
     	     if (webpages!=null && webpages.getId()!=null && webpages.getEntity().equals(Params.WP_TAGCLOUD)) {  		   
     		     webpageID = webpages.getId();
     		  req.setAttribute("curl", url+".html"); 
     		  req.setAttribute("webpages", webpages);
     	     }  	  	     
   	   }  
/////////////////////////////////////////////////////////////  
  	//  System.out.println("CA===============================webpageID)" + webpageID);  
         if (webpageID!=null) {idCls =tagcloudService.readByWP(webpageID);}
 /////////////////////////////////////////////////////////  	   
 /////////////////////////////////////////  
           String[] strutl= {""};
           List<List<Integer>> classifLists= makeLists(req,strutl);
           
           if (strutl[0]!=null && !(strutl[0]).isBlank()) {
        		  req.setAttribute("curl", url+".html?"+strutl[0]); }
        	  else {
        		  req.setAttribute("curl", url+".html");}
                      
           if (idCls!=null) {
       	idCls.forEach(x -> {
       		 List<Integer> nl2=new ArrayList<>();
       		 nl2.add(x.intValue());
       		 classifLists.add(nl2);
       		}); 
       	  }
           
     	  String instock; 
    	   try {instock = new String (req.getParameter("In stock"));}
    	   catch (NullPointerException e){instock ="";}     	
               	   
           if (classifLists!=null && classifLists.size()>0) {
        	   if (instock!=null && !instock.isBlank()) {
        	      list =itemsService.searchListsCategories(classifLists, itemsSort, itemsonpage, page, true);
        	   } else {
        		   list =itemsService.searchListsCategories(classifLists, itemsSort, itemsonpage, page);
        	   }
        	 }  
///////////////////////////////////////////////////	    
//////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////         
    	   	 
    	    if (list==null) {    	        	    	     	   
    	       if (category==null) {
    		      list = itemsService.readPage(itemsSort, itemsonpage, page);	
    	        } else {
    	    	  list =  itemsService.search(category, itemsSort, itemsonpage, page);
    	        }	   
    	    }   
    	   
    	   if (kurs!=1d) {
    	    	 for (Items b: list) {b.setBaseprice((Long)Math.round(b.getBaseprice()*kurs ));}  	    
    	     }    	   
    	   
		   req.setAttribute("list", list);
		   req.setAttribute("currencies", currencies);
    	
		return result;
    	
   }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	 private List<List<Integer>> makeLists(HttpServletRequest req, String[] strutl) throws LogicException{
		 StringBuilder sb = new StringBuilder();  
		 List<List<Integer>> result = new ArrayList<>();
		 String idsString[]; // = req.getParameterValues("id");
		 List<Classification> cClass = classificationService.findAll(); //.stream().map(x-> x.getId())
		 Boolean bfirst=false;
		 for (Classification cc: cClass) {
			try { idsString = req.getParameterValues(cc.getName());
			  if (idsString.length>0) {
				  List<Integer> list = new ArrayList<>();
				  for (String x:idsString) {
					  try {
						    list.add(Integer.parseInt(x)); 
						      sb.append((bfirst?"&":""));bfirst=true;
							  sb.append(cc.getName() + "=" +x);
					      } catch (NumberFormatException err1) {} 
					  }
				  result.add(list);
			  }
			} catch (NullPointerException e) {}
			 
		 }
		 strutl[0] = new String(sb.toString());
		 return result;
	 }
}