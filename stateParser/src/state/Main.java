package state;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
		String sourceFile="src/object.csv";
		String outputFile="src/output.csv";
		String encoding = "UTF-8";
		Parser parser = new Parser(sourceFile,encoding);
		List<String> outputList = parser.parse();
		
		
		  File file = new File(outputFile);
		
		  FileOutputStream fos=null;
		  OutputStreamWriter osw=null;
		  BufferedWriter bw=null;
		  		  		  		  
		  try {
		      fos = new FileOutputStream(file);
			  osw = new OutputStreamWriter(fos, encoding);
			  bw = new BufferedWriter(osw);			  			  
              
			  for (String s : outputList) {
				  if (!s.isBlank()) {
					  System.out.println("Запись: " + s); 
					  bw.write(s);  
					  bw.newLine();
				  }
			  }
			  
			  System.out.println("Готово"); 
			  			  			  
		  } catch(IOException | IllegalArgumentException e) {
			  System.out.println("Ошибка программы");
			   }
		  finally {
			  
			  if(bw != null) {
					try { bw.close(); } catch(IOException e) {}
				  } else if(osw != null) {
					try { osw.close(); } catch(IOException e) {}
				  } else if(fos != null) {
					try { fos.close(); } catch(IOException e) {}
				  }
			  
		  }
		
	}

}
