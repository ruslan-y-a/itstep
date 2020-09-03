package org.itstep.entities;

import org.itstep.tabs.Role;

public class User extends Entity { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5667655294155727623L;
	private String login;
	private String password;
	private String name;
	private String email;
	private Role role;
	
 
  @Override
  public String toString() {		  
	 return "id:" + this.getId() + "\nname:"+ name + 
	 "\nemail:"+email +"\nrole:" + role;
  }
/*//////////////////////////////////////////////////////////////////////////*/
  /*//////////////////////////////////////////////////////////////////////////*/


public String getLogin() {
	return login;
}


public void setLogin(String login) {
	this.login = login;
}


public String getPassword() {
	return password;
}


public void setPassword(String password) {
	this.password = password;
}


public String getName() {
	return name;
}


public void setName(String name) {
	this.name = name;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}

public Role getRole() {
	return role;
}

public void setRole(Role role) {
	this.role = role;
}
 
	
}
