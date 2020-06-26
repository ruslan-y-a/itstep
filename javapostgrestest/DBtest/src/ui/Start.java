package ui;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import csvLoader.CsvLoader;
import de_services.CategoryServiceImpl;
import entities.Category;
import factories.Factory;
import postgres.DaoException;
import postgres.DbDaoImpl;
import service.LogicException;
import tabs.Client;
import tabs.Entity;
import tabs.User;

public class Start {

	public static void main(String[] args) throws SQLException, ClassNotFoundException, DaoException, IOException {
	
		Map<String,ArrayList<String>> map;
		CsvLoader csvLoader = new CsvLoader("rolepages");
		map=csvLoader.Load();
		
		Set<String> admin=new LinkedHashSet<>();
		map.get("ADMIN").forEach(x-> admin.add(x));
		admin.forEach(x->System.out.println(x));
		
	}

}
