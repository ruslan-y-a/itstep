import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Commander {
	private String PATH; //="jdbc:postgresql://localhost/";
	private Connection c;
	private Statement s;
	private ResultSet r;
	private String name;
	private String user;
	private String pass;
	//private String tabname;
	
	public Commander() throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		this.PATH="jdbc:postgresql://localhost/";
		 this.pass="root"; this.user="root";
	}
	public void Connect (String name,String user, String pass) throws SQLException {
		try {
		if (c!=null) {c.close();}
		this.name=name; this.pass=pass; this.user=pass;
		this.c=DriverManager.getConnection(new String(this.PATH+this.name), this.user,this.pass);	
		this.s= c.createStatement();
		} catch(SQLException e) {
			  System.out.println(e.getMessage());  
		}
	}
	public void Connect (String name) throws SQLException {
		try {			
		if (c!=null) {c.close();}
		this.name=name; 
		this.c=DriverManager.getConnection(new String(this.PATH+this.name), this.user,this.pass);	
		this.s= c.createStatement();
		} catch (SQLException se) {
			  System.out.println("Ошибка SQL. Подключение к " + this.name +  " не удалось");  
		 }	
	}
	
	public void Select(String...args) throws SQLException {
	  try {	
		if (args.length==1) {
			this.r= s.executeQuery("SELECT * FROM \"" + args[0] + "\"");
			print();
		}  
		else
		{
		  StringBuilder str = new StringBuilder("SELECT ");		
		  str.append(names(args));
		  str.append(" FROM " + "\"" + args[0] + "\"");
		  this.r= s.executeQuery(str.toString());
		  print(args);
		}  
	  } catch (SQLException se) {
		  System.out.println("Ошибка SQL. Чтение  " + args[0] + " не удалось");  
	  }	
	  catch (Exception e) {
		 System.out.println("Ошибка программы. Чтение не удалось");  
	  }	
	}
////////////////////////////////////////////////////////////////
	public void Close() throws SQLException {
	 if (r!=null) {r.close();r=null;}
	 if (s!=null) {	s.close();s=null;}
	 if (c!=null) {	c.close();c=null;}	
	 this.name="";this.user=""; this.pass="";  
	}
	public void Exit() throws SQLException {
		if (r!=null) {r.close();}
		if (s!=null) {s.close();}
		if (c!=null) {c.close();}
		System.out.println("Всего доброго");
		System.exit(0);
	}		
///////////////////////////////////////////////////////////////	
	public void Listen(String commandLine) throws SQLException {
		commandLine=commandLine.trim();
		String pair[] = commandLine.split(" ");
		if (pair[0].trim().equals("exit")) {this.Exit();return;}
		if (pair[0].trim().equals("close")) {this.Close();return;}
		if (pair[0].trim().equals("help")) {this.help();return;}
		if (pair[0].trim().equals("about")) {this.about();return;}
		if(pair.length > 0) {			
			if(pair.length > 1) {
			  String args[] = pair[1].split(";");						 
			 if (pair[0].trim().equals("connect")) {
				if (args.length==1) {this.Connect(args[0]);}
				if (args.length==3) {this.Connect(args[0],args[1],args[2]);}
			 }
			 if (pair[0].trim().equals("select")) {
				 if (args.length>0) {this.Select(args);}
			 }
		  }
		}
	}
/////////////////////////////////////////////////////	
	public void about() {
	  System.out.printf("Подключение: база-%s; логин-%s;пароль-%s \n",this.name,this.user,this.pass );	
	  System.out.println("Подключение " + (c!=null?c.toString():""));
	}
//////////////////////////////////////////////////////////
	public void help() {
		System.out.println("connect - Подключение 'base';'user';'password'. Пример connect test;root;root ");
		System.out.println("about - Информация про подключение");
		System.out.println("select - Чтение 'table';.... Пример select users;id;name Или просто select users");
		System.out.println("exit - Выход");
		System.out.println("close - Закрыть текущее подключение");
	} 
////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
	private String names(String...args) throws SQLException {
	   // String sql="";
	    //Boolean first=true;
	    StringBuilder str = new StringBuilder();        
   	   for(int i=1;i<args.length;i++) {
   		 if (i==1) {str.append("\"" + args[i] + "\"");}
    	 else {str.append(", \"" + args[i] + "\"");}		 
        }   
		return str.toString();
	}
	private void print(String...args) throws SQLException {
		//Class c;
		String classname;
		StringBuilder sbl;// = new StringBuilder();
		//int num =args.length; 
		while (r.next()) {
			 sbl = new StringBuilder();
			 for(int i=1;i<args.length;i++) {
				 classname= r.getMetaData().getColumnClassName(i);
				 sbl.append(selectprint(args[i],classname));
		      }   
			 System.out.println(sbl);			 
			}
	}
	private void print()  {
	  StringBuilder sbl = null;
	  try {
		String classname;
		// = new StringBuilder();
		//int num =args.length; 
		while (r.next()) {
			 sbl = new StringBuilder();
			 int ifields=r.getMetaData().getColumnCount();
			 for(int i=0;i<ifields;i++) {
				 classname= r.getMetaData().getColumnClassName(i+1);
				 sbl.append(selectprint(r.getMetaData().getColumnLabel(i+1),classname));
		      }   
			 System.out.println(sbl);			 
			}
	  } catch (SQLException e) {System.out.println(e.getMessage());System.out.println(sbl);}
	  catch (Exception e) {System.out.println(sbl);} 
		
	}
	
	private String selectprint(String name,String classname) {
//		String str="";
	  try {	
	    if (classname.indexOf("Integer")>=0) { 
	    	return String.valueOf(r.getInt(name)) + ";";}
	    if (classname.indexOf("Double")>=0) { 
	    	return String.valueOf(r.getDouble(name)) + ";";}
	    if (classname.indexOf("String")>=0) { 
	    	return r.getString(name).trim() + ";";}
	    if (classname.indexOf("Date")>=0) { 
	    	return r.getDate(name).toString() + ";";}
	    if (classname.indexOf("Array")>=0) { 
	    	return r.getArray(name).toString() + ";";}
		return r.getString(name).trim() + ";";
	  
	   } catch(SQLException e) {
		  System.out.println(e.getMessage()); return "";
	   } catch (Exception e) {
		   System.out.println("Ошибка формата поля " + name);
		   return "";
	   }
	 // return r.getString(name).trim()+";";
	}
	
}
