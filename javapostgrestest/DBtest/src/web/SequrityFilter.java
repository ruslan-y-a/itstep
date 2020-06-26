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

public class SequrityFilter implements Filter {
	/*   public static boolean roleAccess(Set<String> list, String url) {
		   boolean b1=(url.contains("list.html") || url.contains("edit.html") || url.contains("save.html") || 
					 url.contains("delete.html"));
		   
		   for (String entry: list) {
			 if (b1 && url.contains(entry)) {return true;}}	  
		   return false;
	   }*/
	private static Set<String> whiteURLs = new HashSet<>();
	static {
		whiteURLs.add("/");
		whiteURLs.add("/index.html");
		whiteURLs.add("/login.html");
		whiteURLs.add("/user.html");		
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
		 System.out.println("=====================)"+file); //new File("/web/WEB-INF/rolepages.csv");
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
		accessURLs.put(Role.CLIENT, clientURLs);
		for(Set<String> userURLs : accessURLs.values()) {
			userURLs.add("/logout.html");
		}
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		String uri = request.getRequestURI();
		uri = uri.substring(request.getContextPath().length());		
		boolean access = false;
		//System.out.println("SECURITY:"+uri);
		if(!whiteURLs.contains(uri)) {
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
			response.sendRedirect(request.getContextPath() + "/login.html?message=" + URLEncoder.encode("Access Denied", "UTF-8"));
		}
	}
}