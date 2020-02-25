package state2;

public class WaitEndRow implements State  {
	private boolean delimeterSearch=false;
	
	public WaitEndRow(boolean delimeterSearch) {
		this.delimeterSearch = delimeterSearch;
	}	
	
	public WaitEndRow() {	
	}
/////////////////////////////////////////////////////////////////////////////////
	@Override
	public Character parsedChar(Parser parser) {
		 char ch=parser.getCurrentChar();
		   if (ch=='n') {		
			 parser.setState(new StartRow());
			 return null;			 
			} else if (ch==';') {
				parser.setState(new Delimiter());return ch;}
			else if (ch=='"') {
				 parser.setState(new WaitQuotes(delimeterSearch));
				 return null;
				} 
		   
		    if (delimeterSearch) {parser.setState(new WaitQuotesDelimiter());}
			else {parser.setState(new WaitDelimiter());}		   			
			return ch;
	}

}
