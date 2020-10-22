package org.itstep.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.itstep.config.ContextKeeper;
import org.itstep.csvLoader.CsvLoader;
import org.itstep.entities.Role;
import org.itstep.entities.User;
import org.springframework.web.context.WebApplicationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SequrityFilter implements Filter {
	private static final Logger logger = LogManager.getLogger();
    private Boolean chkWhiteUrls(String uri) {
    	for(String x: whiteURLs) {
    		if (uri.indexOf(x)>=0) {return true;}
    	}
    	return false;
    }
	
	private static Set<String> whiteURLs = new HashSet<>();
	private static Map<Role, Set<String>> accessURLs = new HashMap<>();
	private static Set<String> adminURLs = new HashSet<>();
	private static Set<String> managerURLs = new HashSet<>();
	private static Set<String> productURLs = new HashSet<>();		
	private static Set<String> couriesURLs = new HashSet<>(); 			
	private static Set<String> cashierURLs = new HashSet<>(); 	
	private static Set<String> clientURLs = new HashSet<>(); 
	
	static {		
		try {
			ServletContext context = ContextKeeper.getServletContext();
			String fileupload = context.getInitParameter("file-upload");					
			Map<String,ArrayList<String>> map = loadRole(fileupload + "rolepages.csv");
			
			whiteURLs = map.get("whiteURL").stream().collect(Collectors.toSet());
			adminURLs = map.get("adminURL").stream().collect(Collectors.toSet());
			managerURLs = map.get("managerURL").stream().collect(Collectors.toSet());
			couriesURLs = map.get("couriesURL").stream().collect(Collectors.toSet());
			cashierURLs = map.get("cashierURL").stream().collect(Collectors.toSet());
			clientURLs = map.get("clientURL").stream().collect(Collectors.toSet());
			productURLs = map.get("productURL").stream().collect(Collectors.toSet());
			
			System.out.println("=====================ROLES LOADED");
			
		} catch (Exception e) {	e.printStackTrace();}		
	}
/*	
	static {
		whiteURLs.add("/");
		whiteURLs.add("/index.html");  
		whiteURLs.add("/login.html");  			
		whiteURLs.add("/catalog.html");
		whiteURLs.add("/search.html");
		whiteURLs.add("/product.html");	
		whiteURLs.add("/product/list.html");
		whiteURLs.add("/catalog/list.html");		
		whiteURLs.add("/about.html");
		whiteURLs.add("/terms.html");
		whiteURLs.add("/contact.html");
		whiteURLs.add("/register.html");
		whiteURLs.add("/registeruser.html");
		whiteURLs.add("/catalog/search.html");
	}
*/
	static {
		accessURLs.put(Role.CLIENT, clientURLs);
		accessURLs.put(Role.COURIER, couriesURLs);
		accessURLs.put(Role.CASHIER, cashierURLs);
		accessURLs.put(Role.PRODUCT, productURLs);
		accessURLs.put(Role.MANAGER, managerURLs);
		accessURLs.put(Role.ADMIN, adminURLs);	
/*
		clientURLs.add("/users/page.html");
		Set<String> adminURLs = new HashSet<>();
		Set<String> managerURLs = new HashSet<>();
		Set<String> productURLs = new HashSet<>();		
		Set<String> couriesURLs = new HashSet<>(); 			
		Set<String> cashierURLs = new HashSet<>(); 	
		Set<String> clientURLs = new HashSet<>(); 		
		productURLs.add("/category/edit.html");
		productURLs.add("/category/save.html");
		productURLs.add("/category/delete.html");
		productURLs.add("/category/list.html");
		productURLs.add("/baseitem/edit.html");
		productURLs.add("/baseitem/save.html");
		productURLs.add("/baseitem/delete.html");
		productURLs.add("/baseitem/list.html");
		productURLs.add("/classification/edit.html");
		productURLs.add("/classification/save.html");
		productURLs.add("/classification/delete.html");
		productURLs.add("/classification/list.html");
		productURLs.add("/items/edit.html");
		productURLs.add("/items/save.html");
		productURLs.add("/items/delete.html");
		productURLs.add("/items/list.html");
		productURLs.add("/size/edit.html");
		productURLs.add("/size/save.html");
		productURLs.add("/size/delete.html");
		productURLs.add("/size/list.html");
		productURLs.add("/color/edit.html");
		productURLs.add("/color/save.html");
		productURLs.add("/color/delete.html");
		productURLs.add("/color/list.html");
		productURLs.add("/backoffice.html");
		productURLs.add("/img/edit.html");
		productURLs.add("/img/save.html");
		productURLs.add("/img/delete.html");
		productURLs.add("/img/list.html");
		productURLs.add("/upload.html");
		
		couriesURLs.add("/backoffice.html");
		couriesURLs.add("/orders/edit.html");
		couriesURLs.add("/orders/save.html");
		couriesURLs.add("/orders/delete.html");
		couriesURLs.add("/orders/list.html");
		
		cashierURLs.add("/backoffice.html");
		cashierURLs.add("/sale/edit.html");
		cashierURLs.add("/sale/save.html");
		cashierURLs.add("/sale/delete.html");
		cashierURLs.add("/sale/list.html");
	*/
	
		
		/*	
		 * 
		   FileOutputStream fos=null;
		  OutputStreamWriter osw=null;
		  BufferedWriter bw=null;
		  try {
		      fos = new FileOutputStream(file);
			  osw = new OutputStreamWriter(fos, "UTF-8");
			  bw = new BufferedWriter(osw);	
			    } catch(IOException | IllegalArgumentException e) {
			  System.out.println("Ошибка программы");
			   }
		       if(bw != null) {
				 try { bw.close(); } catch(IOException e) {}
			    } else if(osw != null) {
			 	 try { osw.close(); } catch(IOException e) {}
			    } else if(fos != null) {
				 try { fos.close(); } catch(IOException e) {} 
				}
			  
			  
		 * 
		 * 	CsvLoader csvLoader = new CsvLoader("/rolepages.csv");
		try {
			Map<String,ArrayList<String>> map=csvLoader.Load();
			map.get("ADMIN").forEach(x-> adminURLs.add(x));
			map.get("MANAGER").forEach(x-> managerURLs.add(x));
			map.get("PRODUCT").forEach(x-> productURLs.add(x));
		} catch (ClassNotFoundException | IOException e) {			
			e.printStackTrace(); }
		 */
/*	
	    adminURLs.add("/backoffice.css");
	    adminURLs.add("/backoffice.html");
		adminURLs.add("/users/edit.html");
		adminURLs.add("/users/save.html");
		adminURLs.add("/users/delete.html");
		adminURLs.add("/users/list.html");
		adminURLs.add("/category/edit.html");
		adminURLs.add("/category/save.html");
		adminURLs.add("/category/delete.html");
		adminURLs.add("/category/list.html");
		adminURLs.add("/baseitem/edit.html");
		adminURLs.add("/baseitem/save.html");
		adminURLs.add("/baseitem/delete.html");
		adminURLs.add("/baseitem/list.html");
		adminURLs.add("/classification/edit.html");
		adminURLs.add("/classification/save.html");
		adminURLs.add("/classification/delete.html");
		adminURLs.add("/classification/list.html");
		adminURLs.add("/webpages/edit.html");
		adminURLs.add("/webpages/save.html");
		adminURLs.add("/webpages/delete.html");
		adminURLs.add("/webpages/list.html");
		adminURLs.add("/currency/edit.html");
		adminURLs.add("/currency/save.html");
		adminURLs.add("/currency/delete.html");
		adminURLs.add("/currency/list.html");
		adminURLs.add("/client/edit.html");
		adminURLs.add("/client/save.html");
		adminURLs.add("/client/delete.html");
		adminURLs.add("/client/list.html");
		adminURLs.add("/color/edit.html");
		adminURLs.add("/color/save.html");
		adminURLs.add("/color/delete.html");
		adminURLs.add("/color/list.html");
		adminURLs.add("/country/edit.html");
		adminURLs.add("/country/save.html");
		adminURLs.add("/country/delete.html");
		adminURLs.add("/country/list.html");
		adminURLs.add("/img/edit.html");
		adminURLs.add("/img/save.html");
		adminURLs.add("/img/delete.html");
		adminURLs.add("/img/list.html");
		adminURLs.add("/items/edit.html");
		adminURLs.add("/items/save.html");
		adminURLs.add("/items/delete.html");
		adminURLs.add("/items/list.html");	
		adminURLs.add("/orders/edit.html");
		adminURLs.add("/orders/save.html");
		adminURLs.add("/orders/delete.html");
		adminURLs.add("/orders/list.html");	
		adminURLs.add("/sale/edit.html");
		adminURLs.add("/sale/save.html");
		adminURLs.add("/sale/delete.html");
		adminURLs.add("/sale/list.html");
		adminURLs.add("/size/edit.html");
		adminURLs.add("/size/save.html");
		adminURLs.add("/size/delete.html");
		adminURLs.add("/size/list.html");
		adminURLs.add("/tagcloud/edit.html");
		adminURLs.add("/tagcloud/save.html");
		adminURLs.add("/tagcloud/delete.html");
		adminURLs.add("/tagcloud/list.html");
		adminURLs.add("/upload.html");

		for(Set<String> userURLs : accessURLs.values()) {
			userURLs.add("/logout.html");
			userURLs.add("/user.html");
			userURLs.add("/users/cart.html");
			userURLs.add("/users/order.html");
			userURLs.add("/users/checkout.html");
			userURLs.add("/users/deactiveorder.html");
			userURLs.add("/users/uncheckout.html");
			userURLs.add("/product/order.html");
		}
		
*/		
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String uri = request.getRequestURI();
		uri = uri.substring(request.getContextPath().length());		
		boolean access = false;
	//	System.out.println("=====================)"+uri);
		if(!whiteURLs.contains(uri) && uri.indexOf("catalog")==-1 && uri.indexOf("img-shoes")==-1 && uri.indexOf("script")==-1  && uri.indexOf("images")==-1) {
	//	if(chkWhiteUrls(uri)) {	
			HttpSession session = request.getSession(false);
			if(session != null) {
				User user = (User)session.getAttribute("sessionUser");
				if(user != null) {
					Set<String> urls = accessURLs.get(user.getRole());
					if(urls.contains(uri)) {
						access = true;
					}
				}
			}
		} else {
			access = true;
		}
		if(access) {
			chain.doFilter(req, resp);
		} else {
			//request.getPathInfo()
			logger.warn(String.format("Access is denied for the request on URI \"%s\" from the client %s", uri, req.getLocalAddr()));
			response.sendRedirect(request.getContextPath() + "/login.html?message=" + URLEncoder.encode("Access Denied", "UTF-8"));
		}
	}
///////////////////////////////////////////////////////////////////////////
	private static Map<String,ArrayList<String>> loadRole(String fileupload){
		  File file= new File(fileupload); if (!file.exists()) {return null;}
		  Map<String,ArrayList<String>> map=null;
			  CsvLoader csvLoader = new CsvLoader(file);				
			  try {
				  map=csvLoader.Load();
			  } catch (ClassNotFoundException | IOException e) {e.printStackTrace();}
			  		
		return map;
	}
}