package ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import service.UserService;
import service.UserServiceImpl;
import postgres.UserDao;
import postgres.UserDbDaoImpl;

//import commands.Command;
//import commands.ExitCommand;
import postgres.DbDao;
import postgres.DbDaoImpl;
import service.DBService;
import service.DBServiceImpl;
import service.LogicException;

public class Factory  implements AutoCloseable {
	private Connection connection = null;
	private DbDao dbDao = null;
	private DBService dBService = null;
	
///////////////////////////////////////////////////////////////
   public Factory() throws LogicException {
  //   Class.forName("org.postgresql.Driver");
    this.connection = getConnection();
   }
	
	@Override
	public void close() {
		try { connection.close();System.out.println("The Connection is closed");} catch(Exception e) {}
	}
	
	public Connection getConnection() throws LogicException {
	if(connection == null) {
		try {
		connection = DriverManager.getConnection("jdbc:postgresql://localhost/ishop", "root", "root");
	    } catch(SQLException e) {
		  throw new LogicException(e);
		}
	 }
		return connection;
	}
	public Connection getConnection(String db,String user, String pass) throws LogicException {
	if(connection == null) {
		try {
		connection = DriverManager.getConnection("jdbc:postgresql://localhost/" + db +", " + user + ", " + pass);
	    } catch(SQLException e) {
		  throw new LogicException(e);
		}
	 }
		return connection;
	}

///////////////////////SERVICE//////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////

 public DbDao getDbDao() throws LogicException {
    if (this.connection==null) {throw new LogicException("Error in creating a DAO", null);}	
    if(dbDao == null) {
	  DbDaoImpl dbDaoImpl = new DbDaoImpl(this.connection);
	  dbDao = dbDaoImpl;
	  dbDaoImpl.setConnection(getConnection());
   }
    return dbDao;
  }
  
  public DBService getDBService() throws LogicException {
  	if(dBService == null) {
  		DBServiceImpl service = new DBServiceImpl(this.dbDao);
  		dBService = service;
  		service.setDbDao(getDbDao());  		
  	}
  	return dBService;
  }
////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////
	private UserService userService = null;
	public UserService getUserService() throws LogicException {
		if(userService == null) {
			UserServiceImpl service = new UserServiceImpl();
			userService = service;
			service.setUserDao(getUserDao());
		}
		return userService;
	}
  
	private UserDao userDao = null;
	public UserDao getUserDao() throws LogicException {
		if(userDao == null) {
			UserDbDaoImpl userDaoImpl = new UserDbDaoImpl();
			userDao = userDaoImpl;
			userDaoImpl.setConnection(getConnection());
		}
		return userDao;
	}
///////////////////////SERVICE COMMANDS//////////////////////////////////////////////////

	
}
