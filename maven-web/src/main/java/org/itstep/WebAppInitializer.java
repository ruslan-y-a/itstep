package org.itstep;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.itstep.config.ApplicationConfiguration;
import org.itstep.config.JpaConfig;
import org.itstep.config.SecurityInit;
import org.itstep.config.WebMvcConfig;
import org.itstep.config.WebSecurityConfig;
//import org.itstep.xml.MakeSitemap;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(WebMvcConfig.class); 
     //   appContext.register(WebSecurityConfig.class);       
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
                "SpringDispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        dispatcher.addMapping("/index");
       
        servletContext.setInitParameter("defaultHtmlEscape", "true");
       
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(ApplicationConfiguration.class);
        rootContext.register(SecurityInit.class);   
        rootContext.register(WebSecurityConfig.class);   
        rootContext.register(JpaConfig.class);
        servletContext.addListener(new ContextLoaderListener(rootContext));
        //appContext.register(ApplicationConfiguration.class);
        //servletContext.addListener(new ContextLoaderListener(appContext));        
        
        FilterRegistration charEncodingFilterReg =
        		servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
        charEncodingFilterReg.setInitParameter("encoding", "UTF-8");
        charEncodingFilterReg.setInitParameter("forceEncoding", "true");
        charEncodingFilterReg.addMappingForUrlPatterns(null, false, "/*");
        
        servletContext.addListener(org.itstep.web.ContextListener.class);  
        servletContext.addListener(org.itstep.web.ApplicationInitializer.class);
        servletContext.addListener(org.itstep.web.AuthorizationLogger.class);
        servletContext.addListener(org.itstep.web.RequestConnectionBinding.class);             
  //     servletContext.addFilter("SequrityFilter", org.itstep.web.SequrityFilter.class);
               
    }
}
