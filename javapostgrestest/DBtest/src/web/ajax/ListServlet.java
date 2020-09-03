package web.ajax;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import de_services.BaseService;
import de_services.CategoryService;
import de_services.ClientService;
import de_services.ItemsService;
import de_services.OrdersService;
import factories.Factory;
import entities.Category;
import entities.Entity;
import entities.Orders;
import entities.Items;
import service.LogicException;

public class ListServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5809000526961602603L;
	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try(Factory factory = new Factory()) {
			String uri = req.getRequestURI();
			String search = req.getQueryString();
			CategoryService cService;
			
			if (search!=null) {search = URLDecoder.decode(search, "UTF-8");}
			uri = uri.substring(req.getContextPath().length());		
			if(uri.endsWith(".json")) {
				uri = uri.substring(0, uri.length() - ".json".length());			
			}						
			Object olist = null;
			Integer serchpos = uri.indexOf("/items/search");			
			BaseService<?> service;			
			
			String categoryParentID = req.getParameter("parent");
			String categoryID = req.getParameter("category");
			
			if (serchpos>=0 ) {								
				service = factory.getService("items");
				List<Items> iList = ((ItemsService)service).search(search);
				olist = iList;	
			} else if (uri.indexOf("users/cart")>=0) {
				OrdersService service1 = factory.getOrdersService(); Long oid=null;
				ItemsService service2 = factory.getItemsService(); 				
				try {oid=Long.parseLong(search);} catch (NumberFormatException | NullPointerException e) {}
				if (oid!=null) {ClientService service3 = factory.getClientService(); 
				oid=service3.readByUserId(oid, true);}
				List<Orders> list = service1.orderdItems(oid);
				list.forEach(x-> {
					Items mItem=null;
					try {
						mItem = service2.read(x.getBaseitem().getItem().getId());
					} catch (LogicException e) {					
						e.printStackTrace();}	
					x.getBaseitem().setItem(mItem);
				});	
				 olist =list;
/////////////////////////////////////////////////////////////////////////////////////				 
			} else if (categoryParentID!=null && !categoryParentID.isBlank()) {
				cService = factory.getCategoryService();			
				try {Long cpid=Long.parseLong(categoryParentID);
				 List<Category> cplist =cService.readByParent(cpid);
				 olist = cplist;	
				} catch (NumberFormatException e) {}
			} else if (categoryID!=null && !categoryID.isBlank()) {
				cService = factory.getCategoryService();
				try {Long cpid=Long.parseLong(categoryID);
				 Category category =cService.read(cpid);
				 olist = category;	
				} catch (NumberFormatException e) {}
//////////////////////////////////////////////////////////////////////////////////////////////				
		   } else {	
			List<Entity> list = new ArrayList<>();						
			String Arr[]=uri.substring(1).split("/");	
			service = factory.getService(Arr[0]);						
			int qPos=0; try {qPos=search.indexOf("=");} catch (NullPointerException e) {}
			  if (qPos>0) {
				  String Arr1[]=search.split("=");
				  Long aid=null; Entity entity=null;
									
				  try {
					  if (Arr1[0].equals("id")) {
					  aid=Long.parseLong(Arr1[1]);
					//  System.out.println("==============aid)"+aid);
					   entity = service.read(aid); }
					//  System.out.println("==============entity)"+entity);
				  } catch (NullPointerException | NumberFormatException e) {
					  resp.sendError(500);
				  }				  	
				  olist =entity;
			  } else {
			  list = (List<Entity>)service.findAll();
			  olist =list;
			 }			  			  
			}
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(resp.getOutputStream(), olist);
		} catch(LogicException e) {
			resp.sendError(500);
		}
	}
}