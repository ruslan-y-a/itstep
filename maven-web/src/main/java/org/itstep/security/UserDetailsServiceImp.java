package org.itstep.security;
/*
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Qualifier;
*/
import org.springframework.transaction.annotation.Transactional;
import org.itstep.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service //@Service("userDetailsService")
@Primary
public class UserDetailsServiceImp implements UserDetailsService {

  @Autowired
  private UserDetailsDao userDetailsDao;

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User user = userDetailsDao.findUserByLogin(username);
   
    if (user == null) {throw new UsernameNotFoundException("User not found.");}
    
    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
            .username(username)
            .password(user.getPassword())
            .roles(user.getRole().toString())
            .build();
   
   /*   
      UserBuilder builder = null;
      builder = org.springframework.security.core.userdetails.User.withUsername(username);     
      builder.password(user.getPassword());      
      String[] authorities = new String[1];
      authorities[0]=user.getRole().toString();
      builder.authorities(authorities);      
      return builder.build();
   */ 
/*    
    List<String> roles= new ArrayList<>(); roles.add(user.getRole().toString());    
    List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
    if(roles!= null)  {
        for(String role: roles)  {          
            GrantedAuthority authority = new SimpleGrantedAuthority(role);
            grantList.add(authority);
        }
    }             
    UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),grantList);
*/
    return userDetails;       
  }
}
