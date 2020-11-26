package test;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.itstep.config.ConnectionThreadHolder;
import org.itstep.daos.ClassificationDaoImpl;
import org.itstep.daos.DaoException;
import org.itstep.daos.OrdersDaoImpl;
import org.itstep.daos.UserDaoImpl;
import org.itstep.daos.WebpagesDaoImpl;
import org.itstep.de_services.OrdersServiceImpl;
import org.itstep.de_services.UserServiceImpl;
import org.itstep.entities.Classification;
import org.itstep.entities.Orderstatus;
import org.itstep.entities.User;
import org.itstep.pool.ConnectionPool;
import org.itstep.pool.ConnectionPoolException;
import org.itstep.service.LogicException;
import org.itstep.xml.SitemapCreator;
import org.junit.After;
//import org.itstep.web.ApplicationInitializer;
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
		
	public static Connection getConnection() throws LogicException, ConnectionPoolException {
		if(connection == null) {
			
				ConnectionPool.getInstance().init("org.postgresql.Driver", "jdbc:postgresql://localhost/ishop", "root", "root", 5, 20, 1000);
			    connection = ConnectionPool.getInstance().getConnection();
				ConnectionThreadHolder.setConnection(connection);
		    
		 }
			return connection;
		}	
/////////////////////////////////////////////////////	
	@org.junit.Test
	public void testBasket() {
		try { assertEquals(Orderstatus.BASKET.ordinal(),0); }
		catch (Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@org.junit.Test
	public void testConnect() {
		try {
		//	ConnectionPool.getInstance().init("org.postgresql.Driver", "jdbc:postgresql://localhost/ishop", "root", "root", 5, 20, 1000);
		//	Connection connection = ConnectionPool.getInstance().getConnection();
		//	ConnectionThreadHolder.setConnection(connection);
			ClassificationDaoImpl classificationDaoImpl = new ClassificationDaoImpl();
			List<Classification> classification = classificationDaoImpl.read();			
			assertTrue(classification.size()>0); }
		catch (Exception e) {
			fail("Not yet implemented");
		}
	}
	
	@org.junit.Test
	public void testNoException() {		
		try {			
			UserDaoImpl userDaoImpl = new UserDaoImpl();
			User admin = userDaoImpl.read("Admin", "Admin");
			System.out.println("TEST PASSED:" + admin.getName());
		} catch (DaoException e) {
			assumeNoException(e);}					//assumeNoException(e);	
	}
	@org.junit.Test
	public void testNotNull() {
		try {		
			ClassificationDaoImpl classificationDaoImpl = new ClassificationDaoImpl();
			List<Classification> classification = classificationDaoImpl.read();	
			Classification cls1= classification.stream().filter(x -> x.getName().equals("In stock")).findFirst().get();
			Classification cls2= classification.stream().filter(x -> x.getName().equals("Prices")).findFirst().get();
			Classification cls3= classification.stream().filter(x -> x.getName().equals("Discounts")).findFirst().get();
			assumeNotNull(cls1); 
			assumeNotNull(cls2); 
			assumeNotNull(cls3);
		} catch ( DaoException e) {
			fail();}					//assumeNoException(e);	
	}
	
	/*
	@org.junit.Test
	public void testSitemap() {
		try {
			System.out.println("====================MAKING SITEMAP!");
			ConnectionPool.getInstance().init("org.postgresql.Driver", "jdbc:postgresql://localhost/ishop", "root", "root", 5, 20, 1000);
			Connection connection = ConnectionPool.getInstance().getConnection();
			ConnectionThreadHolder.setConnection(connection);
			WebpagesDaoImpl webpagesDaoImpl = new WebpagesDaoImpl();  webpagesDaoImpl.setConnection(connection);
			List<String> staticUrls=  webpagesDaoImpl.read().stream().map(x -> x.getUrl()).collect(Collectors.toList());		
			
			SitemapCreator smc = new SitemapCreator(staticUrls);
			File copied =smc.saveXml();
			assertTrue(copied.exists());
		}
		catch (Exception e) {
			fail("====================ALERT! SITEMAP WAS NOT CREATED!");
		}
	}
	*/
}
