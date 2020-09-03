package jtest;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
import java.util.List;
//import java.util.Map;

//import org.itstep.daos.BaseitemDaoImpl;
import org.itstep.daos.Dao;
import org.itstep.daos.DaoImpl;
import org.itstep.de_services.BaseService;
import org.itstep.de_services.BaseitemServiceImpl;
import org.itstep.de_services.CategoryServiceImpl;
import org.itstep.de_services.ClassificationServiceImpl;
import org.itstep.de_services.ItemsServiceImpl;
import org.itstep.de_services.OrdersServiceImpl;
import org.itstep.de_services.TagcloudServiceImpl;
import org.itstep.de_services.UserServiceImpl;
import org.itstep.de_services.WebpagesServiceImpl;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Category;
import org.itstep.entities.Classification;
import org.itstep.entities.Items;
import org.itstep.entities.ItemsSort;
import org.itstep.entities.Orders;
//import org.itstep.entities.Tagcloud;
import org.itstep.entities.User;
import org.itstep.entities.Webpages;
//import org.itstep.factories.Factory;
import org.itstep.factories.ImplementationInstantiationIocContainerException;
import org.itstep.help.Reflection;
import org.itstep.service.LogicException;
import org.itstep.web.action.Action;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

public class Test {
	
	private static Connection connection = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("TESTING STARTED");
	}
	

	@AfterClass
	public static void tearDownAfterClass() throws Exception {	
		System.out.println("TESTING ENDED");
	}

	@Before
	public void setUp() throws Exception {
		connection=getConnection();
	}

	@After
	public void tearDown() throws Exception {
		try { connection.close();System.out.println("The Connection is closed");} catch(Exception e) {}
		connection=null;
	}

	
	@org.junit.Test
	public void testUrl() {
		WebpagesServiceImpl webpagesServiceImpl=null;
		TagcloudServiceImpl tagcloudServiceImpl=null;
		ItemsServiceImpl itemsServiceImpl=null;
		//Params.WP_TAGCLOUD
		try {
			webpagesServiceImpl = get(WebpagesServiceImpl.class);
			tagcloudServiceImpl= get(TagcloudServiceImpl.class);
			itemsServiceImpl =  get(ItemsServiceImpl.class);
			Webpages wp= webpagesServiceImpl.readEntityId("men-white-textile");
			List<Long> tcl = tagcloudServiceImpl.readByWP(wp.getId());
			List<Integer> tci = new ArrayList<>();  tcl.forEach(x-> tci.add(x.intValue()));
			List<Items> li = itemsServiceImpl.search(tci, ItemsSort.NAMEASC, 20, 1);
			assumeTrue(li!=null && li.size()>0);
		} catch (LogicException e) {
			fail();}						
	}

	@org.junit.Test
	public void testNoException() {
		UserServiceImpl userServiceImpl=null;
		try {
			userServiceImpl= get(UserServiceImpl.class);
			User admin = userServiceImpl.authenticate("Admin", "Admin");
			System.out.println("TEST PASSED:" + admin.getName());
		} catch (LogicException e) {
			assumeNoException(e);}					//assumeNoException(e);	
	}
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////	
	@org.junit.Test(expected=NullPointerException.class)
	public void testExpectedException() {		
		ItemsServiceImpl itemsServiceImpl=null;
		try {
			itemsServiceImpl= get(ItemsServiceImpl.class);
			Items items = itemsServiceImpl.read(1L);			
			System.out.println(items.getWebpages().getId());
			System.out.println("TEST NULL POINTER NOT PASSED");
		} catch (LogicException e) {
			fail();}					//assumeNoException(e);	
	}

	@org.junit.Test(timeout=4000)
	public void testTimeout() {
		ItemsServiceImpl itemsServiceImpl=null;
		BaseitemServiceImpl bitemsServiceImpl=null;
		OrdersServiceImpl ordersServiceImpl=null;
		try {
			bitemsServiceImpl= get(BaseitemServiceImpl.class);
			itemsServiceImpl =  get(ItemsServiceImpl.class);
			ordersServiceImpl =  get(OrdersServiceImpl.class);
			List<Baseitem> lb = bitemsServiceImpl.findAll();
			List<Items> li = itemsServiceImpl.findAll();
			List<Orders> lo =ordersServiceImpl.findAll();		
			System.out.println("TEST LISTS PASSED:" + lb.size() + " " +li.size() + " " + lo.size() );
		} catch (LogicException e) {
			fail();}					//assumeNoException(e);	
	}
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////		
	@org.junit.Test
	public void testNotNull() {
		OrdersServiceImpl orderServiceImpl=null;
		try {
			orderServiceImpl= get(OrdersServiceImpl.class);
			assumeNotNull(orderServiceImpl.getMaxNumber());
		} catch (LogicException e) {
			fail();}					//assumeNoException(e);	
	}
	
	@org.junit.Test
	public void testFactoryClassif() {
		ClassificationServiceImpl classificationServiceImpl=null;
		try {
			classificationServiceImpl = get(ClassificationServiceImpl.class);
			Classification classification =  classificationServiceImpl.read(1L);
			assertEquals(classification.getName(), "Category");
		} catch (LogicException e) {e.printStackTrace(); fail();}						
	}
	
	@org.junit.Test
	public void testCategory() {
		CategoryServiceImpl categoryServiceImpl=null;
		try {
			categoryServiceImpl =  get(CategoryServiceImpl.class);
			Category category =  categoryServiceImpl.read(1L);
			assertEquals(category.getName(), "Special shoes");
		} catch (LogicException e) {e.printStackTrace(); fail();}						
	}
	
	@org.junit.Test
	public void testItem() {
		ItemsServiceImpl itemsServiceImpl=null;
		try {
			itemsServiceImpl =  get(ItemsServiceImpl.class);
			Items items =  itemsServiceImpl.read(1L);
			List<Classification> classification=items.getClassification(); 
			assertTrue(classification.get(0).getId()==2 && classification.size()>=4);
		} catch (LogicException e) {e.printStackTrace(); fail();}						
	}
