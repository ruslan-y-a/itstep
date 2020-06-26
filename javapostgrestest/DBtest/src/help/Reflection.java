package help;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import daos.BaseitemDao;
import daos.ItemsDao;
import daos.CategoryDao;
import daos.ClassificationDao;
import daos.ClientDao;
import daos.ColorDao;
import daos.CountryDao;
import daos.CurrencyDao;
import daos.ImgDao;
import daos.OrdersDao;
import daos.SaleDao;
import daos.SizeDao;
import daos.TagcloudDao;
import daos.UserDao;
import daos.WebpagesDao;
import de_services.BaseService;
import postgres.DaoException;

public class Reflection {
	
	
  public static Map<String,Object> getDaos(BaseService<?> BS){
	  Field field=null;  Object list;
	  Map<String,Object> daoMap = new HashMap<>();
	  try {
		  try { 
		   field=null; field = BS.getClass().getDeclaredField("baseitemDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((BaseitemDao)field.get(BS)).read(); daoMap.put("baseitem", list); }
		  } catch (NoSuchFieldException e) {} 
///////////////////////////
		try { 
		  field=null; field = BS.getClass().getDeclaredField("itemsDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((ItemsDao)field.get(BS)).read(); daoMap.put("items", list); }
	  } catch (NoSuchFieldException e) {} 
///////////////////////////////////////
		 try { 
		   field=null; field = BS.getClass().getDeclaredField("colorDao");
	       if (field!=null) { field.setAccessible(true);
	       list = new ArrayList<>();
	       ColorDao colorDao = (ColorDao)field.get(BS);        
	       list= colorDao.read(); daoMap.put("color", list); 	       	      	       
	       }
    } catch (NoSuchFieldException e) {}  
///////////////////////////////////////
    try { 
        field=null; field = BS.getClass().getDeclaredField("sizeDao");
     if (field!=null) { field.setAccessible(true);
     list = new ArrayList<>();
     SizeDao sizeDao = (SizeDao)field.get(BS);       
    list= sizeDao.read(); daoMap.put("size", list); 	       
   	       
}
} catch (NoSuchFieldException e) {}  
///////////////////////////////////////	       
   try { 
		 field=null; field = BS.getClass().getDeclaredField("categoryDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((CategoryDao)field.get(BS)).read(); daoMap.put("category", list); }
    } catch (NoSuchFieldException e) {}     
///////////////////////////////////////	       
     try { 
           field=null; field = BS.getClass().getDeclaredField("classificationDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((ClassificationDao)field.get(BS)).read(); daoMap.put("classification", list); }
	   } catch (NoSuchFieldException e) {}       
///////////////////////////////////////	   	       
     try { 
          field=null; field = BS.getClass().getDeclaredField("clientDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((ClientDao)field.get(BS)).read(); daoMap.put("client", list); }
	   } catch (NoSuchFieldException e) {}      
///////////////////////////////////////	 	 
     try { 
          field=null; field = BS.getClass().getDeclaredField("countryDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((CountryDao)field.get(BS)).read(); daoMap.put("country", list); }
	   } catch (NoSuchFieldException e) {}     
///////////////////////////////////////	
      try {  
          field=null; field = BS.getClass().getDeclaredField("imgDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((ImgDao)field.get(BS)).read(); daoMap.put("img", list); }
	   } catch (NoSuchFieldException e) {}      
///////////////////////////////////////	
      try { 
          field=null; field = BS.getClass().getDeclaredField("currencyDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((CurrencyDao)field.get(BS)).read(); daoMap.put("currency", list); }
      } catch (NoSuchFieldException e) {}	       
///////////////////////////////////////	
      try {       
	       field=null; field = BS.getClass().getDeclaredField("itemsDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	     //  ItemsDao itemsDao=(ItemsDao)field.get(BS);
	       list= ((ItemsDao)field.get(BS)).read(); daoMap.put("items", list); }
	   } catch (NoSuchFieldException e) {} 	       
///////////////////////////////////////	
      try {     
	       field=null; field = BS.getClass().getDeclaredField("ordersDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((OrdersDao)field.get(BS)).read(); daoMap.put("orders", list); }
	   } catch (NoSuchFieldException e) {} 	       
///////////////////////////////////////	
      try {      
	       field=null; field = BS.getClass().getDeclaredField("saleDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((SaleDao)field.get(BS)).read(); daoMap.put("sale", list); }
	   } catch (NoSuchFieldException e) {} 	       
///////////////////////////////////////	
      try {     
	       field=null; field = BS.getClass().getDeclaredField("tagcloudDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((TagcloudDao)field.get(BS)).read(); daoMap.put("tagcloud", list); }
	   } catch (NoSuchFieldException e) {} 	       
///////////////////////////////////////	
      try {     
	       field=null; field = BS.getClass().getDeclaredField("userDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((UserDao)field.get(BS)).read(); daoMap.put("users", list); }
	   } catch (NoSuchFieldException e) {} 	       
///////////////////////////////////////	
      try {      
	       field=null; field = BS.getClass().getDeclaredField("webpagesDao");
	       if (field!=null) {
	       list = new ArrayList<>(); field.setAccessible(true);
	       list= ((WebpagesDao)field.get(BS)).read(); daoMap.put("webpages", list); }
	   } catch (NoSuchFieldException e) {} 	       
///////////////////////////////////////	
	      
	       
	   } catch (IllegalAccessException | IllegalArgumentException | DaoException e) {
	       e.printStackTrace();}
	  return daoMap;
  }
}
