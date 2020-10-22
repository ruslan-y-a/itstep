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
public class StatsDao {
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
    
    public StatsDao() throws SQLException, ClassNotFoundException {    
    	Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection(CON_STR);
    }
 
    public List<Stats> getAll() {      
        try (Statement statement = this.connection.createStatement()) {          
            List<Stats> products = new ArrayList<Stats>();           
            ResultSet resultSet = statement.executeQuery("SELECT id, date, url, count FROM stats");
         
            while (resultSet.next()) {
                products.add(new Stats(resultSet.getLong("id"),
                		                 resultSet.getString("date"),
                                            resultSet.getString("url"),
                                            resultSet.getInt("count")));
            }
         
            return products;
 
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }
    public Stats read(Long i) {      
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT date, url, count FROM stats where id=?")) {          
        
            statement. setLong(1, i);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
            	Stats products =new Stats(i,
            			resultSet.getString("date"),
                                            resultSet.getString("url"),
                                            resultSet.getInt("count"));
            	 return products;
            }
         
            return null;
 
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }
    
    public Stats read(String date, String i) {      
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT id, count FROM stats where date=? and url=?")) {          
        	 statement.setString(1, date);
            statement. setString(2, i);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
            	Stats products =new Stats(resultSet.getLong("id"),
            			                          date,
                                                    i,
                                            resultSet.getInt("count"));
            	 return products;
            }
         
            return null;
 
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }
    public Long save(Stats product) {      
        try (PreparedStatement statement = this.connection.prepareStatement(
                        "INSERT INTO stats('date', 'url', 'count') " +
                         "VALUES( ?, ?, ?)")) {                       
            statement.setString(1, product.getDate());
            statement.setString(2, product.getUrl());
            statement.setInt(3, product.getCount());
            statement.execute();
            ResultSet r = statement.getGeneratedKeys();
            return r.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
    }
    public void add(Stats product) {      
        try (PreparedStatement statement = this.connection.prepareStatement(
                        "INSERT INTO stats('id', 'date', 'url', 'count') " +
                         "VALUES(?, ?, ?, ?)")) {
            statement.setLong(1, product.getId());            
            statement.setString(2, product.getDate());
            statement.setString(3, product.getUrl());
            statement.setInt(4, product.getCount());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(Stats product) {      
        try (PreparedStatement statement = this.connection.prepareStatement(
        		"UPDATE stats SET date= ?, url= ?, count = ? WHERE id = ?")) {
            statement.setLong(4, product.getId());            
            statement.setString(1, product.getDate());
            statement.setString(2, product.getUrl());
            statement.setInt(3, product.getCount());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM stats WHERE id = ?")) {
            statement.setObject(1, id);         
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
