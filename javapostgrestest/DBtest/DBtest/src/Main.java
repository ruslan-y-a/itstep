/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;*/
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
     public static final String PATH="jdbc:postgresql://localhost/"; 
     public static Scanner scanner;
     public static Commander commander;
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
	   try {		
		   scanner = new Scanner(System.in);  
		commander = new Commander();
		String command;
		System.out.println("Введите команду(помощь - команда help):");
		while(true) {
			System.out.print("> ");
			command = scanner.nextLine();
			commander.Listen(command);
		}			
		
      }
      finally {
    	  scanner.close();
    	  commander.Exit();
      }	
	
	}

	/*
	public static Connection connect0(String name,String user, String pass) throws SQLException {
		String url = PATH + name;		
		Connection c=DriverManager.getConnection(url, user, pass);
		return c;
	}
	public static String names(String name,String...args) throws SQLException {
	    String sql="";
	    Boolean first=true;
	    StringBuilder str = new StringBuilder("SELECT ");
        for(String ss: args) {
        	if (first) {str.append("\"" + ss + "\"");}
        	else {str.append(", \"" + ss + "\"");}
        }
        str.append(" FROM " + "\"" + name + "\"");
		return sql;
	}*/
}
