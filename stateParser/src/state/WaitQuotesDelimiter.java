package state;

public class WaitQuotesDelimiter  implements State {
    
	@Override
	public Character parsedChar(Parser parser) {
		char ch=parser.getCurrentChar();
        if (ch=='"') {
		 parser.setState(new WaitQuotes(true));
		 return null;
		} else if (ch==';') {
			parser.setState(this); return ',';
		}
		parser.setState(this);		
		return ch;
	}

}
