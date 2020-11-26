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
public class RatesDao {
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
    
    public RatesDao() throws SQLException, ClassNotFoundException {     
    	Class.forName("org.sqlite.JDBC");
        this.connection = DriverManager.getConnection(CON_STR);
    }
 
    public List<Rates> getAll() {      
        try (Statement statement = this.connection.createStatement()) {          
            List<Rates> products = new ArrayList<Rates>();           
            ResultSet resultSet = statement.executeQuery("SELECT id, date, currency, rate FROM rates");
         
            while (resultSet.next()) {
                products.add(new Rates(resultSet.getLong("id"),
                		          resultSet.getString("date"),
                                            resultSet.getString("currency"),
                                            resultSet.getDouble("rate")));
            }
         
            return products;
 
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }
 
    public Rates read(Long i) {      
        try (PreparedStatement statement = this.connection.prepareStatement("SELECT  date, currency, rate FROM rates where id=?")) {          
        
            statement. setLong(1, i);
            statement.execute();
            ResultSet resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
            	Rates products =new Rates(i,                                            
            			 resultSet.getString("date"),
                                            resultSet.getString("url"),
                                            resultSet.getDouble("rate"));
            	 return products;
            }
         
            return null;
 
        } catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }
    
    public Long save(Rates product) {      
        try (PreparedStatement statement = this.connection.prepareStatement(
                        "INSERT INTO rates('date', 'currency', 'rate') " +
                         "VALUES( ?, ?, ?)")) {                       
            statement.setString(1, product.getDate());
            statement.setString(2, product.getCurrency());
            statement.setDouble(3, product.getRate());
            statement.execute();
            ResultSet r = statement.getGeneratedKeys();
            return r.getLong(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;    	
    }
    public void add(Rates product) {      
        try (PreparedStatement statement = this.connection.prepareStatement(
                        "INSERT INTO rates('id', 'date', 'currency', 'rate') " +
                         "VALUES(?, ?, ?, ?)")) {
            statement.setLong(1, product.getId());            
            statement.setString(2, product.getDate());
            statement.setString(3, product.getCurrency());
            statement.setDouble(4, product.getRate());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(Rates product) {      
        try (PreparedStatement statement = this.connection.prepareStatement(
        		"UPDATE rates SET date= ?, currency= ?, rate = ? WHERE id = ?")) {
            statement.setLong(4, product.getId());            
            statement.setString(1, product.getDate());
            statement.setString(2, product.getCurrency());
            statement.setDouble(3, product.getRate());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM rates WHERE id = ?")) {
            statement.setObject(1, id);         
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
