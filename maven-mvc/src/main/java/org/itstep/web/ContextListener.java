package org.itstep.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itstep.config.ContextKeeper;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ContextListener implements ServletContextListener{
	   private Logger logger = LogManager.getLogger();

	   @Override
	   public void contextInitialized(ServletContextEvent sce) {
	      logger.info("contextInitialized()");
	      WebApplicationContext rootContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
	      ContextKeeper.setContext(rootContext);
	      ContextKeeper.setServletContext(sce.getServletContext());
	   }

	   @Override
	   public void contextDestroyed(ServletContextEvent sce) {
	      logger.info("contextDestroyed()");
	   }

	}