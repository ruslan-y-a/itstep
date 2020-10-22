package org.itstep.config;

//import java.sql.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.context.annotation.Scope;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@ComponentScan(basePackages = {"org.itstep"})
public class ApplicationConfiguration {
/*	@Bean
	@Scope("prototype")
	public Connection connection() throws OpenConnectionException {
		Connection connection = ConnectionThreadHolder.getConnection();
		if(connection != null) {
			return connection;
		} else {
			throw new OpenConnectionException();
		}
	}
	*/
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(100000);
	    return multipartResolver;
	}
}
