package web.action.catalog;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.CategoryServiceImpl;
import de_services.ClassificationService;
import de_services.CurrencyService;
import de_services.ItemsService;
import de_services.TagcloudService;
import de_services.WebpagesService;
import entities.Category;
import entities.Classification;
import entities.Currency;
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

public class CatalogDispatherAction extends BaseAction { 
	private String url;
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}

	private WebpagesService webpagesService;   
	private CurrencyService currencyService;
	private ItemsService itemsService;
	private ClassificationService classificationService;
	private TagcloudService tagcloudService;
	//public WebpagesService getWebpagesService() {return webpagesService;}
	public void setWebpagesService(WebpagesService webpagesService) {this.webpagesService = webpagesService;}
	//public CurrencyService getCurrencyService() {return currencyService;}
	public void setCurrencyService(CurrencyService currencyService) {this.currencyService = currencyService;}		
	//public ItemsService getItemsService() {return itemsService;}
	public void setItemsService(ItemsService itemsService) {this.itemsService = itemsService;}
	//public ClassificationService getClassificationService() {return classificationService;}
	public void setClassificationService(ClassificationService classificationService) {this.classificationService = classificationService;}		
	public void setTagcloudService(TagcloudService tagcloudService) {this.tagcloudService = tagcloudService;}
	
	@Override
	   public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {		
		
	//	System.out.println("=================req.getRequestURI())"+req.getRequestURI());
		//System.out.println("=================req.getQueryString())"+req.getQueryString());
	//	System.out.println("=================url)"+url);
	
  	////////////////////////////
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
		  	   
			   String scurrency; 
		  	   try {scurrency = new String (req.getParameter("currency"));}
		  	   catch (NullPointerException e){scurrency="";}
		  	 
		 		  Long currency = null;
		  	     try {
		  	    	currency = Long.parseLong(scurrency);}
				   catch (NullPointerException | NumberFormatException err) {}
////////////////////////////////////////////////////////		  	     
/////////////////////////////////////////////////////////// 
		  	   List<Items> iList=null;  
		  	 List<Currency> currencies = currencyService.findAll();  	     	     
		  	req.setAttribute("currencies", currencies);	   
		  	   Double kurs=1d;
		  	 final Long currency1 =currency;
		     if (currency1!=null) {kurs = currencies.stream().filter(x-> x.getId()==currency1).findFirst().get().getRate();}
/////////////////////////////////////////////////////////		  	     
 ///////////////////////////////////////////////////////            
////////////////////////////////STATIC URL//////////////////  
//////////////////////////////////////////////////	   
		  	   String scategory=null; 
	    	   try {scategory = new String (req.getParameter("category"));}	    	   
	    	   catch (NullPointerException e){} //return new Result("/catalog/list");     
	    	   Long icategory = null;
	    	   try {icategory = Long.parseLong(scategory);}
	    	   catch (NullPointerException | NumberFormatException err) {}

	  Result result=null; 
	  Long webpageID=null;
	  
	   if (url.startsWith("/catalog/category/")) 
	    {
		  url=url.substring("/catalog/category/".length());  
		  result = new Result("/catalog/list", ResultType.FORWARD);
		  result.add("page", spage);
		  result.add("sort", name);
		  result.add("itemsonpage", sitemsonpage);
		  result.add("currency", scurrency);
	//	  result.add("currencies", currencies);
		  
	     Webpages webpages  = webpagesService.readEntityId(url);    	
  	     if (webpages!=null && webpages.getId()!=null) {
  	    	 if (webpages.getEntity().equals(Params.WP_CATEGORY)) {  		   
  	              icategory = webpages.getEntityid(); webpageID = webpages.getId();
  		         req.setAttribute("curl", url+".html"); 
  	         } else if (webpages.getEntity().equals(Params.WP_TAGCLOUD)) {
  	        	webpageID = webpages.getId();
  	         }
  	    	req.setAttribute("webpages", webpages);
  	     }	 
	   }  
///////////////////////////////////////
///////////////////////////////////////////////////////////////////
        CategoryServiceImpl categoryServiceImpl= (CategoryServiceImpl)getService();
        List<Category> list =  categoryServiceImpl.findAll();	
        List<Long> ids = findCategories(list,icategory, null);  	
///////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////        
        List<Long> idCls =null;
        if (webpageID!=null) {idCls =tagcloudService.readByWP(webpageID);}
/////////////////////////////////////////////////////////  	   
//////////////////////////////////////////
//////////////CLASSIF/////////////////////////////////////////////		     
/////////////////////////////////////////////////////////
  String[] strutl= {""};
  List<List<Integer>> classifLists= makeLists(req,strutl);
 // System.out.println("=====================================strutl)" + strutl[0]);
  if (strutl[0]!=null && !(strutl[0]).isBlank()) {
	  req.setAttribute("curl", url+".html?"+strutl[0]); }
  else {
	  req.setAttribute("curl", url+".html");}

  if (classifLists!=null ) {  //&& classifLists.size()>0
	if (ids!=null && ids.size()>0) {
		
		List<Classification> classification= classificationService.readByCategory(ids);		
		if (classification!=null) {
			List<Integer> nl=new ArrayList<>();
			classification.forEach(x -> nl.add(x.getId().intValue())); 
			classifLists.add(nl);
		} 
	} 
	
	if (idCls!=null) {List<Integer> nl2=new ArrayList<>();
	idCls.forEach(x -> nl2.add(x.intValue())); 
	classifLists.add(nl2);}
	
	  String instock; 
	   try {instock = new String (req.getParameter("In stock"));}
	   catch (NullPointerException e){instock ="";}     	
          	   
      if (classifLists!=null && classifLists.size()>0) {
   	   if (instock!=null && !instock.isBlank()) {	
          iList =itemsService.searchListsCategories(classifLists, itemsSort, itemsonpage, page,true);
   	   } else {
   		  iList =itemsService.searchListsCategories(classifLists, itemsSort, itemsonpage, page);
   	   } 
   	  }
   		   
    req.setAttribute("sort", name);
    req.setAttribute("page", spage);
    req.setAttribute("itemsonpage", sitemsonpage);
    req.setAttribute("currency", scurrency);      	
	if (iList.size()>0) {
    if (kurs!=1d) {
      for (Items b: iList) {b.setBaseprice((Long)Math.round(b.getBaseprice()*kurs ));}  	    
     }    	
     req.setAttribute("list", iList);
	}
    return result;
}    
 ////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////    
///////////////////////////////////////////////////////////////  	 
       if (ids.size()==0) {throw new IllegalArgumentException();} 	 
  	   iList=itemsService.searchCategories(ids, itemsSort, itemsonpage, page);  	  	 	
  	   
  	 if (kurs!=1d) {
    	 for (Items b: iList) {b.setBaseprice((Long)Math.round(b.getBaseprice()*kurs ));}  	    
     }    				
  	 
  	    req.setAttribute("list", iList);
	   
	   
		return result;
	}
//////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////END/////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////	
	 private List<Long> findCategories(List<Category> categories,Long categoryId,List<Long> ids) {
	  if (ids==null) {ids = new ArrayList<Long>();}
	  List<Category> newlist=null;
	  try {newlist=categories.stream().filter((x)-> x.getParentid()==categoryId).collect(Collectors.toList());}
	  catch (Exception e) {}
	  if (newlist==null || newlist.size()==0) {ids.add(categoryId); return ids;}
	  else {		  
		  for (Category x :newlist) {
			  findCategories(newlist,x.getId(),ids);}
	      }
	   return ids; 	
	 }

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
					  try {list.add(Integer.parseInt(x));
					  sb.append((bfirst?"&":""));bfirst=true;
					  sb.append(cc.getName() + "=" +x);
					  } catch (NumberFormatException err1) {} }
				  result.add(list);
			  }
			} catch (NullPointerException e) {}
			 
		 }
		 strutl[0] = new String(sb.toString());
	//	 System.out.println("=====================================INS strutl)" + strutl);
		 return result;
	 }
}
