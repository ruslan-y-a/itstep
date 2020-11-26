package org.itstep.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;

import org.itstep.csvLoader.CsvLoader;
import org.itstep.security.CustomAuthencationProvider;
//import org.itstep.security.UserDetailsServiceImp;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity  + AOP @PreAuthorized, @PostAuthorize
/*
 * @PreAuthorize("hasRole('ADMIN')")
public List<String> getUserPasswords() {
  return userService.passwords();
}
 */
@ComponentScan(basePackages = {"org.itstep.security"})
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private ServletContext context;
	
  //@Autowired  private UserDetailsServiceImp userDetailsService;
  
  @Autowired
  private CustomAuthencationProvider customAuthencationProvider;
  
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  };
  /////////////////////////////////////////////
  /*@Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }*/
  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {   
     auth.authenticationProvider(customAuthencationProvider);
  }
/////////////////////////////////////////////////////////////////////
  @Override
  protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
	  http.csrf().disable();
	  Set<String> whiteURLs, adminURLs, managerURLs, couriesURLs, cashierURLs, clientURLs, productURLs;
	  try {
		     
		  //ServletContext  context = ContextKeeper.getServletContext();
			String fileupload = context.getInitParameter("file-upload");					
			Map<String,ArrayList<String>> map = loadRole(fileupload + "rolepages.csv");
			
			whiteURLs = map.get("whiteURL").stream().collect(Collectors.toSet());
			adminURLs = map.get("adminURL").stream().collect(Collectors.toSet());
			managerURLs = map.get("managerURL").stream().collect(Collectors.toSet());
			couriesURLs = map.get("couriesURL").stream().collect(Collectors.toSet());
			cashierURLs = map.get("cashierURL").stream().collect(Collectors.toSet());
			clientURLs = map.get("clientURL").stream().collect(Collectors.toSet());
			productURLs = map.get("productURL").stream().collect(Collectors.toSet());			
			System.out.println("=====================SECURITY ROLES LOADED");			
		} catch (Exception e) {	 e.printStackTrace(); throw new LogicException(e);}	
	  
	  Map<String,Set<String>> listUrl = listUrls(null,adminURLs,"ADMIN");
	  listUrl =  listUrls(listUrl,managerURLs,"MANAGER");
	  listUrl =  listUrls(listUrl,couriesURLs,"COURIER");
	  listUrl =  listUrls(listUrl,cashierURLs,"CASHIER");
	  listUrl =  listUrls(listUrl,clientURLs,"CLIENT");
	  listUrl =  listUrls(listUrl,productURLs,"PRODUCT");
	  
	  String dz="/**";
	   
	  whiteURLs.forEach(x -> {
		  if (x!=null && !x.isBlank()) {
		     try {	http.authorizeRequests().antMatchers(x+dz).permitAll();} catch (Exception e) {e.printStackTrace();} 
		  }  
	  });	 
	  listUrl.forEach((x,y) -> {
		  try {
			  if (x!=null && !x.isBlank()) {  
		//	    http.authorizeRequests().antMatchers(x+dz).permitAll();  
		        http.authorizeRequests().antMatchers(x+dz).access("hasAnyRole(" + setToString(y) + ")");
			  }  
		  } catch (Exception e) {e.printStackTrace();}
	  });
		 
	   // Config for Login Form
      http.authorizeRequests().and().formLogin()//         
               .and()
               .authorizeRequests().antMatchers("/login**").permitAll()
              .and()
              .formLogin().loginPage("/login")
              .loginProcessingUrl("/login")
              .usernameParameter("login")
              .passwordParameter("password")              
              .defaultSuccessUrl("/").failureUrl("/login?error=true").permitAll()              
              .and()
              .logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();
	  
/*	 	
    http.authorizeRequests().anyRequest().hasAnyRole("ADMIN", "USER")
    .and()
    .authorizeRequests().antMatchers("/login**").permitAll()
    .and()
    .formLogin().loginPage("/login").loginProcessingUrl("/login").permitAll()
    .and()
    .logout().logoutSuccessUrl("/login").permitAll();
*/
      
    //  http.logout().addLogoutHandler(new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter()));
      http.authorizeRequests().antMatchers("/**").permitAll();
  }
///////////////////////////////////////////////////////////////////////////////  

///////////////////////////////////////////////////////////////////////////
private static Map<String,ArrayList<String>> loadRole(String fileupload){
   File file= new File(fileupload); if (!file.exists()) {return null;}
   Map<String,ArrayList<String>> map=null;
   CsvLoader csvLoader = new CsvLoader(file);				
   try {
    map=csvLoader.Load();
   } catch (ClassNotFoundException | IOException e) {e.printStackTrace();}
    return map;
  }  
private static Map<String,Set<String>> listUrls(Map<String,Set<String>> map, Set<String> URLs, String sRole){
	 if (map==null) {map = new HashMap<>();}
	  String ss;
	  for(String x : URLs) {			 
		  ss = (x.indexOf(".html")>=0? x.substring(0, x.indexOf(".html")):x);
		  Set<String> al = map.get(ss);
		  if (al==null) {al = new HashSet<>();}
		  al.add(sRole);
		  map.put(ss, al);
	  };
	 return map;   
  }  

private String setToString(Set<String> URLs) {
   StringBuilder sb = new StringBuilder();
   boolean first=false;
   for(String x : URLs) {
	     if (first) {sb.append(", ");}; sb.append("'"); sb.append(x); sb.append("'");
	     first=true;	   
	    }	  
	return sb.toString();
  }
}

