package org.itstep.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcStaticResourcesConfig implements WebMvcConfigurer  {
	 
	   // Static Resource Config
	   @Override
	   public void addResourceHandlers(ResourceHandlerRegistry registry) {	    
	       // Css resource.
	     // registry.addResourceHandler("/styles/**").addResourceLocations("/WEB-INF/resources/css/").setCachePeriod(31556926);
		  registry.addResourceHandler("/**").addResourceLocations("/");// .setCachePeriod(31556926);
	//	  registry.addResourceHandler("*.css").addResourceLocations("/");
	   }
	 
	    
	   @Override
	   public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	       configurer.enable();
	   }
/*
	   registry
       .addResourceHandler("/js/**")
       .addResourceLocations("/js/")
       .setCachePeriod(3600)
       .resourceChain(true)
       .addResolver(new GzipResourceResolver())
       .addResolver(new PathResourceResolver());
	   
	   private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
    "classpath:/META-INF/resources/", "classpath:/resources/",
    "classpath:/static/", "classpath:/public/" };

     registry.addResourceHandler("/**").addResourceLocations(RESOURCE_LOCATIONS);
	*/   
	}

