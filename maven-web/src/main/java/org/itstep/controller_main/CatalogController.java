package org.itstep.controller_main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.itstep.aspect.MyAnnotation;
import org.itstep.de_services.CategoryService;
import org.itstep.de_services.ClassificationService;
//import org.itstep.de_services.CurrencyService;
import org.itstep.service.CurrencyService;
import org.itstep.de_services.ItemsService;
import org.itstep.de_services.TagcloudService;
import org.itstep.de_services.WebpagesService;
import org.itstep.entities.Category;
import org.itstep.entities.Classification;
import org.itstep.entities.Currency;
import org.itstep.entities.Items;
import org.itstep.entities.ItemsSort;
import org.itstep.entities.Webpages;
import org.itstep.help.Params;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping("/catalog")
public class CatalogController {
	@Autowired private WebpagesService webpagesService;   
	@Autowired private CurrencyService currencyService;
	@Autowired private ItemsService itemsService;
	@Autowired private ClassificationService classificationService;
	@Autowired private TagcloudService tagcloudService;
	@Autowired private CategoryService categoryService;
	
	private String name, spage, sitemsonpage, scurrency, scategory, uri; 
	private ItemsSort itemsSort = ItemsSort.NAMEASC;;	
	private Integer page=1, itemsonpage = 20; 
	private Long currency = null, icategory=null, webpageID=null;
	private List<Items> iList=null;  
	private Double kurs=1d;

//====================================================================================================	
	 public String getUrl(HttpServletRequest req) {
		return req.getRequestURI();
	 }
	
