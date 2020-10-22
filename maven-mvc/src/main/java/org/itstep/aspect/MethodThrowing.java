package org.itstep.aspect;
import org.aspectj.lang.annotation.AfterThrowing;
import java.util.Date;
/*
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.itstep.sqlite.StatsDao;
import org.itstep.sqlite.Stats; */
//import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.itstep.help.DateHelp;
import org.itstep.sqlite.Events;
import org.itstep.sqlite.EventsDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MethodThrowing {
	 
	@Autowired private EventsDao eventsDao;
	// @Autowired private StatsDao statsDao;
	
	@Pointcut("execution(* org.itstep.controller_main.MainController.about(..))")
	public void selectAllMethodAvailable() {
		System.out.println("============================MethodThrowing");
	}
	
	 @AfterThrowing(
		        pointcut="selectAllMethodAvailable()",
		        throwing="ex")
		    public void doRecoveryActions(Exception ex) {
		  String sdate =  DateHelp.getDateDMY(new Date());
		      System.out.println("============================doRecoveryActions");
		      Events events = eventsDao.readByTypeEvent(sdate,"errors","list");
		      if (events==null) {events = new Events(sdate,"errors","list");
		      eventsDao.save(events);
		      }
		      else {events.Increment();eventsDao.update(events);  }          	  
		    }
	
	 
	  
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
	  	
	  
}
