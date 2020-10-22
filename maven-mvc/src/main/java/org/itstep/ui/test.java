package org.itstep.ui;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.itstep.config.ConnectionThreadHolder;
import org.itstep.csvupdater.LoadService;
import org.itstep.daos.BaseitemDaoImpl;
import org.itstep.daos.ClassificationDaoImpl;
import org.itstep.daos.CurrencyDaoImpl;
import org.itstep.daos.DaoException;
import org.itstep.daos.ItemsDaoImpl;
import org.itstep.daos.TagcloudDaoImpl;
import org.itstep.daos.WebpagesDaoImpl;
import org.itstep.de_services.CurrencyServiceImpl;
import org.itstep.de_services.ItemsServiceImpl;
import org.itstep.de_services.WebpagesService;
import org.itstep.de_services.WebpagesServiceImpl;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Classification;
import org.itstep.entities.Currency;
import org.itstep.entities.Items;
import org.itstep.entities.ItemsSort;
import org.itstep.entities.Webpages;
import org.itstep.pool.ConnectionPool;
import org.itstep.pool.ConnectionPoolException;
import org.itstep.service.LogicException;
import org.itstep.sqlite.Stats;
import org.itstep.sqlite.StatsDao;
import org.itstep.xml.SitemapCreator;

public class test {
 public static void main(String...args) throws Exception {
/*
	 Connection c = getConnection();
	/// c = ConnectionThreadHolder.getConnection();
		if (c==null) {
			ConnectionPool.getInstance().init("org.postgresql.Driver", "jdbc:postgresql://localhost/ishop", "root", "root", 5, 20, 1000);
		    connection = ConnectionPool.getInstance().getConnection();
			ConnectionThreadHolder.setConnection(connection);
		}
	 BaseitemDaoImpl bDao = new BaseitemDaoImpl(); bDao.setConnection(c);
	 ItemsDaoImpl iDao = new ItemsDaoImpl(); iDao.setConnection(c);

	 List<Baseitem>list1 = bDao.read();
	 List<Items>list2 = iDao.read();
	 list1.forEach(x -> {
		 String nn=makeStr(x.getName()).trim();
		 if (nn.length()<10) {
			 Items it;
			try {
				it = iDao.read(x.getItem().getId());
				nn = "item " + it.getModel() + nn;   
			} catch (DaoException e) {e.printStackTrace();}
			 
		 }
		 x.setName(nn);
		   try {
			bDao.update(x);
		     } catch (DaoException e) {e.printStackTrace();}
		 });

	 list2.forEach(x -> {
		 String nn=makeStr(x.getName()).trim();
		 if (nn.length()<10) {
			 nn="model " + x.getModel() + nn;
		 }
		 x.setName(nn);
		   try {
			   iDao.update(x);
		     } catch (DaoException e) {e.printStackTrace();}
		 });

	 System.out.println("==============================================");
*//*
	  LoadService ls = new LoadService();
	  ls.setConnection(getConnection());
	    File nf = new File("c:/Users/Admin/eclipse-workspace/maven-mvc/src/main/webapp/baseitem.csv");
	    boolean bl= ls.createCsvLoad(nf);
	    System.out.println("==============================================bl");

	 /*	 System.out.println("==============================================");

			 categoryname = "women-faux-leather-flip-flops";
			 System.out.println(categoryname);
			 webpages  = webpagesDaoImpl.readEntityId(categoryname);
			 idCls =tagcloudDaoImpl.readByWP(webpages.getId());
				 idCls.forEach(x -> System.out.println(x));
				 classifLists=new ArrayList<>();
				 classifLists.add(idCls.stream().map( x-> x.intValue()).collect(Collectors.toList()));
				 iList= itemsDaoImpl.searchListsCategories(classifLists, itemsSort, i1, i2);
				 iList.forEach(x -> System.out.println(x));  */
	 /*
	 StatsDao sd = new StatsDao();
	 Date date = new Date();
	 String ss="/";
	 Integer i = 2;
	 Stats s = new Stats(date,ss,i);
	 Stats gg=sd.read(1L);
	 System.out.println("saved)" +gg);
	 */

 }
///////////////////////////////////////////////////////////////////////////////////////////
 private static String makeStr(String text) {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<text.length();i++) {
			boolean b1=charToInt(text.charAt(i));
			boolean b2=check_a(text.charAt(i));
			boolean b3=check_A(text.charAt(i));
			boolean b4=check_(text.charAt(i));
			if (b1 || b2 || b3 || b4) {sb.append(text.charAt(i));}
		}
		 return sb.toString();
	}
	private static boolean charToInt(char c) {
		Integer yy= null;
		try {
		yy= Integer.parseInt(c+"");
		} catch (Exception e) {return false;}
		return true;
	}
	private static boolean check_a(char c) {
		for(char i='a';i!='z';++i) {if (i==c) {return true;}}
		return false;
	}
	private static boolean check_A(char c) {
		for(char i='A';i!='Z';++i) {if (i==c) {return true;}}
		return false;
	}
	private static boolean check_(char c) {
		if (c==' ') {return true;}
		if (c=='_') {return true;}
		return false;
	}
///////////////////////////////////////////////////////////////////////////////////////////
 private static Connection connection;
 private static Connection getConnection() throws LogicException, ClassNotFoundException {
	    Class.forName("org.postgresql.Driver");
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