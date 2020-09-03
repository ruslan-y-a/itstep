package org.itstep.config;

import java.sql.Connection;

//import org.springframework.boot.context.embedded.MultipartConfigFactory;

//import javax.servlet.MultipartConfigElement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan(basePackages = {"org.itstep"})
public class ApplicationConfiguration {
	@Bean
	@Scope("prototype")
	public Connection connection() throws OpenConnectionException {
		Connection connection = ConnectionThreadHolder.getConnection();
		if(connection != null) {
			return connection;
		} else {
			throw new OpenConnectionException();
		}
	}
	/*
	  @Bean
	    MultipartConfigElement multipartConfigElement() {
	        MultipartConfigFactory factory = new MultipartConfigFactory();
	        factory.setMaxFileSize("128KB");
	        factory.setMaxRequestSize("128KB");
	        return factory.createMultipartConfig();
	    }
	
	  @Bean(name = "commonsMultipartResolver")
	  public MultipartResolver multipartResolver() {
	      return new StandardServletMultipartResolver();
	  }


	  @Bean
	  public MultipartConfigElement multipartConfigElement() {
	      MultipartConfigFactory factory = new MultipartConfigFactory();

	      factory.setMaxFileSize("10MB");
	      factory.setMaxRequestSize("10MB");

	      return factory.createMultipartConfig();
	  }
	  */
}
