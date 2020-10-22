package org.itstep.controller_main;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
/*
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import org.itstep.de_services.ItemsService;
import org.itstep.entities.Rate;
*/
/*
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
*/
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itstep.config.ContextKeeper;
import org.itstep.csvupdater.LoadService;
import org.itstep.daos.CurRatDao;
import org.itstep.daos.DaoException;

import org.itstep.entities.Currency;

import org.itstep.help.Files;
import org.itstep.service.CurrencyService;
//import org.itstep.service.RateService;
import org.itstep.tools.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
public class MainController {
	@Autowired private CurrencyService currencyService;
//	@Autowired private RateService rateService;
	
	private static final Logger logger = LogManager.getLogger();
	   @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	    public ModelAndView list() {
	        ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("index");
	        return modelAndView;
	    }
	///////////////////////////////////////////////////////////////////   
	   @RequestMapping(value = {"/upload"}, method = RequestMethod.POST)
	    public String upload(HttpServletRequest req, @RequestParam(required=false) MultipartFile file, ModelMap modelMap) {
		   String fileupload = ContextKeeper.getServletContext().getInitParameter("file-upload");
		   String filename = null;
		   if (file!=null && !file.getName().isBlank()) { 						
			 try {				
				 filename=Files.saveMultipartFileName(file, fileupload);		
				 System.out.println("=============LODING FILE " + filename);
			     if (filename==null) {
			    	 System.out.println("=============FAIL TO LOAD FILE " + filename);}
			     else {			    	 			    	 
			    	    LoadService ls = new LoadService();					 					  
					    File nf = new File(fileupload+filename);
					    boolean bl= ls.createCsvLoad(nf);
					    logger.warn("TRY TO LOAD FILE "+(bl?"SUCCESS":"FAIL")+" : " +nf);			     
				     }		 			    	 			    	 
			   
			} catch (Exception e) {e.printStackTrace();} 												
	    }	   
		   return null;		
	   } 
///////////////////////////////////////////			 
	   @RequestMapping(value = {"/upload"}, method = RequestMethod.GET)
	    public ModelAndView upload() {
		   ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("upload");
	        return modelAndView;
	   }
///////////////////////////////////////////////////////////////////
	   @RequestMapping(value = {"/about"}, method = RequestMethod.GET)
	    public ModelAndView about() {
		   ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("about");
	        Map<String, String> currencyMap = new HashMap<>();
	        String currencytitle ="";
	        try {
				currencyMap = Parser.getCurrencyMap();
				currencytitle = Parser.getTitle();
			} catch (IOException e) {e.printStackTrace();}
	        	   
	       if (currencyMap!=null && currencyMap.size()>0) {updateCurrencies(currencyMap);}
	        
	        modelAndView.addObject("currency", currencyMap);
	        modelAndView.addObject("currencytitle", currencytitle);
	        return modelAndView;
	   }
//////////////////////////////////////////////////////	   
	   @RequestMapping(value = {"/backoffice"}, method = RequestMethod.GET)
	    public ModelAndView backoffice() {
		   ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("backoffice");
	        return modelAndView;
	   }
   
///////////////////////////////////////////	   
	   @RequestMapping(value = {"/contact"}, method = RequestMethod.GET)
	    public ModelAndView contact() {
		   ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("contact");
	        return modelAndView;
	   }
	   @RequestMapping(value = {"/error"}, method = RequestMethod.GET)
	    public ModelAndView error() {
		   ModelAndView modelAndView = new ModelAndView();
	        modelAndView.setViewName("error");
	        return modelAndView;
	   }
	   @RequestMapping(value = {"/terms"}, method = RequestMethod.GET)
	    public ModelAndView terms() {
		   ModelAndView modelAndView = new ModelAndView();
		 //  List<String> titles = new ArrayList<>();
		   		   
	//	    Map<String, List<String>> currencyMap= currenciesToMap(titles);		  	     	        	   	       	       
	  //      modelAndView.addObject("currency", currencyMap);
	    //    modelAndView.addObject("currencytitle", titles);
	       
	        WebApplicationContext context = ContextKeeper.getContext();
	        CurRatDao iservice=context.getBean(CurRatDao.class);
	        List<List<String>> list=null;
			try {
				list=iservice.read();
			} catch (DaoException e) {e.printStackTrace();} 
	        modelAndView.addObject("currency2", list);
	        
	        modelAndView.setViewName("terms");
	        return modelAndView;
	   }	   
////////////////////////////////////////////////////////////
	   private void updateCurrencies(Map<String, String> currencyMap) {
		   List<Currency> list = currencyService.findAll();
		   for(Currency c:list) {
			   for(Map.Entry<String, String> e: currencyMap.entrySet()) {
				 if (e.getKey().contains(c.getName())) {					
					 double dbl = Double.parseDouble(e.getValue().replace(",", "."));
					 c.setRate(dbl); c.addToBase(dbl);
					 currencyService.save(c);
				 }
			   }			   
		   }
	   }
/////////////////////////////////////////////////////////////
	   /*	   private Map<String, List<String>> currenciesToMap(List<String> titles) {
		   titles.add("Dates");
		   List<Currency> list = currencyService.findAll();
		   //List<Rate> list2 = rateService.findAll();
		   int size = list.size();
		   //if (list==null || !(list.size()>0)) {return null;}

		   Map<String, List<String>> currencyMap = new LinkedHashMap<>();
		   //Set<String> dates = new HashSet<>();
		 //  System.out.println("===================2");
		   for(Currency currency:list) {			   
			   titles.add(currency.getName());
			List<Rate> r =currency.getRates(); r.forEach(x -> {
				String s=x.getDate().toString();
			//	dates.add(s);
				List<String> yl = currencyMap.get(s);
				if (yl==null) {yl = new ArrayList<>(size);}				
			});
		   }
		   //System.out.println("===================3");
		   for(int i=0;i<0;i++) {
			   Currency currency=list.get(i);			   
			   List<Rate> r =currency.getRates();			
			   System.out.print("=====currency"+currency.getName() + " ");
			   for(Rate x :r) {
				   String s=x.getDate().toString();
				   List<String> yl = currencyMap.get(s);				   
				   yl.set(i, x.getRate().toString());
				   currencyMap.put(s, yl);
				   System.out.print("=====rate"+x);
			   }
			   			  
		   }	
		   return currencyMap;
	   }
	   /////////////////////////////////////////////////////////////	   
	   class PrinteCurencyTable{
		   Map<String, List<String>> map= new HashMap<>();
		   List<String> dates = new ArrayList<>();
		   
		   void putDates(List<String> dates) {this.dates=dates;}
		   void putCurrency(String cur,List<String> values) {
			   map.put(cur, values);
		   }
	   }
	   */
}
