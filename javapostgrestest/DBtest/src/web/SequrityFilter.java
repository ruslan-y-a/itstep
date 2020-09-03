package web;

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

import csvLoader.CsvLoader;
import tabs.Role;
import entities.User;
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
		/*whiteURLs.add("/index");  
		whiteURLs.add("/login");  
		whiteURLs.add("/user");
		whiteURLs.add("/catalog");
		whiteURLs.add("/search");
		whiteURLs.add("/product");
		whiteURLs.add("/about");
		whiteURLs.add("/terms");
		whiteURLs.add("/contact");*/
	}
	private static Map<Role, Set<String>> accessURLs = new HashMap<>();
	static {
		Set<String> adminURLs = new HashSet<>();
		Set<String> managerURLs = new HashSet<>();
		Set<String> productURLs = new HashSet<>();
			
	//	String absolutePathToIndexJSP = servletContext.getRealPath("/index.jsp");
	//	ServletContext cv = new ServletContext() {};
				
//				getResourcePaths("/")
		/* File file= new File("hui.txt"); ///rolepages.csv		 
		 System.out.println("=====================)"+file);
		 System.out.println("=====================${request.contextPath})"+System.getProperty( "catalina.Path" ));

		 
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
		  	    */		  
		  
	/*	 if (!file.exists()) {System.out.println("--------------------ONO NO File2)"+file);}
		 /*
		 file= Paths.get("\\web\\rolepages.csv").toFile();
		  ali.add(Long.parseLong(ss[++i].replace("\"", "")));}
		 if (!file.exists()) {System.out.println("--------------------ONO NO File2)"+file);}
		*/
			
		/*		CsvLoader csvLoader = new CsvLoader("/rolepages.csv");
		try {
			Map<String,ArrayList<String>> map=csvLoader.Load();
			map.get("ADMIN").forEach(x-> adminURLs.add(x));
			map.get("MANAGER").forEach(x-> managerURLs.add(x));
			map.get("PRODUCT").forEach(x-> productURLs.add(x));
		} catch (ClassNotFoundException | IOException e) {			
			e.printStackTrace(); }
		 */
		accessURLs.put(Role.PRODUCT, productURLs);
		accessURLs.put(Role.MANAGER, managerURLs);
		accessURLs.put(Role.ADMIN, adminURLs);	
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
	
		Set<String> couriesURLs = new HashSet<>();
		accessURLs.put(Role.COURIER, couriesURLs);		
		Set<String> cashierURLs = new HashSet<>();
		accessURLs.put(Role.CASHIER, cashierURLs);
		Set<String> clientURLs = new HashSet<>();
		clientURLs.add("/users/page.html");
		accessURLs.put(Role.CLIENT, clientURLs);
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
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String uri = request.getRequestURI();
		uri = uri.substring(request.getContextPath().length());		
		boolean access = false;
	//	System.out.println("=====================)"+uri);
		if(!whiteURLs.contains(uri) && uri.indexOf("catalog")==-1) {
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
}