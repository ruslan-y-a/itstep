package org.itstep.xml;

import java.sql.Connection;
/*
import java.util.Date;
import java.util.List;
import org.itstep.de_services.ItemsService;
import org.itstep.entities.Currency;
import org.itstep.entities.Rate;
import org.itstep.help.DateHelp;
import org.itstep.service.CurrencyService;
import org.itstep.service.RateService;
*/
import javax.servlet.ServletContext;
import org.itstep.config.ConnectionThreadHolder;
import org.itstep.config.ContextKeeper;
import org.itstep.de_services.ItemsServiceImpl;
import org.itstep.pool.ConnectionPool;
import org.springframework.web.context.WebApplicationContext;

public class MakeSitemap {

	public MakeSitemap() {
		SitemapCreator smc;
		try {
			ServletContext context = ContextKeeper.getServletContext();
		//	System.out.println("====================call");
			if (context==null) {return;}
			System.out.println("====================MAKING SITEMAP!");
			smc = new SitemapCreator();
			smc.saveXml();		
			
			WebApplicationContext webcontext = ContextKeeper.getContext();
			if (webcontext==null) {System.out.println("====================ITEMS ARE NOT UPDATED!");return;}
			Connection connection = ConnectionThreadHolder.getConnection();
			if (connection==null) {
				ConnectionPool.getInstance().init("org.postgresql.Driver", "jdbc:postgresql://localhost/ishop", "root", "root", 5, 20, 1000);
			    connection = ConnectionPool.getInstance().getConnection();
				ConnectionThreadHolder.setConnection(connection);
			}
			ItemsServiceImpl iservice=webcontext.getBean(ItemsServiceImpl.class);	
			System.out.println("====================UPDATING ITEMS STATUS: " + iservice.updateItemsStatus());
		/*				
			RateService iservice2=webcontext.getBean(RateService.class);
			List<Rate> list = iservice2.findAll();
			   for(Rate c:list) {
				   c.setRate(c.getRate()-0.1); 
				   c.setDate(DateHelp.getYesterday(new Date(),1));				   
				   iservice2.save(c);
			   }
			*/
			
		} catch (Exception e) {			
			e.printStackTrace();
			System.out.println("====================ALERT! SITEMAP WAS NOT CREATED!");
		}
	
	}

}
