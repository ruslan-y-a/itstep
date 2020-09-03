package org.itstep.ui;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.itstep.csvLoader.CsvLoader;
import org.itstep.de_services.CategoryServiceImpl;
import org.itstep.entities.Category;
import org.itstep.help.Helper;
import org.itstep.postgres.DaoException;
import org.itstep.postgres.DbDaoImpl;
import org.itstep.service.LogicException;
import org.itstep.tabs.Client;
import org.itstep.tabs.Entity;
import org.itstep.tabs.User;

import org.itstep.daos.Dao;
import org.itstep.daos.DaoImpl;
import org.itstep.de_services.BaseService;
import org.itstep.de_services.ItemsServiceImpl;
import org.itstep.entities.Items;
import org.itstep.factories.ImplementationInstantiationIocContainerException;
import org.itstep. web.action.Action;

public class Start {
private static Connection connection = null;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, DaoException, LogicException {
		Class.forName("org.postgresql.Driver");
		/*	Map<String,ArrayList<String>> map;
			CsvLoader csvLoader = new CsvLoader("rolepages");
			map=csvLoader.Load();
			
			Set<String> admin=new LinkedHashSet<>();
			map.get("ADMIN").forEach(x-> admin.add(x));
			admin.forEach(x->System.out.println(x));*/
						
		    CategoryServiceImpl c1= get(CategoryServiceImpl.class);
			List<Category> li= c1.findAll();
			li.forEach(x-> System.out.println(x)); 
	
	}
////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////
	public static Connection getConnection() throws LogicException {
		if(connection == null) {
			try {
			    connection = DriverManager.getConnection("jdbc:postgresql://localhost/ishop", "root", "root");
		      } catch(SQLException e) { throw new LogicException(e);}
		 }
			return connection;
		}
//////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////	
	private static Map<Class<?>, Object> cache = new HashMap<>();
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> i) throws LogicException {
		System.out.println("==================="+i);
		Object o = cache.get(i);
		if(o == null) {
			Class<?> c=null;
			if(i.isInterface()) {
				try {c = Class.forName(i.getName() + "Impl");} catch (ClassNotFoundException e) {}	
			} else {
				c = i;
			}
			if(c != null) {
				try {																				
					  o = c.getConstructor().newInstance();
					  cache.put(i, o);
					  System.out.println("==========Dao========="+o);
					    if (Dao.class.isInstance(o)) {
					    	 System.out.println("==========Dao2========="+o);
					    	 //Method setConnection = o.getClass().getDeclaredMethod("setConnection");					    	 
					    	 Method setConnection = DaoImpl.class.getDeclaredMethod("setConnection", Connection.class);
					    	 setConnection.setAccessible(true);	
					    	 setConnection.invoke(o,getConnection());
						   //  ((DaoImpl<?>)o).setConnection(getConnection());
					    } else if (Action.class.isInstance(o) || BaseService.class.isInstance(o)) {					    
					     //DependencyInjector injector = daoDI;   if(injector != null) {injector.inject(o, this);}
					    	 for (Field field : o.getClass().getDeclaredFields()) {
					    		 Object obj=null;
					    		 field.setAccessible(true);
					    		 System.out.println("==========Dao3========="+field);
					    		// if (Dao.class.isInstance(field.get(o))) {field = get(field.getClass());}
					    		 try {					    								    			 
					    			 obj = get(field.getType());
					    		 } catch (Exception e) 
					    		 {e.printStackTrace(); System.out.println("==========ERROR CREATION=========" + field);}
					    		 Method setter = getSetter(o,field.getName(),field.getType());
					    		 setter.invoke(o,obj);
					    	 } 
					   }
					} catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					   throw new ImplementationInstantiationIocContainerException(e);
					}
			 } else {
			
				 System.out.println("==========Dao FAIL=========");
			}
		}
		return (T)o;
	}
////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////	
	private static Method getSetter(Object obj, String fieldName, Class<?> cl) {
		
		try {             
			  //Method method = obj.getClass().getMethod("set" + fieldName);		
			String s1=fieldName.substring(0, 1).toUpperCase();
			String s2=fieldName.substring(1);
			  Method method = obj.getClass().getDeclaredMethod("set" + s1 + s2,cl);
			         method.setAccessible(true);			  
			         return method;	         
			} catch (Exception ex) { ex.printStackTrace();}
		
		return null;
	}

}
