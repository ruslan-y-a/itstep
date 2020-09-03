package ui;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import daos.CategoryDaoImpl;
import daos.ClassificationDaoImpl;
import daos.ClientDaoImpl;
import daos.ItemsDaoImpl;
import daos.OrdersDaoImpl;
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
import entities.ItemsSort;
import entities.Orders;
import entities.Orderstatus;
import entities.Sale;
import entities.Tagcloud;
import entities.Variant;
import entities.Webpages;
import factories.Factory;
import help.Reflection;

public class Start2 {
	private static Connection connection = null;
	public static void main(String[] args) throws ClassNotFoundException, SQLException, DaoException, LogicException {
		Class.forName("org.postgresql.Driver");
		
		
		ClassificationDaoImpl classificationDaoImpl = new ClassificationDaoImpl();		
		classificationDaoImpl.setConnection(getConnection());
		
		Classification ids =classificationDaoImpl.readByCategory(13L);
		System.out.println(ids);
							  
		  	 //ids.forEach(x->System.out.println(x));
	
			
			
	}
	
/////////////////////////////////////////////////////////////////////////////////////	
private static List<Long> findCategories(List<Category> categories,Long categoryId,List<Long> ids) {
if (ids==null) {ids = new ArrayList<Long>();}
List<Category> newlist=null;
try {newlist=categories.stream().filter((x)-> x.getParentid()==categoryId).collect(Collectors.toList());}
catch (Exception e) {}
if (newlist==null || newlist.size()==0) {ids.add(categoryId); return ids;}
else {		  
for (Category x :newlist) {
findCategories(newlist,x.getId(),ids);}
}
return ids; 	
}

public static Connection getConnection() throws LogicException {
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
