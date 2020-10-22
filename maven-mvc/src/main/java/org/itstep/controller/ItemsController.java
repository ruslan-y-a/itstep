package org.itstep.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import java.util.Map;
//import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.itstep.entities.Category;
import org.itstep.entities.Classification;
import org.itstep.entities.Img;
import org.itstep.entities.Items;
import org.itstep.entities.Webpages;
import org.itstep.help.Files;
import org.itstep.help.Params;
import org.itstep.service.LogicException;
import org.itstep.config.ContextKeeper;
//import org.itstep.de_services.CategoryService;
import org.itstep.de_services.CategoryServiceImpl;
//import org.itstep.de_services.ClassificationService;
import org.itstep.de_services.ClassificationServiceImpl;
import org.itstep.service.ImgService;
import org.itstep.de_services.ItemsService;
//import org.itstep.de_services.WebpagesService;
import org.itstep.de_services.WebpagesServiceImpl;

@Controller
@Scope("prototype")
@RequestMapping(value = "/items")
public class ItemsController{		
	@Autowired private ItemsService itemsService;	
	@Autowired private WebpagesServiceImpl webpagesService;
	@Autowired private ClassificationServiceImpl classificationService;
	@Autowired private CategoryServiceImpl categoryService;
	@Autowired private ImgService imgService;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Items> list = itemsService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("items/list");
        modelAndView.addObject("list", list);
	    return modelAndView;
	}
	
	@RequestMapping("/edit")
	public ModelAndView editItems(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView("items/edit");
	    if (id!=null) {
	     Items items = itemsService.findById(id);	
	     mav.addObject("entity", items);
	    }
	    List<Category> category = categoryService.findAll();	
	    List<Webpages> webpages = webpagesService.findAll();	    
	    List<Classification> classification = classificationService.findAll();	 
	    List<Img> img = imgService.findAll();		    	
	    mav.addObject("category", category);
	    mav.addObject("webpages", webpages);
	    mav.addObject("classification", classification);
	    mav.addObject("img", img);
	    return mav;
	}
/*	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveItems(@ModelAttribute("items") Items items) throws LogicException {
		itemsService.save(items);
	    return "redirect:/items/list";
	} */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveItems(HttpServletRequest req, @RequestParam(required=false) MultipartFile file) throws LogicException {
		Items items= getItems(req);
		if (file!=null) { 						
			try {
		     String fileupload = ContextKeeper.getServletContext().getInitParameter("upload-img-shoes");
		     String fileuploadTarget = ContextKeeper.getServletContext().getInitParameter("upload-img-shoes-target");
		     String filename=Files.saveMultipartFileName(file, fileupload, fileuploadTarget);
		     if (filename!=null) {Img img = new Img(); img.setUrl("/img-shoes/"+filename);
		       img.setId(imgService.saveGetId(img));
		       if (null==items.getImg())  {items.setImg(new ArrayList<>());}
		       items.getImg().add(img);
		     } 
			} catch (Exception e) {    e.printStackTrace();
			System.out.println("================IMG WAS NOT LOADED");
			}
		}	
		itemsService.save(items);
	    return "redirect:/items/list";
	    
	}
		
	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteItems(@RequestParam long []id) throws LogicException {
		for (long i:id) {itemsService.delete(i);}		
	    return "redirect:/items/list";       
	}
	/*
	@RequestMapping("/search")
	public ModelAndView search(@RequestParam String keyword) throws LogicException {
	    List<Items> result = itemsService.search(keyword);
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("items/search");	    
        modelAndView.addObject("result", result);	 
	    return modelAndView;    
	}	
	@RequestMapping("/new")
	public String newCustomerForm(Map<String, Object> model) {
		Items items = new Items();
	    model.put("items", items);
	    return "items/new";
	}
	*/
/////////////////////////////////////////////////////////
	private Items getItems(HttpServletRequest req) {
		String sid; 
	 	   try {sid = new String (req.getParameter("id"));}
	 	   catch (NullPointerException e){sid ="";}
	 	   
			   Long id = null;
	 	   try {id = Long.parseLong(sid);}
			   catch (IllegalArgumentException err) {}
	 	   
	 	   Items product = new Items();    	  
	 	   String articul;
	 	   String model;
	 	   Long category;
	 	  Long webpages;
	 	   Long baseprice;
	 	   Integer discount;	
	 	   String name;
	 	   
	 	   articul=req.getParameter("articul"); product.setArticul(articul); 
	 	   if(articul == null || articul.isBlank()) {throw new IllegalArgumentException();}
	 	   model=req.getParameter("model"); product.setModel(model);
	 	   if(model == null || model.isBlank()) {throw new IllegalArgumentException();}
	 	   category= Long.parseLong(req.getParameter("category")); 
	 	   if(category == null || category<=0) {throw new IllegalArgumentException();}
	 	   product.setCategory(new Category()); product.getCategory().setId(category);

	 	  webpages= Long.parseLong(req.getParameter("webpages")); 
		   if(webpages<=0) {webpages = null;}
		   if (webpages != null) {
			   product.setWebpages( new Webpages()); product.getWebpages().setId(webpages);
			   product.getWebpages().setEntity(Params.WP_ITEMS); product.getWebpages().setEntityid(id);
		   } 	   
	 	   
	 	  try {baseprice= Long.parseLong(req.getParameter("baseprice")); }
			   catch (NullPointerException | NumberFormatException err) {baseprice =null;}
	 	     if(baseprice == null || baseprice<0) {baseprice=0L; }
	      product.setBaseprice(baseprice);  	
	 	    	   
	 	   try { discount= Integer.parseInt(req.getParameter("discount"));}
		   catch (NullPointerException | NumberFormatException err) {discount =null;}
	 	   if(discount == null || discount<0) {discount=0; }; 
	 	   product.setDiscount(discount);
	 	   
	 	   name=req.getParameter("name"); product.setName(name);  
		   if(name == null || name.isBlank()) {throw new IllegalArgumentException();}
	 	   
		   Boolean active=true;
		   String bactive = req.getParameter("active");
		   try { active= Boolean.parseBoolean(bactive);} catch (NullPointerException err) {active =false;}
		   if (bactive!=null) {
			   active=true; //  if (bactive.equals("checked")) {active=true;}  ///active=true; ////!!!!!!!!!!!!------------
		   }
		//   System.out.println("==================bactive"+bactive);
	 	   product.setActive(active); 
		   
	 	  String imgs[]=  req.getParameterValues("img"); 	    	   
		  if (imgs!=null) {
	 	  ArrayList<Img> imgIds = new ArrayList<>(); 
		   for(int i=0; i<imgs.length;i++) {
			   try { 
				   Img tImg = new Img(); tImg.setId(Long.parseLong(imgs[i])); 	    		    
				   imgIds.add(tImg);
			      } catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}
			   }	    	       	 	    	   
		   product.setImg(imgIds);  
		  }
		 	  String clsfs[]=  req.getParameterValues("classification"); 	    	   
			   ArrayList<Classification> clsfsIds = new ArrayList<>(); 
			   for(int i=0; i<clsfs.length;i++) {
				   try { 
					   Classification tImg = new Classification(); tImg.setId(Long.parseLong(clsfs[i])); 	    		    
					   clsfsIds.add(tImg);
				      } catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}
				   }	    	       	 	    	   
			   product.setClassification(clsfsIds);  
		   
		   product.setId(id); 	    	   		   
		   return product;
	}
}
