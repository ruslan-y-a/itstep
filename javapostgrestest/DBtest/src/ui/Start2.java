package ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import postgres.DaoException;
import postgres.DbDaoImpl;
import service.DBService;
import service.DBServiceImpl;
import service.LogicException;
import tabs.Entity;

public class Start2 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, DaoException {
		try(Factory factory = new Factory()) {
			DBService service = factory.getDBService();
			service.createCsvLoad("client"); //"client"
			
		} catch(LogicException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	
	}

}
