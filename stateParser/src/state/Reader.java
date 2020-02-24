package state;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
//import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Reader {
   private String sFile="src/object.csv";
   private String encoding="UTF-8";
 //////////////////////////////////////////////////////////  
   public Reader(String sFile, String encoding) {
		this.sFile = sFile;
		this.encoding = encoding;
	}
	public Reader() {		
	}
	public Reader(String sFile) {
		this.sFile = sFile;	
	}
  ///////////////////////////////////////////////////  
   public List<String> getList() throws IOException{
	   List<String> list = new ArrayList<>();
	   File file = new File(sFile);
	   if (!file.exists()) {return null;}
	   
	   FileInputStream fis = new FileInputStream(file); 
	   InputStreamReader ois = new InputStreamReader(fis, encoding); 
	   BufferedReader br=new BufferedReader(ois);
	   String s;
	   while ((s=br.readLine())!=null) {
		 if (!s.isBlank())  {
			 list.add(s);}
	   }
	   
	     if(br != null) {
			try { br.close(); } catch(IOException e) {}
		  } 
	   
	   return list;
   }
   
}
