package org.itstep.tabs;

import java.sql.ResultSet;
import org.itstep.postgres.DaoException;
import org.itstep.sqlSetGet.SqlGetterI;
import org.itstep.sqlSetGet.SqlGetterL;
import org.itstep.sqlSetGet.SqlGetterS;
import org.itstep.sqlSetGet.SqlSetterI;
import org.itstep.sqlSetGet.SqlSetterL;
import org.itstep.sqlSetGet.SqlSetterS;

public class User extends Entity { 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5667655294155727623L;
	private String login;
	private String password;
	private String name;
	private String email;
	private Role roleid;
	
  public User() {	  		  
    super("users");
    //entityValues = new HashMap<String,Object>();
    entityValues.put("id", null);
    entityValues.put("name", null);
    entityValues.put("login", null);  
    entityValues.put("password", null);
    entityValues.put("email", null);
    entityValues.put("roleid", null);	
    
    tabSetter.put("id", new SqlSetterL());
    tabSetter.put("name", new SqlSetterS());
    tabSetter.put("login", new SqlSetterS());
    tabSetter.put("password", new SqlSetterS());
    tabSetter.put("email", new SqlSetterS());
    tabSetter.put("roleid", new SqlSetterI());
    
    tabGetter.put("id", new SqlGetterL());
    tabGetter.put("name", new SqlGetterS());
    tabGetter.put("login", new SqlGetterS());
    tabGetter.put("password", new SqlGetterS());
    tabGetter.put("email", new SqlGetterS());
    tabGetter.put("roleid", new SqlGetterI());  
       
  }
  @Override
  public String toString() {		  
	 return "id:" + this.DBgetId() + "\nname:"+ name + 
	 "\nemail:"+email +"\nrole:" + roleid;
  }
/*//////////////////////////////////////////////////////////////////////////*/
  /*//////////////////////////////////////////////////////////////////////////*/
  @Override
  public void cast() {
	 this.DBsetId((Long) entityValues.get("id"));  
	 this.name =  entityValues.get("name").toString();
	 this.login =  entityValues.get("login").toString();
	 this.password = entityValues.get("id").toString();
	 this.email = entityValues.get("email").toString();
	 this.roleid = Role.getRole((Integer) entityValues.get("roleid"));
  }
  @Override
  public void cast(String name) {
	 if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;}  
	 if (name.equals("name")) {this.name =  entityValues.get("name").toString();return;}
	 if (name.equals("login")) {this.login =  entityValues.get("login").toString();return;}
	 if (name.equals("password")) {this.password = entityValues.get("password").toString();return;}
	 if (name.equals("email")) {this.email = entityValues.get("email").toString();return;}
	 if (name.equals("roleid")) {this.roleid = Role.getRole((Integer) entityValues.get("roleid"));}
  }
  
   public String getName() {
	return name;}
	public String getLogin() {
		return login;}
   public String getPassword() {
	 return password;}
   public String getEmail() {
	  return email;}
	public Role getRole() {
		return roleid;}
  
   public String getName(ResultSet r) throws DaoException {
	  this.getNameFromTab(r, "name"); return name;}
   public String getLogin(ResultSet r) throws DaoException {
	  this.getNameFromTab(r, "login"); return login;}
   public String getPassword(ResultSet r) throws DaoException {
	   this.getNameFromTab(r, "password"); return password;}
   public String getEmail(ResultSet r) throws DaoException {
	   this.getNameFromTab(r, "email"); return email;}
   public Role getRoleid(ResultSet r) throws DaoException {
	   this.getNameFromTab(r, "roleid"); return roleid;}
   
	public Role getRole(ResultSet r) throws DaoException {
		   this.getNameFromTab(r, "roleid"); return roleid;}
	
	public void setRole(Role role) {
		this.roleid = role; }
	
	public void setLogin(String login) {
		this.login = login;}
	
	public void setPassword(String password) {
		this.password = password;}
	public void setName(String name) {
		this.name = name;}
	public void setEmail(String email) {
		this.email = email;}
   		
}
