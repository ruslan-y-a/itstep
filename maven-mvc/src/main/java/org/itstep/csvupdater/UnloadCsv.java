package org.itstep.csvupdater;

import java.io.FileWriter;
import java.util.List;

import org.itstep.config.ContextKeeper;
import org.itstep.de_services.ItemsService;
import org.itstep.entities.CsvWritable;
import org.springframework.web.context.WebApplicationContext;

import com.opencsv.CSVWriter;




public class UnloadCsv {
	 public static void WriteCsv(String name) throws Exception
	   {}
		 /*
		   	
		 
	      String csv = "data.csv";
	      CSVWriter writer = new CSVWriter(new FileWriter(csv));
	      //Create record
	      String [] record = "4,David,Miller,Australia,30".split(",");

	      writer.writeNext(record);
	      //close the writer
	      writer.close();
	   }
	 CSVReader reader = new CSVReader(new FileReader("data.csv"), ',' , '"' , 1);
     //Read CSV line by line and use the string array as you want
     String[] nextLine;
     while ((nextLine = reader.readNext()) != null) {
        if (nextLine != null) {
           //Verifying the read data here
           System.out.println(Arrays.toString(nextLine));
        }
      }
   }	 
  //////////////////////////////////////   
     private String[] getCsvRow(String name) {
    	 WebApplicationContext context = ContextKeeper.getContext();
		 ItemsService iservice=(CsvWritable) context.getBean(getClassByName(name));
    	 return null;
     }
     */
//////////////////     
     private Class<?> getClassByName(String name) {  
    	 WebApplicationContext context = ContextKeeper.getContext();
    	 Class<?> cl=null;
    	        if (name.equals("users"))  {cl= org.itstep.service.UserService.class;}    	
    			if (name.equals("category"))  {cl=org.itstep.service.CurrencyService.class;}
    			if (name.equals("classification"))  {cl=org.itstep.service.ClassificationService.class;}
    			if (name.equals("country"))  {cl=org.itstep.service.CountryService.class;}
    			if (name.equals("currency"))  {cl=org.itstep.service.CurrencyService.class;}
    			if (name.equals("img"))  {cl=org.itstep.service.ImgService.class;}
    			
    			if (name.equals("items"))  {cl=org.itstep.de_services.ItemsService.class;}
    			if (name.equals("baseitem"))  {cl=org.itstep.de_services.BaseitemService.class;}
    			if (name.equals("orders"))  {cl=org.itstep.de_services.OrdersService.class;}
    			if (name.equals("sale"))  {cl=org.itstep.de_services.SaleService.class;}
    			if (name.equals("tagcloud"))  {cl=org.itstep.de_services.TagcloudService.class;}
    			if (name.equals("client"))  {cl=org.itstep.de_services.ClientService.class;}

    			if (name.equals("webpages"))  {cl=org.itstep.service.WebpagesService.class;} 
    			if (name.equals("size"))  {cl=org.itstep.service.SizeService.class;}
    			if (name.equals("color"))  {cl=org.itstep.service.ColorService.class;}
    			 if (cl!=null) {
    			
    			Object o = 	 context.getBean(getClassByName(name));
    			List<String[]> list=  ((CsvWritable)o).toCsv();
    				 
    			 }
    			return null;
        }
///////////////////////////////     
}
