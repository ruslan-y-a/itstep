package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ui.Factory;
import service.DBService;
import postgres.DaoException;
//import tabs.Classification;
import tabs.Entity;
//import tabs.Itemcatgory;
import tabs.Items;
import java.util.List;
import service.LogicException;

public class SaveItemServlet  extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2717945169636526958L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       try {
	  
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
    	   Long baseprice;
    	   Integer discount;	
    	   
    	   articul=req.getParameter("articul"); product.put("articul", articul);
    	   if(articul == null || articul.isBlank()) {throw new IllegalArgumentException();}
    	   model=req.getParameter("model"); product.put("model", model);
    	   if(model == null || model.isBlank()) {throw new IllegalArgumentException();}
    	   category= Long.parseLong(req.getParameter("category")); product.put("category", category);
    	   if(category == null || category<=0) {throw new IllegalArgumentException();}
  
    	   baseprice= Long.parseLong(req.getParameter("baseprice")); product.put("baseprice", baseprice);
    	   if(baseprice == null || baseprice<0) {baseprice=0L; }   	   
    	   discount= Integer.parseInt(req.getParameter("discount")); product.put("discount", discount);
    	   if(discount == null || discount<0) {discount=0; }; 
    	   
    	   String title = new String(req.getParameter("title")); if (title.isBlank()) {title=null;}
    	   product.put("title", title);
    	   
    	   String text = new String(req.getParameter("text")); if (text.isBlank()) {text=null;}
    	   product.put("text", text);
    	   
    	   String name = new String(req.getParameter("name")); if (name.isBlank()) {name=null;}
    	   product.put("name",name);
    	   
    	   String description = new String(req.getParameter("description")); if (description.isBlank()) {description=null;}
    	   product.put("description", description);
    	   
    	   String keywords = new String(req.getParameter("keywords")); if (keywords.isBlank()) {keywords=null;}
    	   product.put("keywords",keywords);
    	    
    	   String mainimgurl = new String(req.getParameter("mainimgurl")); if (mainimgurl.isBlank()) {mainimgurl=null;}
    	   product.put("mainimgurl", mainimgurl);
    	   
    	   String url = new String(req.getParameter("url")); if (url.isBlank()) {url=null;}
    	   product.put("url", url);    
    	   
    	   product.setDBName("items");
    	   product.DBsetId(id); 
    	   product.put("id", id); 
    	   
    	   try(Factory factory = new Factory()) {
    		   DBService service = factory.getDBService();    		   
				Long idItem=service.save(product);

////////////////////////////
			  service.updateClassification(idItem,category); 
//////////////////////////
			  if (req.getParameter("addparams")==null) {	
				resp.sendRedirect("list.html"); 
			  } else {
				  
				  List<Entity> list=service.read("classification");			  
                  req.setAttribute("classification", list);
                  product.cast();
                  product.DBsetId(idItem);
                  req.setAttribute("product", product);
				  req.getRequestDispatcher("/WEB-INF/jsp/items/addparams.jsp").forward(req, resp);
			    }
			} catch(LogicException e) {
				throw new ServletException(e);
			}
    	   
      	} catch(NumberFormatException | DaoException e) {
		throw new ServletException(e);
	    }

     }
}	