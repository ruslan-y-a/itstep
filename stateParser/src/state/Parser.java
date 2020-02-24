package state;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
  private State state;
  private List<String> inputList;
  private List<String> outputList;
  private char currentChar;
/////////////////////////////////////  
  public Parser(String sFile, String encoding) throws IOException {
		Reader reader = new Reader(sFile,encoding);
		this.state = new StartRow();
		this.inputList = reader.getList();
		this.outputList = new ArrayList<>();
	//	this.currentChar =;
	}  
  public Parser() throws IOException {
		Reader reader = new Reader();
		this.state = new StartRow();
		this.inputList = reader.getList();
		this.outputList = new ArrayList<>();
//		this.currentChar = ;
	}  
  public Parser(String sFile) throws IOException {
		Reader reader = new Reader(sFile);
		this.state = new StartRow();
		this.inputList = reader.getList();
		this.outputList = new ArrayList<>();
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
	
  public List<String> parse(){
	  StringBuilder row = new StringBuilder();
	  Character ch=null;
	  for (String s: inputList) {
		 this.state=new StartRow();
		 for (int i=0;i<s.length();i++) {
			 this.currentChar=s.charAt(i);
			 ch=state.parsedChar(this);
			 if (ch!=null) {row.append(ch);}
		 }
		 outputList.add(row.toString()); row= new StringBuilder();
	  }
	  
	  return outputList;
  }

}
