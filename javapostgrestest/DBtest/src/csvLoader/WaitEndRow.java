package csvLoader;

public class WaitEndRow implements State  {
//	private boolean delimeterSearch=false;
	
	@Override
	public Character parsedChar(Parser parser) {
		 char ch=parser.getCurrentChar();
		   if (ch=='n') {		
			 parser.setState(new StartRow());
			 return null;			 
			} else if (ch==';') {
				parser.setState(this);return ch;}
			else if (ch=='\\') {
				 parser.setState(new WaitEndRow());
				 return null;
				} 
		   
			parser.setState(new WaitDelimiter());		
			return ch;
	}

}
