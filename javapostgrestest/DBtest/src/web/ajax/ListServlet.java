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
import de_services.ItemsService;
import factories.Factory;
import entities.Entity;
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
			
			if (search!=null) {search = URLDecoder.decode(search, "UTF-8");}
			uri = uri.substring(req.getContextPath().length());		
			if(uri.endsWith(".json")) {
				uri = uri.substring(0, uri.length() - ".json".length());			
			}						
			Object olist = null;
			Integer serchpos = uri.indexOf("/items/search");			
			BaseService<?> service;			
			
			if (serchpos>=0 ) {								
				service = factory.getService("items");
				List<Items> iList = ((ItemsService)service).search(search);
				olist = iList;	
			} else {
			List<Entity> list = new ArrayList<>();	
			String Arr[]=uri.substring(1).split("/");	
			service = factory.getService(Arr[0]);
						
			int qPos=search.indexOf("=");
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
				//  if (entity!=null) {list.add(entity);}
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