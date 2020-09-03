package org.itstep.csvLoader;

public class WaitQuotes  implements State {
    private boolean delimeterSearch=false;
	
	public boolean isDelimeterSearch() {
		return delimeterSearch;}
	public void setDelimeterSearch(boolean delimeterSearch) {
		this.delimeterSearch = delimeterSearch;}
//////////////////////////////////////	
	public WaitQuotes(boolean delimeterSearch) {		
		this.delimeterSearch = delimeterSearch;}
	public WaitQuotes() {		
		this.delimeterSearch = false;}
//////////////////////////////////////////////	
	@Override
	public Character parsedChar(Parser parser) {
		char ch=parser.getCurrentChar();
	   if (ch=='"') {		
		 if (delimeterSearch) {parser.setState(new WaitQuotesDelimiter());}
		 else {parser.setState(new WaitDelimiter());}
		 
		 return null;
		} else if (ch==';') {
			parser.setState(new Delimiter());
			return ch;	
		}	   
		else if (ch=='\\') {
			 parser.setState(new WaitEndRow());
			 return null;
			} 
		parser.setState(new WaitQuotesDelimiter());		
		return ch;
	}

}