///////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////	
////////////////////////////////////////////////////////
	public static Connection getConnection() throws LogicException {
		if(connection == null) {
			try {
			    connection = DriverManager.getConnection("jdbc:postgresql://localhost/ishop", "root", "root");
		      } catch(SQLException e) { throw new LogicException(e);}
		 }
			return connection;
		}	
//////////////////////////////////////////////////////////////////////////////
	 @SuppressWarnings("unchecked")
	  public <T> T get(Class<T> i) throws LogicException {
		//	System.out.println("==================="+i);
			Object o = null;
			
				Class<?> c=null;
				if(i.isInterface()) {
					try {c = Class.forName(i.getName() + "Impl");} catch (ClassNotFoundException e) {}	
				} else {
					c = i;
				}
				if(c != null) {
					try {																				
						  o = c.getConstructor().newInstance();						  
				//		  System.out.println("==========Dao========="+o);
						    if (Dao.class.isInstance(o)) {
					//	    	 System.out.println("==========Dao2========="+o);						    					    	
						    	 Method setConnection = DaoImpl.class.getDeclaredMethod("setConnection", Connection.class);
						    	 setConnection.setAccessible(true);	
						    	 setConnection.invoke(o,getConnection());
						    } else if (Action.class.isInstance(o) || BaseService.class.isInstance(o)) {					    
						    	 for (Field field : o.getClass().getDeclaredFields()) {
						    		 Object obj=null;
						    		 field.setAccessible(true);
				//		   		 System.out.println("==========Dao3========="+field);
						    		 try {					    								    			 
						    			 obj = get(field.getType());
						    		 } catch (Exception e) 
						    		 {e.printStackTrace(); System.out.println("==========ERROR CREATION=========" + field);}
						    		 Method setter = Reflection.getSetter(o,field.getName(),field.getType());
						    		 if (setter!=null) {setter.invoke(o,obj);}
						    	 } 
						   }
						} catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
						   throw new ImplementationInstantiationIocContainerException(e);
						}
				 } else {System.out.println("==========Dao FAIL=========)" + i);}
			
			return (T)o;
		}  
//////////////////////////////////////////////////////////////////////////////	
}
