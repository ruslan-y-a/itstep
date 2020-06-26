package ui;


import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import daos.ClientDaoImpl;
import daos.TagcloudDaoImpl;
import de_services.BaseitemServiceImpl;
import de_services.CategoryServiceImpl;
import de_services.ClassificationServiceImpl;
import de_services.ClientServiceImpl;
import de_services.CurrencyServiceImpl;
import de_services.ImgServiceImpl;
import de_services.ItemsServiceImpl;
import de_services.OrdersServiceImpl;
import de_services.SaleServiceImpl;
import postgres.DaoException;
import service.LogicException;
import web.action.BaseAction;
import entities.Baseitem;
import entities.Category;
import entities.Classification;
import entities.Client;
import entities.Color;
import entities.Currency;
import entities.Entity;
import entities.Img;
import entities.Items;
import entities.Orders;
import entities.Sale;
import entities.Tagcloud;
import entities.Variant;
import entities.Webpages;
import factories.Factory;
import help.Reflection;

public class Start2 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, DaoException {
		Class.forName("org.postgresql.Driver");
		try(Factory factory = new Factory()) {
			
		//OrdersServiceImpl baseitemServiceImpl = (OrdersServiceImpl)factory.getOrdersService();		
			TagcloudDaoImpl baseitemServiceImpl1 = (TagcloudDaoImpl) factory.getTagcloudDao();		
		/*	List<Classification> list = new ArrayList<>(); 
			Classification cl1 = new Classification(); cl1.setId(8L); list.add(cl1);
			Classification cl2 = new Classification(); cl2.setId(10L); list.add(cl2);
			Tagcloud tagcloud = new Tagcloud();
			tagcloud.setClassification(list);
			tagcloud.setWebpages(new Webpages()); tagcloud.getWebpages().setId(1L);
			baseitemServiceImpl1.create(tagcloud);*/
			
		//	Orders Orders = baseitemServiceImpl.read(1L);
			//List<Tagcloud> list= baseitemServiceImpl1.read();
			Tagcloud tagcloud = baseitemServiceImpl1.read(1L);
			tagcloud.setWebpages(new Webpages()); tagcloud.getWebpages().setId(1L);
		   System.out.println(tagcloud);
		   baseitemServiceImpl1.update(tagcloud);
		   
		//	list.forEach(x->System.out.println(x));
	
			
		} catch(LogicException  e) {
			e.printStackTrace();
		}
	
	}

}
