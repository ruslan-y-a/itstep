package org.itstep.web.ajax;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.itstep.config.ContextKeeper;
import org.itstep.controller_main.Mapping;
import org.itstep.de_services.BaseService;
import org.itstep.de_services.CategoryService;
import org.itstep.de_services.ClientService;
import org.itstep.de_services.ItemsService;
import org.itstep.de_services.ItemsServiceImpl;
import org.itstep.de_services.OrdersService;
import org.itstep.entities.Category;
import org.itstep.entities.Orders;
import org.itstep.entities.Items;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class ListServlet extends HttpServlet {
	/**
	 * 
	 */
	@Autowired private WebApplicationContext context;
	
	public void setWebApplicationContext(WebApplicationContext context) {
		this.context = context;
	}

	private static final long serialVersionUID = -5809000526961602603L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
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
			BaseService <?>service;			
			
			String categoryParentID = req.getParameter("parent");
			String categoryID = req.getParameter("category");
			context = ContextKeeper.getContext(); //WebApplicationContext 
			ItemsService iservice=(ItemsService) context.getBean(ItemsServiceImpl.class);	
			if (serchpos>=0 ) {													
				List<Items> iList = iservice.search(search);
				olist = iList;	
			} else if (uri.indexOf("users/cart")>=0) {
			
				OrdersService service1 = context.getBean(OrdersService.class);	Long oid=null;
									
				try {oid=Long.parseLong(search);} catch (NumberFormatException | NullPointerException e) {}
				if (oid!=null) {
					ClientService service3 = context.getBean(ClientService.class);   
				oid=service3.readByUserId(oid, true);}
				List<Orders> list = service1.orderdItems(oid);
				list.forEach(x-> {
					Items mItem=null;
					try {
						mItem = iservice.findById(x.getBaseitem().getItem().getId());
					} catch (LogicException e) {					
						e.printStackTrace();}	
					x.getBaseitem().setItem(mItem);
				});	
				 olist =list;
/////////////////////////////////////////////////////////////////////////////////////				 
			} else if (categoryParentID!=null && !categoryParentID.isBlank()) {
				cService = context.getBean(CategoryService.class); 			
				try {Long cpid=Long.parseLong(categoryParentID);
				 List<Category> cplist =cService.readByParent(cpid);
				 olist = cplist;	
				} catch (NumberFormatException e) {}
			} else if (categoryID!=null && !categoryID.isBlank()) {
				cService = context.getBean(CategoryService.class);							
				try {Long cpid=Long.parseLong(categoryID);
				 Category category =cService.findById(cpid);
				 olist = category;	
				} catch (NumberFormatException e) {}
//////////////////////////////////////////////////////////////////////////////////////////////				
		   } else {	
							
			String Arr[]=uri.substring(1).split("/");	
			Class<?> cl = Mapping.getServiceImpl(Arr[0]);	
			List<?> list = new ArrayList<>();		
		//	Class<?> clEntity = Mapping.getEntity(Arr[0]);
			
			service = (BaseService <?>) context.getBean(cl); 	
			int qPos=0; try {qPos=search.indexOf("=");} catch (NullPointerException e) {}
			  if (qPos>0) {
				  String Arr1[]=search.split("=");
				  Long aid=null; Object entity=null;
									
				  try {
					  if (Arr1[0].equals("id")) {
					  aid=Long.parseLong(Arr1[1]);					
					  entity = service.findById(aid); }
				
				  } catch (NullPointerException | NumberFormatException e) {
					  resp.sendError(500);
				  }				  	
				  olist =entity;
			  } else {
			  list = service.findAll();
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