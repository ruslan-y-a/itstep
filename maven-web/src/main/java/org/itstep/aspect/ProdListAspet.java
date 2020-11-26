package org.itstep.aspect;

import java.util.Date;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.itstep.help.DateHelp;
import org.itstep.sqlite.Events;
import org.itstep.sqlite.EventsDao;
import org.springframework.beans.factory.annotation.Autowired;

public class ProdListAspet {
	@Autowired private EventsDao eventsDao;
	
	@Pointcut("execution(* org.itstep.controller_user.ProductOrderController.productlist(..))")
	public void selectAllMethodAvailable() {
		System.out.println("============================product list fired");
	}
	
	 @AfterThrowing(
		        pointcut="selectAllMethodAvailable()",
		        throwing="ex")
		    public void doRecoveryActions(Exception ex) {
		  String sdate =  DateHelp.getDateDMY(new Date());
		      System.out.println("============================doRecoveryActions");
		      Events events = eventsDao.readByTypeEvent(sdate,"errors","productlist");
		      if (events==null) {events = new Events(sdate,"errors","productlist");
		      eventsDao.save(events);
		      }
		      else {events.Increment();eventsDao.update(events);  }          	  
		    }
	
	  @Before("selectCatalogControllerUse()")	 
	    public void doBeforeActions() {
	  String sdate =  DateHelp.getDateDMY(new Date());
	      System.out.println("============================product list fired BEFORE Actions");
	    
	    }
	 /*
	  
	  @AfterReturning(pointcut="selectAllMethodAvailable()", returning="retVal")
	    public void doParseCheck(Object retVal) {
		  String sdate =  DateHelp.getDateDMY(new Date());
		   System.out.println("============================doParseCheck");
		  
		  try {			
			if (retVal==null) {
				   Events events = eventsDao.readByTypeEvent(sdate,"errors","parsesite");
				      if (events==null) {events = new Events(sdate,"errors","parsesite");eventsDao.save(events);
				      }
				      else {events.Increment();eventsDao.update(events);  }   
			} 
			else
			{				
				  Events events = eventsDao.readByTypeEvent(sdate,"parse","currencies");
				  System.out.println("============================events" + events);
			      if (events==null) {events = new Events(sdate,"parse","currencies");eventsDao.save(events);
			      }
			      else {events.Increment();eventsDao.update(events);  }   
			}
		} catch (Throwable e) {e.printStackTrace();}
	    }
	  	*/
}
