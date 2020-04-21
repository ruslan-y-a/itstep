package tabs;

import java.sql.ResultSet;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.ArrayList;
import java.util.HashMap;

import postgres.DaoException;
//import help.Helper;
//import sqlSetGet.SqlGetter;
//import sqlSetGet.SqlSetter;
//import java.util.Map;
import sqlSetGet.SqlGetterI;
import sqlSetGet.SqlGetterL;
import sqlSetGet.SqlGetterS;
import sqlSetGet.SqlSetterI;
import sqlSetGet.SqlSetterL;
import sqlSetGet.SqlSetterS;

public class User extends Entity { 
	
	private String name;
	private String password;
	private String email;
	private Integer roleid;
	
  public User() {	  		  
    super("users");
    entityValues = new HashMap<String,Object>();
    entityValues.put("id", null);
    entityValues.put("name", null);
    entityValues.put("password", null);
    entityValues.put("email", null);
    entityValues.put("roleid", null);	
    
    tabSetter.put("id", new SqlSetterL());
    tabSetter.put("name", new SqlSetterS());
    tabSetter.put("password", new SqlSetterS());
    tabSetter.put("email", new SqlSetterS());
    tabSetter.put("roleid", new SqlSetterI());
    
    tabGetter.put("id", new SqlGetterL());
    tabGetter.put("name", new SqlGetterS());
    tabGetter.put("password", new SqlGetterS());
    tabGetter.put("email", new SqlGetterS());
    tabGetter.put("roleid", new SqlGetterI());  
       
  }
  @Override
  public String toString() {		  
	 return "id:" + this.DBgetId() + "\nname:"+ name + "\npassword:" + password + 
	 "\nemail:"+email +"\nroleid:" + roleid;
  }
/*//////////////////////////////////////////////////////////////////////////*/
  /*//////////////////////////////////////////////////////////////////////////*/
  @Override
  public void cast() {
	 this.DBsetId((Long) entityValues.get("id"));  
	 this.name =  entityValues.get("name").toString();
	 this.password = entityValues.get("id").toString();
	 this.email = entityValues.get("email").toString();
	 this.roleid = (Integer) entityValues.get("roleid");
  }
  @Override
  public void cast(String name) {
	 if (name.equals("id")) {this.DBsetId((Long) entityValues.get("id"));return;}  
	 if (name.equals("name")) {this.name =  entityValues.get("name").toString();return;}
	 if (name.equals("password")) {this.password = entityValues.get("password").toString();return;}
	 if (name.equals("email")) {this.email = entityValues.get("email").toString();return;}
	 if (name.equals("roleid")) {this.roleid = (Integer) entityValues.get("roleid");}
  }
  
   public String getName() {
	return name;}
   public String getPassword() {
	 return password;}
   public String getEmail() {
	  return email;}
   public Integer getRoleid() {
	  return roleid;}
  
   public String getName(ResultSet r) throws DaoException {
	  this.getNameFromTab(r, "name"); return name;}
   public String getPassword(ResultSet r) throws DaoException {
	   this.getNameFromTab(r, "password"); return password;}
   public String getEmail(ResultSet r) throws DaoException {
	   this.getNameFromTab(r, "email"); return email;}
   public Integer getRoleid(ResultSet r) throws DaoException {
	   this.getNameFromTab(r, "roleid"); return roleid;}
   
}
