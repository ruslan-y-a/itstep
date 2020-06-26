package web.action.save;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de_services.ItemsServiceImpl;
import entities.Img;
import service.LogicException;
import entities.Category;
import entities.Classification;
import entities.Items;
import entities.Webpages;
import web.action.ActionException;
import web.action.BaseAction;

public class ItemsSaveAction extends BaseAction {
	
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
	   if (webpages != null) {product.setWebpages( new Webpages()); product.getWebpages().setId(webpages);} 	   
 	   
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
 	   
	   Boolean active;
	   try { active= Boolean.parseBoolean(req.getParameter("active"));}
	   catch (NullPointerException err) {active =false;}
 	   product.setActive(active); 
	   
 	  String imgs[]=  req.getParameterValues("img"); 	    	   
	   ArrayList<Img> imgIds = new ArrayList<>(); 
	   for(int i=0; i<imgs.length;i++) {
		   try { 
			   Img tImg = new Img(); tImg.setId(Long.parseLong(imgs[i])); 	    		    
			   imgIds.add(tImg);
		      } catch (NullPointerException | NumberFormatException err) {throw new IllegalArgumentException();}
		   }	    	       	 	    	   
	   product.setImg(imgIds);  
	   
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
	   ItemsServiceImpl itemsServiceImpl= (ItemsServiceImpl)getService();
	   itemsServiceImpl.save(product);

	   return new Result("/items/list");
	    } catch(IllegalArgumentException e) {
	    throw new ActionException(e, 400);
	     }
  }
}	
