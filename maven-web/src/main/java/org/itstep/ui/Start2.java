package org.itstep.ui;


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

import org.itstep.daos.CategoryDaoImpl;
import org.itstep.daos.ClassificationDaoImpl;
import org.itstep.daos.ClientDaoImpl;
import org.itstep.daos.ItemsDaoImpl;
import org.itstep.daos.OrdersDaoImpl;
import org.itstep.daos.TagcloudDaoImpl;
import org.itstep.de_services.BaseitemServiceImpl;
import org.itstep.de_services.CategoryServiceImpl;
import org.itstep.de_services.ClassificationServiceImpl;
import org.itstep.de_services.ClientServiceImpl;
import org.itstep.de_services.CurrencyServiceImpl;
import org.itstep.de_services.ImgServiceImpl;
import org.itstep.de_services.ItemsServiceImpl;
import org.itstep.de_services.OrdersServiceImpl;
import org.itstep.de_services.SaleServiceImpl;
import org.itstep.postgres.DaoException;
import org.itstep.service.LogicException;
import org.itstep.web.action.BaseAction;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Category;
import org.itstep.entities.Classification;
import org.itstep.entities.Client;
import org.itstep.entities.Color;
import org.itstep.entities.Currency;
import org.itstep.entities.Entity;
import org.itstep.entities.Img;
import org.itstep.entities.Items;
import org.itstep.entities.ItemsSort;
import org.itstep.entities.Orders;
import org.itstep.entities.Orderstatus;
import org.itstep.entities.Sale;
import org.itstep.entities.Tagcloud;
import org.itstep.entities.Variant;
import org.itstep.entities.Webpages;
import org.itstep.help.Reflection;

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
