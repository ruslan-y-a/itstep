package org.itstep.aspect;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.itstep.aspect.MyAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
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
public class AnnotationAspect {
	@Autowired private EventsDao eventsDao;
	@Autowired private StatsDao statsDao;
	/*
	@Pointcut("org.itstep.controller_main.CatalogController.categorylist(javax.servlet.http.HttpServletRequest,..) && args(req,*)")	
	public void catalogList(HttpServletRequest req) {
		System.out.println("============================catalogList POINTCUT");
	}
	/*
	//@Pointcut("@annotation(myannotation)")
	//public void callAtMyServiceAnnotation() { }

	// @Before("execution(@MyAnnotation public *.*(..))")  //GOOD
	 @Before("@annotation(MyAnnotation)")
	    public void beforeCallAt(MyAnnotation myAnnotation) {
		  String sdate =  DateHelp.getDateDMY(new Date());
		 System.out.println("============================callAtMyServiceAnnotation");
	      Events events = eventsDao.readByTypeEvent(sdate, "myannotation","before");
	      if (events==null) {events = new Events(sdate, "myannotation","before");eventsDao.save(events);
		      }
		      else {events.Increment();eventsDao.update(events);  }   
	 } 
	
	
	@Around("callAtMyServiceSecurityAnnotation(req)")
    public Object aroundCallAt(ProceedingJoinPoint pjp, HttpServletRequest req) {
		Object retVal = null;
		
		String sdate =  DateHelp.getDateDMY(new Date());
		 try {
			    retVal = pjp.proceed();
				if (retVal==null) {
					   Events events = eventsDao.readByTypeEvent(sdate,"errors","itemlist");
					      if (events==null) {events = new Events(sdate,"errors","itemlist");eventsDao.save(events);
					      }
					      else {events.Increment();eventsDao.update(events);  }   
				} 
				else
				{		
					  String uri = req.getRequestURI();
					  Stats stats = statsDao.read(sdate, uri);
				      if (stats==null) {stats = new Stats(sdate,uri);statsDao.save(stats);
				      }
				      else {stats.Increment();statsDao.update(stats);  }      
				}
			} catch (Throwable e) {e.printStackTrace();}
				
           return retVal;                
        
    }
	*/
}
