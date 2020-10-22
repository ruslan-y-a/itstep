package org.itstep.help;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Files {
	
	public static boolean saveMultipartFile(MultipartFile file, String spath) {		 
		try {
			 byte[] bytes = file.getBytes();
			 String filename = file.getOriginalFilename();			
		     File myimg = new File(spath+filename); //fileupload+filename
		     BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(myimg));
		     stream.write(bytes);
		     stream.close();
		} catch (IOException e) {
			e.printStackTrace(); return false;
		}	    
		return true;
	}	
	public static String saveMultipartFileName(MultipartFile file, String spath) {		 
		try {
			 byte[] bytes = file.getBytes();
			 String filename = file.getOriginalFilename();			
		     File myimg = new File(spath+filename); //fileupload+filename
		     BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(myimg));
		     stream.write(bytes);
		     stream.close();
		     return filename;
		} catch (IOException e) {
			e.printStackTrace(); return null;
		}	    		
	}		
	public static String saveMultipartFileName(MultipartFile file, String spath, String TargetPath) {		 
		try {
			 byte[] bytes = file.getBytes();
			 String filename = file.getOriginalFilename();			
		     File myimg = new File(spath+filename); //fileupload+filename
		     BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(myimg));
		     stream.write(bytes);
		     stream.close();
		     File myimg1 = new File(TargetPath+filename);
		     BufferedOutputStream stream1 = new BufferedOutputStream(new FileOutputStream(myimg1));
		     stream1.write(bytes);
		     stream1.close();
		     return filename;
		} catch (IOException e) {
			e.printStackTrace(); return null;
		}	    		
	}
	
}
