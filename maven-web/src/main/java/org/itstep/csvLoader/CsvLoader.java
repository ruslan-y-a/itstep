package org.itstep.csvLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class CsvLoader {
  private String dbList;  
  private String encoding = "UTF-8";
  private File fileToRead;
  
public CsvLoader(String dbList) {this.dbList = dbList;}	
public CsvLoader(String dbList,String encoding) {		
	this.dbList = dbList; this.encoding=encoding;
}
public CsvLoader(File fileToRead,String encoding) {		
	this.fileToRead = fileToRead; this.encoding=encoding;
}	
public CsvLoader(File fileToRead) {this.fileToRead = fileToRead;}	
public void setEncoding(String encoding) {this.encoding = encoding;}

public Map<String,ArrayList<String>> Load() throws ClassNotFoundException, IOException {
	Parser parser;
	Map<String,ArrayList<String>> outputList=null;
		
	if (fileToRead!=null) {parser = new Parser(fileToRead,encoding);}
	else {parser = new Parser(dbList,encoding);}
	try {
		outputList = parser.parse();
	} catch (ParserException e) {			
		e.printStackTrace();
	    System.out.println(e.getMessage());
		System.out.println(dbList + " read Error");
	}
	return 	outputList;
	
		
	
}

}
