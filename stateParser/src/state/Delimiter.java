package state;

public class Delimiter implements State {

	@Override
	public Character parsedChar(Parser parser) {
		   char ch=parser.getCurrentChar();
		   if (ch=='"') {		
			 parser.setState(new WaitQuotes());
			 return null;
			 
			} else if (ch==';') {
				parser.setState(this);return ch;}
		   
			parser.setState(new WaitDelimiter());		
			return ch;
	}

}
