package org.itstep.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users", schema="public")
public class User { 
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}
	private String login;
	private String password;
	private String name;
	private String email;
    @Column(name = "roleid")	
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
