package org.itstep.config;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

public class ContextKeeper {
  private static WebApplicationContext context;
  private static ServletContext servletContext; 

  public static WebApplicationContext getContext() {return context;}
  public static void setContext(WebApplicationContext context) {ContextKeeper.context = context;}

  public static ServletContext getServletContext() {return servletContext;}
  public static void setServletContext(ServletContext servletContext) {ContextKeeper.servletContext = servletContext;}

}
