package org.itstep.aspect;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.itstep.help.DateHelp;
import org.itstep.sqlite.Events;
import org.itstep.sqlite.EventsDao;
import org.itstep.sqlite.Stats;
import org.itstep.sqlite.StatsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class CatalogAspect {
	@Autowired private EventsDao eventsDao;
	@Autowired private StatsDao statsDao;
	
	@Pointcut("execution(* org.itstep.controller_main.CatalogController.getUrl(..))")	
	public void selectCatalogControllerUse() {
		System.out.println("============================AspectCatalog POINTCUT");
	}
	/*
	  @AfterReturning(pointcut="selectCatalogControllerUse()", returning="req")
	    public void doParseCheck(Object req) {
		   System.out.println("============================CatalogControllerUse");
		   HttpServletRequest request = (HttpServletRequest) req;
		  try {			
			if (request==null) {
				   Events events = eventsDao.readByTypeEvent(new Date(),"errors","itemlist");
				      if (events==null) {events = new Events(new Date(),"errors","itemlist");eventsDao.save(events);
				      }
				      else {events.Increment();eventsDao.update(events);  }   
			} 
			else
			{		
				  String uri = request.getRequestURI();
				  Stats stats = statsDao.read(new Date(), uri);
			      if (stats==null) {stats = new Stats(new Date(),uri);statsDao.save(stats);
			      }
			      else {stats.Increment();statsDao.update(stats);  }      
			}
		} catch (Throwable e) {e.printStackTrace();}
	    }
	    	    		    		
	        Object retVal = pjp.proceed();
	        
	        if (retVal==null) {
	        	 Events events = eventsDao.readByTypeEvent(sdate,"errors","list");
			      if (events==null) {events = new Events(sdate,"errors","list");}
			      else {events.Increment();}
	        	  eventsDao.save(events);  
	        } 
	    
	*/
	@AfterReturning(pointcut="selectCatalogControllerUse()", returning="retVal")
    public void afterReturningCallAt(Object retVal) {
		String url= (String)retVal;
		System.out.println("============================AspectCatalog" + retVal);
		String sdate =  DateHelp.getDateDMY(new Date());
		  Stats stats = statsDao.read(sdate, url);
	      if (stats==null) {stats = new Stats(sdate,url);statsDao.save(stats);
	      }
	      else {stats.Increment();statsDao.update(stats);  }      
	}  
	
	 @AfterThrowing(
		        pointcut="selectCatalogControllerUse()",
		        throwing="ex")
		    public void doRecoveryActions(Exception ex) {
		      String sdate =  DateHelp.getDateDMY(new Date());
		      System.out.println("============================AspectCatalogError");
		      Events events = eventsDao.readByTypeEvent(sdate,"errors","list");
		      if (events==null) {events = new Events(sdate,"errors","list");}
		      else {events.Increment();}
     	  eventsDao.save(events);  
		    }
	  
	  @Before("selectCatalogControllerUse()")	 
		    public void doBeforeActions() {
		  String sdate =  DateHelp.getDateDMY(new Date());
		      System.out.println("============================AspectCatalogBefore");
		      Events events = eventsDao.readByTypeEvent(sdate,"catalog","list");
		      if (events==null) {events = new Events(sdate,"catalog","list");}
		      else {events.Increment();}
   	  eventsDao.save(events);  
		    }
}