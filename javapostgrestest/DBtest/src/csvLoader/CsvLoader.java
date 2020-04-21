package csvLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class CsvLoader {
  private String dbList;  
  private String encoding = "UTF-8";

public CsvLoader(String dbList) {		
	this.dbList = dbList;	
}	
public CsvLoader(String dbList,String encoding) {		
	this.dbList = dbList;	
	this.encoding=encoding;
}	
public void setEncoding(String encoding) {
	this.encoding = encoding;
}

public Map<String,ArrayList<String>> Load() throws ClassNotFoundException, IOException {
	Parser parser;
	Map<String,ArrayList<String>> outputList=null;
		
	parser = new Parser(dbList,encoding);
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
