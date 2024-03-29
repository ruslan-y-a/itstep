package postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tabs.Client;
import tabs.Entity;
import tabs.User;

public class Start {

	public static void main(String[] args) throws SQLException, ClassNotFoundException, DaoException {
		 Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/ishop", "root", "root");
		DbDaoImpl dbDaoImpl = new DbDaoImpl(connection);
		List<Entity> listU = dbDaoImpl.read("users");
		List<Entity> listC = dbDaoImpl.read("client");
	
		
			 for (Entity entry: listU) {
				 entry.cast();
			   System.out.println(entry);
			 }
			 for (Entity entry: listC) {
				 entry.cast();
				   System.out.println(entry);
				 }
			
		System.out.println("====================================");
		System.out.println("====================================");
		System.out.println("====================================");
		System.out.println("====================================");
		
			
		Entity newU =new User();
		newU.setDBName("users");
		newU.put("name", "Шарик");
		newU.put("password", "111");
		newU.put("email", "gavgav@gav.gav");
		newU.put("roleid", 1);	
		
		long i=dbDaoImpl.create(newU);
	    
		Entity newC=new Client();
		newC.setDBName("client");
		newC.put("countryid", 1);
		newC.put("address", "gav str Gav18 Gav");		
		newC.put("userid", i);
		newC.put("bonuspoints", 0);	
		newC.put("phoneno", "+35785452");	
		
		int[] mass = new int[2]; mass[0]=1;mass[1]=2;  
		
		newC.put("basket", mass);	
		newC.put("orders", mass);
		newC.put("sales", mass);	
		newC.put("recentitems", mass);
		
		long j=dbDaoImpl.create(newC);
		
		newC=null;
		newU=null;
		
		newU=dbDaoImpl.read("users", i);
		 for (Map.Entry<String,Object> entry: newU.getEntityValues().entrySet()) {	
			   System.out.println(entry);
			 }
		newC=dbDaoImpl.read("client", j);
		 for (Map.Entry<String,Object> entry: newC.getEntityValues().entrySet()) {	
			   System.out.println(entry);
			 }
			System.out.println("====================================");
			System.out.println("====================================");
			System.out.println("====================================");

			for (Entity entity:listU) {
				 for (Map.Entry<String,Object> entry: entity.getEntityValues().entrySet()) {	
				   System.out.println(entry);
				 }
			}
		
			System.out.println("====================================");
			for (Entity entity:listC) {
				 for (Map.Entry<String,Object> entry: entity.getEntityValues().entrySet()) {	
					   System.out.println(entry);
					 }
			}
			
		
	}

}