	  @RequestMapping(value = {"/category/{categoryname}" }, method = RequestMethod.GET)
	    public String categorylist(HttpServletRequest req, Model model, @PathVariable(required = false) String categoryname) throws LogicException {
		    getRequestParams(req);	
		    uri = getUrl(req);
		
		    model.addAttribute("page", spage);
	        model.addAttribute("sort", name);
		    model.addAttribute("itemsonpage", sitemsonpage);
		    model.addAttribute("currency", scurrency);
///////////////SEARCH///////////////////////////////////////////////
		    //String searchUrl = req.getQueryString();
		    String search=req.getParameter("search");
		    if (search!=null && !search.isBlank()) {		      
		      if (uri.indexOf("/catalog/search")>=0) {return "redirect:/catalog/search";}
		    }
//////////////////////////////////////////////////////////////////		    		 		    
		     Webpages webpages  = webpagesService.readEntityId(categoryname);    	
		 //    System.out.println("======================================webpages)"+webpages);		     
		     if (webpages!=null && webpages.getId()!=null) {
		    	 if (webpages.getEntity().equals(Params.WP_CATEGORY)) {  		   
		              icategory = webpages.getEntityid(); webpageID = webpages.getId();			         
			                
		         } else if (webpages.getEntity().equals(Params.WP_TAGCLOUD)) {
		        	webpageID = webpages.getId();
		         }
		    	 model.addAttribute("webpages", webpages);		    
		     }    		
///////////////////////////////////////////////////////////////////
         List<Category> list =  categoryService.findAll();	
         List<Long> ids = findCategories(list,icategory, null);  	
/////////////////////////////////////////////////////////////        
         List<Long> idCls =null; if (webpageID!=null) {idCls =tagcloudService.readByWP(webpageID);}		          
//////////////CLASSIF/////////////////////////////////////////////		     
            String[] strutl= {""};
             List<List<Integer>> classifLists= makeLists(req,strutl); 
          //   System.out.println("=====================================classifLists1)"+ classifLists);
           //  System.out.println("=====================================classifLists ids)"+ ids);
             uri = uri.substring(req.getContextPath().length());  // ?????????????????????  
            if (strutl[0]!=null && !(strutl[0]).isBlank()) {                
              model.addAttribute("curl", uri+"?"+strutl[0]);   //TODO    "curl", uri+".html?"+strutl[0]
             }
             else { model.addAttribute("curl", uri);}  //TODO      "curl", uri+".html"
/////////////////>>
            if (classifLists!=null ) {  //&& classifLists.size()>0

              if (ids!=null && ids.size()>0) {

                  List<Classification> classification= classificationService.readByCategory(ids);		
                 if (classification!=null) {
                   List<Integer> nl=new ArrayList<>();
                   classification.forEach(x -> nl.add(x.getId().intValue())); 
                   classifLists.add(nl);
                 } 
               }       
//////////////////////////////                
             Map<Long,List<Integer>> classifWebpageList = new HashMap<>(); 
              
             idCls.forEach(x -> {
            	  Long tpid=0L;
				try {tpid = classificationService.findById(x).getParentid();}
				catch (LogicException e) {e.printStackTrace();}
				 Long mykey=((tpid!=null && tpid>0)?tpid:0);
				  List<Integer> ntlist = classifWebpageList.get(mykey);
				  if (ntlist==null) {ntlist=new ArrayList<>();}
				  ntlist.add(x.intValue());
				  classifWebpageList.put(mykey, ntlist);
            	  
              });
            
             for(Map.Entry<Long,List<Integer>> mymap: classifWebpageList.entrySet()) {
            	 classifLists.add(mymap.getValue());
             }
                                      
  //       System.out.println("=====================================classifLists)"+ itemsSort +" "+ itemsonpage +" "+ page);
    //     System.out.println("=====================================classifLists.size)"+ classifLists);
          
            String instock; 
            try {instock = req.getParameter("In stock");}
            catch (NullPointerException e){instock ="";}     	

            if (classifLists!=null && classifLists.size()>0) {
            if (instock!=null && !instock.isBlank()) {	
                iList =itemsService.searchListsCategories(classifLists, itemsSort, itemsonpage, page,true);
               } else {
                iList =itemsService.searchListsCategories(classifLists, itemsSort, itemsonpage, page);
               } 
            }
            
    //   System.out.println("=====================================iList.size())" + iList.size());
            model.addAttribute("sort", name);
            model.addAttribute("page", spage);
            model.addAttribute("itemsonpage", sitemsonpage);
            model.addAttribute("currency", scurrency);

           if (iList.size()>0) {
               if (kurs!=1d) {
                    for (Items b: iList) {b.setBaseprice((Long)Math.round(b.getBaseprice()*kurs ));}  	    
               }    	
               model.addAttribute("list", iList);               
            }
   //        System.out.println("================================CAT1 CONTR1=====DONE)"); 
           return "catalog/list";
        }   
 //////////////<<           
///////////////////////////////////////////////////////////////  	 
           if (ids.size()>0) { 	 
              iList=itemsService.searchCategories(ids, itemsSort, itemsonpage, page);}
           else {        	           	   
        	  iList = itemsService.readPage(itemsSort, itemsonpage, page);	     	        	   
            }
         
           if (kurs!=1d) {
             for (Items b: iList) {b.setBaseprice((Long)Math.round(b.getBaseprice()*kurs ));}  	    
             }    		
   //        System.out.println("================================CAT1 CONTR1=====iList)" + + iList.size()); 
           model.addAttribute("list", iList);
   return "catalog/list";
}	 
///////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////
	  @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
	    public String list(HttpServletRequest req, Model model) throws LogicException {
		    getRequestParams(req);	
		    uri = getUrl(req);
		    model.addAttribute("page", spage);
	        model.addAttribute("sort", name);
		    model.addAttribute("itemsonpage", sitemsonpage);
		    model.addAttribute("currency", scurrency);
///////////////SEARCH///////////////////////////////////////////////
		    //String searchUrl = req.getQueryString();
		    String search=req.getParameter("search");
		    if (search!=null && !search.isBlank()) {		      
		      if (uri.indexOf("search")>=0) {return "redirect:/catalog/search";}
		    }
//////////////////////////////////////////////////////////////////		    		 		    
       List<Category> list =  categoryService.findAll();	
       List<Long> ids = findCategories(list,icategory, null);  	       
//////////////CLASSIF/////////////////////////////////////////////	
    //   System.out.println("=====================================CONTROL2)");       
          String[] strutl= {""};
           List<List<Integer>> classifLists= makeLists(req,strutl); 
    //      System.out.println("=====================================CONTROL21 classifLists)" + classifLists.size());   
           uri = uri.substring(req.getContextPath().length());  // ????????????????????? 
          if (strutl[0]!=null && !(strutl[0]).isBlank()) {                
            model.addAttribute("curl", uri+"?"+strutl[0]);   //TODO     "curl", uri+".html?"+strutl[0]
           }
           else { model.addAttribute("curl", uri);}   //TODO     "curl", uri+".html"
/////////////////>>
          if (classifLists!=null && classifLists.size()>0) {  //&& classifLists.size()>0
            try {
              if (ids!=null && ids.size()>0) {

                List<Classification> classification= classificationService.readByCategory(ids);		
                if (classification!=null) {
                  List<Integer> nl=new ArrayList<>();
                  classification.forEach(x -> nl.add(x.getId().intValue())); 
                  classifLists.add(nl);
                } 
              }
            } catch (Exception e) {}  
        //  System.out.println("=====================================classifLists22)" + (classifLists!=null?classifLists.get(0):""));
     
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

          model.addAttribute("sort", name);
          model.addAttribute("page", spage);
          model.addAttribute("itemsonpage", sitemsonpage);
          model.addAttribute("currency", scurrency);

         if (iList.size()>0) {
             if (kurs!=1d) {
                  for (Items b: iList) {b.setBaseprice((Long)Math.round(b.getBaseprice()*kurs ));}  	    
             }    	
             model.addAttribute("list", iList);               
          }
    //    System.out.println("================================CAT CONTR2=====DONE)"); 
         return "catalog/list";
      }   
//////////////<<           
///////////////////////////////////////////////////////////////  	 
         if (ids.size()>0) { 	 
            iList=itemsService.searchCategories(ids, itemsSort, itemsonpage, page);}
         else {        	           	   
      	  iList = itemsService.readPage(itemsSort, itemsonpage, page);	     	        	   
          }
      
         if (kurs!=1d) {
           for (Items b: iList) {b.setBaseprice((Long)Math.round(b.getBaseprice()*kurs ));}  	    
           }    		
     //    System.out.println("==============================CAT CONTR2======iList)" + iList.size());
         model.addAttribute("list", iList);
 return "catalog/list";
}	 
///////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////	
@MyAnnotation
@RequestMapping(value = {"/search/{searchparam}", "/search" })  //, method = RequestMethod.GET
public ModelAndView search(HttpServletRequest req,@PathVariable(required = false) String searchparam) throws LogicException {
	  uri = getUrl(req); 
	getRequestParams(req);	
	ModelAndView modelAndView = new ModelAndView();
	
//	  System.out.println("=====================================search)" );
	 String search;
	 if (searchparam!=null) {search=searchparam;}
	 else {search=req.getParameter("search"); }	 	
 //    System.out.println("=====================================search)" + search);
	  if (search!=null && !search.isBlank()) {	
		  try {
			    getRequestParams(req);
			    iList = itemsService.search(search, itemsSort, itemsonpage, page);	
	//		    System.out.println("=====================================search iList)" + (iList!=null?iList.size():""));
			    if (kurs!=1d) { for (Items b: iList) {b.setBaseprice((Long)Math.round(b.getBaseprice()*kurs ));}}   	       	    	    	       
				modelAndView.addObject("list", iList);	        	        				
			  } catch (LogicException e) {}		  		 
			 modelAndView.addObject("search", search);	         
	     }   
	 	 		  		   	 
	        modelAndView.setViewName("catalog/search");
	        return modelAndView;	     		
}	 
////////////////////////////////////////////////////////	  
//////////////////////////////////////////////////////////////////////////////////////
/////////////////////////END/////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////
private void getRequestParams(HttpServletRequest req) throws LogicException {
	   name=req.getParameter("sort");  
	   if(name == null || name.isBlank()) {itemsSort = ItemsSort.NAMEASC;}	
	   else {itemsSort = ItemsSort.valueOf(name);}
	   	   
	   try {spage = new String (req.getParameter("page"));}
	   catch (NullPointerException e){spage ="";}     	   
	   try {page = Integer.parseInt(spage);} catch (IllegalArgumentException err) {}  
	   	  
	   try {sitemsonpage = new String (req.getParameter("itemsonpage"));}
	   catch (NullPointerException e){sitemsonpage ="";}     	   
	   try {itemsonpage = Integer.parseInt(sitemsonpage);} catch (IllegalArgumentException err) {}
	   	   
	   try {scurrency = new String (req.getParameter("currency"));}
	   catch (NullPointerException e){scurrency="";}
	     try {currency = Long.parseLong(scurrency);} catch (NullPointerException | NumberFormatException err) {}
////////////////////////////////////////////////////////	     	 
	 List<Currency> currencies = currencyService.findAll();  	     	     
	 req.setAttribute("currencies", currencies);	   	   
	 final Long currency1 =currency;
     if (currency1!=null) {kurs = currencies.stream().filter(x-> x.getId()==currency1).findFirst().get().getRate();}
        
	   try {scategory = new String (req.getParameter("category"));}	    	   
	   catch (NullPointerException e){} //return new Result("/catalog/list");     	   
	   try {icategory = Long.parseLong(scategory);}
	   catch (NullPointerException | NumberFormatException err) {}         
}

/////////////////////////////////////////////////////////// 
/////////////////////////////////////////////////////////// 	  
private List<Long> findCategories(List<Category> categories,Long categoryId,List<Long> ids) {
   if (ids==null) {ids = new ArrayList<Long>();}
     List<Category> newlist=null;
     try {newlist=categories.stream().filter((x)-> x.getParentid()==categoryId).collect(Collectors.toList());}
     catch (Exception e) {}
     if ((categoryId!=null) && (newlist==null || newlist.size()==0)) {ids.add(categoryId); return ids;}
     else if (newlist!=null) {		  
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
   return result;
  }	
}
