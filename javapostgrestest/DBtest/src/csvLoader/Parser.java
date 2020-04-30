package csvLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
  private State state;
  private List<String> inputList;
  private Map<String,ArrayList<String>> outputMap;
  private ArrayList<String> outputHeadList;
  private char currentChar;
/////////////////////////////////////  
  public Parser(String sFile, String encoding) throws IOException {
		Reader reader = new Reader(sFile,encoding);
		this.state = new StartRow();
		this.inputList = reader.getList();
		this.outputMap = new HashMap<String,ArrayList<String>>();
	//	this.currentChar =;
	}  
  public Parser() throws IOException {
		Reader reader = new Reader();
		this.state = new StartRow();
		this.inputList = reader.getList();
		this.outputMap = new HashMap<String,ArrayList<String>>();
//		this.currentChar = ;
	}  
  public Parser(String sFile) throws IOException {
		Reader reader = new Reader(sFile);
		this.state = new StartRow();
		this.inputList = reader.getList();
		this.outputMap = new HashMap<String,ArrayList<String>>();
	//	this.currentChar = ;
	}  
////////////////////////////////////////   
  public State getState() {
	return state;}

public char getCurrentChar() {
	return currentChar;
}
public void setState(State state) {
	this.state = state;}

public ArrayList<String> getOutputHeadList() {
	if (outputHeadList==null) {outputHeadList=new ArrayList<String>();}
	return outputHeadList;
}
//////////////////////////////////////////	
  public Map<String,ArrayList<String>> parse() throws ClassNotFoundException, ParserException{
	  StringBuilder row = new StringBuilder();
	  ArrayList<String> headers; // = new ArrayList<String>();
	  Character ch=null;
	  ArrayList<String> mapValues;
	  int nColumn=-1;
	  Integer columnsNumber=null;
	  boolean firstRow=true;
	  for (String s: inputList) {
		 this.state=new StartRow();		
		 headers = getOutputHeadList();
		 
		 for (char tch: s.toCharArray()) {	 
			 this.currentChar=tch;
			 ch=state.parsedChar(this);
			 if (ch!=null) {
			   if (this.state.getClass().equals(Class.forName("csvLoader.Delimiter"))) {
				  if (firstRow) {
					  headers.add(row.toString()); 
					  outputMap.put(headers.get(++nColumn), new ArrayList<String>());}
				  else {
					  mapValues=outputMap.get(headers.get(++nColumn)); 
					  mapValues.add(row.toString());					  
				  }
				   row.delete(0, row.length());   
			   } else {
				   row.append(ch);
			   } 
			 }
			 else if (this.state.getClass().equals(Class.forName("csvLoader.StartRow"))) {
				  if (columnsNumber!=null && columnsNumber!=nColumn) {
					 throw new ParserException("Wrong amount of columns.Bad column"+ nColumn +". Bad row:" + outputMap.get(headers.get(nColumn)).size());
				  } else {
				    row = new StringBuilder(); firstRow=false; nColumn=-1;
				  }
			 }
		 }
		 //row= new StringBuilder(); firstRow=false; nColumn=-1;
		 if (firstRow) {
			  headers.add(row.toString()); 
			  outputMap.put(headers.get(++nColumn), new ArrayList<String>());}
		  else {
			  mapValues=outputMap.get(headers.get(++nColumn)); 
			  mapValues.add(row.toString());					  
		  }
		 row= new StringBuilder(); firstRow=false; nColumn=-1;
	  }
	  
	  return outputMap;
  }
//ParserException
}
