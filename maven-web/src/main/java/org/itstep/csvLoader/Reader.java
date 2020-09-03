package org.itstep.csvLoader;

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
   private String sFile="web/import.csv";
   private String encoding="UTF-8";
 //////////////////////////////////////////////////////////  
   public Reader(String sFile, String encoding) {
		//this.sFile = "web/" + sFile + ".csv";
	    this.sFile = sFile;
		this.encoding = encoding;
	}
	public Reader() {		
	}
	public Reader(String sFile) {
		this.sFile = sFile;	
	}
  public String getsFile() {
		return sFile;
	}
	public void setsFile(String sFile) {
		this.sFile = sFile;
	}
///////////////////////////////////////////////////  
   public List<String> getList() throws IOException{
	   List<String> list = new ArrayList<>();
	   File file = new File(sFile);	
	   if (!file.exists()) {return null;}		
		
	    FileInputStream fis = null;
		InputStreamReader ois = null;
		BufferedReader br = null;
	   
	   try {
	   fis = new FileInputStream(file); 
	   ois = new InputStreamReader(fis, encoding); 
	   br=new BufferedReader(ois);
	   String s;
	    while ((s=br.readLine())!=null) {
		  if (!s.isBlank())  {
			 list.add(s);}
	     }
	   }
	   catch (IOException e) {
		  System.out.println("File " +sFile + " reading error.");
		  throw new IOException();
	   }
	   finally {
	 
	        if(br != null) {
				try { br.close(); } catch(IOException e) {}
			} else if(ois != null) {
				try { ois.close(); } catch(IOException e) {}
			} else if(fis != null) {
				try { fis.close(); } catch(IOException e) {}
			}
	     
	   }
	   return list;
   }
   
}
