package org.itstep.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itstep.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthencationProvider implements AuthenticationProvider {
    @Autowired private UserDetailsDao dao;
    @Autowired private BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LogManager.getLogger();
    
    @Override
    public Authentication authenticate(Authentication authentication) 
                                          throws AuthenticationException {
        String userName = authentication.getName().trim();
        String password = authentication.getCredentials().toString().trim();
        User user = dao.findUserByLogin(userName);
   
        if (user == null) {throw new BadCredentialsException("Unknown user "+userName + " or bad password");}
        String pass = user.getPassword().trim();
        if (!passwordEncoder.matches(password, pass) ) {
        	if (password.equals(user.getPassword().trim())) {
        		logger.warn("Password was not encoded. The Pass is being encoded and reset"); 	
        		user.setPassword(passwordEncoder.encode(pass));
        	} else {throw new BadCredentialsException("Unknown user "+userName + " or bad password");}            
        }
            
        UserDetails principal = org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole().toString())
                .build();
        
        return new UsernamePasswordAuthenticationToken(
                principal, password, principal.getAuthorities());
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
    public void setBcryptEncoder(BCryptPasswordEncoder bcryptEncoder) {
        this.passwordEncoder = bcryptEncoder;
    }
}