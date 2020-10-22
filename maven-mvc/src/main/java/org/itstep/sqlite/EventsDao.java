package org.itstep.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class EventsDao {
	private static final String CON_STR = "jdbc:sqlite:d:/Program Files/sqlite/DB/sdata.db";	    
	private Connection connection;
 
    public synchronized Connection getConnection() {
    	if(connection == null) {    		
    		try {
				connection = DriverManager.getConnection(CON_STR);
			} catch (SQLException e) {e.printStackTrace();}    	   
    	 }
    		return connection;
    	}
    
    public EventsDao() throws SQLException, ClassNotFoundException {     
    	Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection(CON_STR);
    }
 
    public List<Events> getAll() {      
        try (Statement statement = this.connection.createStatement()) {          
            List<Events> products = new ArrayList<Events>();           
            ResultSet resultSet = statement.executeQuery("SELECT id, date, type, event, count FROM events");
         
            while (resultSet.next()) {
                products.add(new Events(resultSet.getLong("id"),
                		resultSet.getString("date"),
                                            resultSet.getString("type"),
                                            resultSet.getString("event"),
                                              resultSet.getInt("count")));
                                           
            }
         
            return products;
 
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }
 
    public Events read(Long i) {      
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT  date, type, event, count FROM events where id=?")) {          
        
            statement. setLong(1, i);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
            	Events products =new Events(i,
            			                    resultSet.getString("date"),
                                            resultSet.getString("type"),
                                            resultSet.getString("event"),
                                            resultSet.getInt("count"));
            	 return products;
            }
         
            return null;
 
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }
    public List<Events> readByType(String i) {      
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT id, date, event, count FROM events where type=?")) {          
        	List<Events> products = new ArrayList<Events>();      
            statement. setString(1, i);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
            	products.add(new Events(resultSet.getLong("id"),                                            
            			                resultSet.getString("date"),
                                            i,
                                            resultSet.getString("event"),
                                            resultSet.getInt("count")));
            	 
            }
         
            return products;
 
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }
    public List<Events> readByEvent(String i) {      
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT id, date, type, count FROM events where event=?")) {          
        	List<Events> products = new ArrayList<Events>();      
            statement. setString(1, i);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
            	products.add(new Events(resultSet.getLong("id"),                                            
            			resultSet.getString("date"),
                                            resultSet.getString("type"),
                                            i,
                                            resultSet.getInt("count")));
            	 
            }
         
            return products;
 
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }
    public Events readByTypeEvent(String date, String type, String event) {      
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT id, count FROM events where date=? and type=? and event=?")) {                  	      
            statement.setString(2,type);
            statement.setString(3, event);
            statement.setString(1,date);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
            	Events products =new Events(resultSet.getLong("id"),                                            
            			                    date,
                                    		type,
                                    		event,
                                            resultSet.getInt("count"));
            	 return products;
            }
         
            return null;
 
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }
    public Long save(Events product) {      
        try (PreparedStatement statement = this.connection.prepareStatement(
                        "INSERT INTO events('date', 'type', 'event', 'count') " +
                         "VALUES( ?, ?, ?, ?)")) {                       
            statement.setString(1,product.getDate());
            statement.setString(2, product.getType());
            statement.setString(3, product.getEvent());
            statement.setLong(4, product.getCount());
            statement.execute();
            ResultSet r = statement.getGeneratedKeys();
            return r.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;    	
    }
    public void add(Events product) {      
        try (PreparedStatement statement = this.connection.prepareStatement(
                        "INSERT INTO events('id', 'date', 'type', 'event', 'count') " +
                         "VALUES(?, ?, ?, ?, ?)")) {
            statement.setLong(1, product.getId());            
            statement.setString(2, product.getDate());
            statement.setString(3, product.getType());
            statement.setString(4, product.getEvent());
            statement.setLong(5, product.getCount());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(Events product) {      
        try (PreparedStatement statement = this.connection.prepareStatement(
        		"UPDATE events SET date= ?, type= ?, event = ?, count = ? WHERE id = ?")) {
            statement.setLong(5, product.getId());            
            statement.setString(1, product.getDate());
            statement.setString(2, product.getType());
            statement.setString(3, product.getEvent());
            statement.setLong(4, product.getCount());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM events WHERE id = ?")) {
            statement.setObject(1, id);         
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
