package org.itstep.ui;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.itstep.config.ConnectionThreadHolder;
import org.itstep.config.ContextKeeper;
import org.itstep.csvLoader.CsvLoader;
import org.itstep.csvupdater.LoadService;
import org.itstep.daos.BaseitemDaoImpl;
import org.itstep.daos.ClassificationDaoImpl;
import org.itstep.daos.CurrencyDaoImpl;
import org.itstep.daos.DaoException;
import org.itstep.daos.ItemsDaoImpl;
import org.itstep.daos.TagcloudDaoImpl;
import org.itstep.daos.WebpagesDaoImpl;
import org.itstep.de_services.CurrencyServiceImpl;
import org.itstep.de_services.ItemsServiceImpl;
import org.itstep.de_services.WebpagesService;
import org.itstep.de_services.WebpagesServiceImpl;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Classification;
import org.itstep.entities.Currency;
import org.itstep.entities.Items;
import org.itstep.entities.ItemsSort;
import org.itstep.entities.Webpages;
import org.itstep.pool.ConnectionPool;
import org.itstep.pool.ConnectionPoolException;
import org.itstep.service.LogicException;
import org.itstep.sqlite.Stats;
import org.itstep.sqlite.StatsDao;
import org.itstep.xml.SitemapCreator;

public class test {
 public static void main(String...args) throws Exception {
/*
	 Connection c = getConnection();
	/// c = ConnectionThreadHolder.getConnection();
		if (c==null) {
			ConnectionPool.getInstance().init("org.postgresql.Driver", "jdbc:postgresql://localhost/ishop", "root", "root", 5, 20, 1000);
		    connection = ConnectionPool.getInstance().getConnection();
			ConnectionThreadHolder.setConnection(connection);
		}
		*/
	 /*
		Set<String> whiteURLs, adminURLs, managerURLs, couriesURLs, cashierURLs, clientURLs, productURLs;
		 try {
			  String fileupload ="c:/Users/Admin/eclipse-workspace/maven-mvc/src/main/webapp/";
				//ServletContext context = ContextKeeper.getServletContext();
				//String fileupload = context.getInitParameter("file-upload");					
				Map<String,ArrayList<String>> map = loadRole(fileupload + "rolepages.csv");
				
				whiteURLs = map.get("whiteURL").stream().collect(Collectors.toSet());
				adminURLs = map.get("adminURL").stream().collect(Collectors.toSet());
				managerURLs = map.get("managerURL").stream().collect(Collectors.toSet());
				couriesURLs = map.get("couriesURL").stream().collect(Collectors.toSet());
				cashierURLs = map.get("cashierURL").stream().collect(Collectors.toSet());
				clientURLs = map.get("clientURL").stream().collect(Collectors.toSet());
				productURLs = map.get("productURL").stream().collect(Collectors.toSet());			
				System.out.println("=====================ROLES LOADED");			
			} catch (Exception e) {	 e.printStackTrace(); throw new LogicException(e);}	
		  
		  Map<String,Set<String>> listUrl = listUrls(null,adminURLs,"ADMIN");
		  listUrl =  listUrls(listUrl,managerURLs,"MANAGER");
		  listUrl =  listUrls(listUrl,couriesURLs,"COURIER");
		  listUrl =  listUrls(listUrl,cashierURLs,"CASHIER");
		  listUrl =  listUrls(listUrl,clientURLs,"CLIENT");
		  listUrl =  listUrls(listUrl,productURLs,"PRODUCT");
		
		  listUrl.forEach((x,y) -> {
			  if (x!=null && !x.isBlank()) {System.out.print(x + " " + setToString(y)); System.out.println();}
			  });  
			  
			  */
 }
///////////////////////////////////////////////////////////////////////////////////////////
 private static Map<String,ArrayList<String>> loadRole(String fileupload){
	   File file= new File(fileupload); if (!file.exists()) {return null;}
	   Map<String,ArrayList<String>> map=null;
	   CsvLoader csvLoader = new CsvLoader(file);				
	   try {
	    map=csvLoader.Load();
	   } catch (ClassNotFoundException | IOException e) {e.printStackTrace();}
	    return map;
	  }  
	private static Map<String,Set<String>> listUrls(Map<String,Set<String>> map, Set<String> URLs, String sRole){
		  if (map==null) {map = new HashMap<>();}
		  String ss;
		  for(String x : URLs) {			 
			  ss = (x.indexOf(".html")>=0? x.substring(0, x.indexOf(".html")):x);
			  Set<String> al = map.get(ss);
			  if (al==null) {al = new HashSet<>();}
			  al.add(sRole);
			  map.put(ss, al);
		  };
		 return map;  
	  }  

	private static String setToString(Set<String> URLs) {
	   StringBuilder sb = new StringBuilder();
	   boolean first=false;
	   for(String x : URLs) {
		     if (first) {sb.append(", ");}; sb.append("'"); sb.append(x); sb.append("'");
		     first=true;	   
		    }	  
		return sb.toString();
	  }
///////////////////////////////////////////////////////////////////////////////////////////
 private static Connection connection;
 private static Connection getConnection() throws LogicException, ClassNotFoundException {
	    Class.forName("org.postgresql.Driver");
		if(connection == null) {
			try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/ishop", "root", "root");
		    } catch(SQLException e) {
			  throw new LogicException(e);
			}
		 }
			return connection;
		}
}