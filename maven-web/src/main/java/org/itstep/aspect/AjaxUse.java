package org.itstep.aspect;

import java.util.Date;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.itstep.help.DateHelp;
//import org.aspectj.lang.annotation.AfterReturning;
import org.itstep.sqlite.Events;
import org.itstep.sqlite.EventsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class AjaxUse {
	@Autowired private EventsDao eventsDao;

	@Pointcut("execution(* org.itstep.web.ajax.ListServlet.doGet(..))")
	public void selectAllAjaxUseAvailable() {
		System.out.println("============================AjaxUse POINTCUT");
	}
	
	//@AfterThrowing("org.itstep.web.ajax.ListServlet()");
	@Before("selectAllAjaxUseAvailable()")
	public void BeforeAjaxTrowing() {
		   String sdate =  DateHelp.getDateDMY(new Date());
		System.out.println("============================BeforeAjaxTrowing"); 		
        Events events = eventsDao.readByTypeEvent(sdate,"ajax","before");
        if (events==null) { events = new Events(sdate,"ajax","before"); eventsDao.save(events);
	      }
	      else {events.Increment();eventsDao.update(events);  }   
	}
	
	@After("selectAllAjaxUseAvailable()")
	public void AfterAjaxTrowing() {
		 String sdate =  DateHelp.getDateDMY(new Date());
		System.out.println("============================AfterAjaxTrowing");
        Events events = eventsDao.readByTypeEvent(sdate,"ajax","after");
        if (events==null) { events = new Events(sdate,"ajax","after");eventsDao.save(events);
	      }
	      else {events.Increment();eventsDao.update(events);  }   
	}
	
	 @AfterReturning(pointcut="selectAllAjaxUseAvailable()", returning="retVal")
	    public void doParseCheck(Object retVal) {
		  String sdate =  DateHelp.getDateDMY(new Date());
		   System.out.println("============================AjaxUse");
		  
		  try {			
			if (retVal==null) {
				   Events events = eventsDao.readByTypeEvent(sdate,"errors","ajax");
				      if (events==null) {events = new Events(sdate,"errors","ajax");eventsDao.save(events);
				      }
				      else {events.Increment();eventsDao.update(events);  }   
			} 
			else
			{				
				  Events events = eventsDao.readByTypeEvent(sdate,"ajax","use");
			      if (events==null) {events = new Events(sdate,"ajax","use");eventsDao.save(events);
			      }
			      else {events.Increment();eventsDao.update(events);  }   
			}
		} catch (Throwable e) {e.printStackTrace();}
	    }
	  	
	
}
