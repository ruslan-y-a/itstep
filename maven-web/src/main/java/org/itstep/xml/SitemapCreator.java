package org.itstep.xml;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletContext;
/*
import javax.xml.parsers.ParserConfigurationException;
import org.itstep.de_services.ItemsService;
import org.itstep.de_services.TagcloudService;
import org.springframework.beans.factory.BeanFactory;
*/
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
/*import org.itstep.de_services.WebpagesService;
import org.itstep.config.ConnectionThreadHolder;
import org.itstep.de_services.WebpagesServiceImpl;
import org.itstep.service.LogicException;*/
import org.itstep.config.ContextKeeper;
import org.itstep.daos.DaoException;
import org.itstep.daos.WebpagesDaoImpl;


import org.itstep.pool.ConnectionPool;

import org.springframework.web.context.WebApplicationContext;

public class SitemapCreator {
	private String mainNode="urlset";
    private String sFile, sFile2;
    private List<String> staticUrls=null;
    private XmlMaker xmlmaker;
    private String fileupload="http://www.sitemaps.org/schemas/sitemap/0.9";        
    public void setFileupload(String fileupload) {this.fileupload = fileupload;}

	public SitemapCreator() throws Exception{		
    	
    	WebApplicationContext webcontext = ContextKeeper.getContext();	
		ServletContext context = ContextKeeper.getServletContext();
		sFile = context.getInitParameter("xml-path"); //+ sFile
		sFile2 = context.getInitParameter("xml-path-target");
		ConnectionPool.getInstance().init("org.postgresql.Driver", "jdbc:postgresql://localhost/ishop", "root", "root", 5, 20, 1000);
		Connection connection = ConnectionPool.getInstance().getConnection();
		WebpagesDaoImpl iservice=webcontext.getBean(WebpagesDaoImpl.class);		
		iservice.setConnection(connection);		
	//	System.out.println("-----------------" + connection);
		  try {
			  staticUrls=  iservice.read().stream().map(x -> x.getUrl()).collect(Collectors.toList());
		   } catch (DaoException e) {e.printStackTrace();}
    	}
		
    	public SitemapCreator( List<String> staticUrls) throws Exception{
    		this.staticUrls = staticUrls;	
    		xmlmaker= new XmlMaker(fileupload, mainNode);    		
    	}
    	
    	public File saveXml() throws Exception {
    		   xmlmaker= new XmlMaker(fileupload, mainNode);
  		  xmlmaker.addNewChildren("url",addXmlUrlParam("/terms"));
		  xmlmaker.addNewChildren("url",addXmlUrlParam("/about"));
     	  xmlmaker.addNewChildren("url",addXmlUrlParam("/contact"));
		  xmlmaker.addNewChildren("url",addXmlUrlParam("/catalog/list"));
		  xmlmaker.addNewChildren("url",addXmlUrlParam("/"));
		  if (staticUrls!=null && staticUrls.size()>0) {
			 staticUrls.forEach((x) -> { xmlmaker.addNewChildren("url",addXmlUrlParam("/catalog/category/" + x)); }); 
		   }	
		 
		  try {
			xmlmaker.saveXml(sFile + "sitemap.xml");
		  } catch (TransformerException e) {e.printStackTrace();
		   throw new Exception(e);
		 }	
		 File srcFile = new File(sFile + "sitemap.xml"); 
		 File copied = new File(sFile2 + "sitemap.xml");
		 FileUtils.copyFile(srcFile, copied); 
		 return copied;
	}
	
    private Map<String, String> addXmlUrlParam(String url) {
    	Map<String, String> urlparam = new HashMap<String, String>();
    	urlparam.put("loc", url);
    	urlparam.put("changefreq", "weekly");
    	urlparam.put("priority","1.0");
    	return urlparam;
    }
}
