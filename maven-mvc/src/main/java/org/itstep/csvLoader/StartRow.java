package org.itstep.csvLoader;

public class StartRow implements State {

	@Override
	public Character parsedChar(Parser parser) {		
		char ch=parser.getCurrentChar();
		if (ch==';') {
		 parser.setState(new Delimiter());	 return ch;
		} else if (ch=='"') {
		 parser.setState(new WaitQuotes());
		 return null;
		} else if (ch=='\\') {
			 parser.setState(new WaitEndRow());
			 return null;
			} 
		parser.setState(new WaitDelimiter());		
		return ch;
	}

	

}
