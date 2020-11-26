package org.itstep.tools;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
//import org.jsoup.nodes.DataNode;
//import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Parser {
			
	 public static Map<String, String> getCurrencyMap () throws IOException {
	      Document doc = Jsoup.connect("http://www.nbrb.by/statistics/rates/ratesdaily.asp").get();	
	
	      Elements element = doc.getElementsByClass("currencyTable");	  
	      Map<String, String> cmap = new HashMap<>(); 
	      element.forEach(x -> {
	    	 
	    	  Elements e1 = x.getElementsByTag("tr");	    	  
	    	   e1.forEach(y -> {
	    		   Elements e2 = x.getElementsByTag("td");	    		   
	    		   List<String> list =e2.stream().map(r -> r.text()).collect(Collectors.toList());
	    		   int i=0;
	    		   StringBuilder sb = null;
	    		   while (i< list.size()) {
	    			   sb = new StringBuilder();
	    			   sb.append(list.get(i++)); sb.append(" "); sb.append(list.get(i++));	    			   
	    			   cmap.put(sb.toString(), list.get(i++));	    			   
	    		   }
	    		   
	    		/*   System.out.println("=============cmap size)" + cmap.size());
	    		   for (Map.Entry<String, String> entry: cmap.entrySet())
	    		    {
	    			   System.out.println("=============entry)" + entry.getKey() + " " + entry.getValue());   
	    		   } */
	    		   	    		 
	    	   });
	      });
	      
	      return cmap;
	  }
	 
	 public static String getTitle () throws IOException {
	      Document doc = Jsoup.connect("http://www.nbrb.by/statistics/rates/ratesdaily.asp").get();
	      String title = doc.title();
	      return title;
	 }

}
