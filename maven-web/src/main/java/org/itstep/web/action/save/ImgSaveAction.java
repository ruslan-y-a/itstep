package org.itstep.web.action.save;
/*
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import javax.servlet.http.Part;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;*/
//import java.io.File;
//import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.de_services.ImgService;
//import org.itstep.de_services.ImgServiceImpl;
import org.itstep.entities.Img;
import org.itstep.service.LogicException;
import org.itstep.web.action.ActionException;
import org.itstep.web.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
//@MultipartConfig
public class ImgSaveAction extends BaseAction {
	@Autowired			
	ImgService imgService;
	
@Override
public Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException {
try {
   
	////////////////////////////////////////
	   String sid; 
   try {sid = new String (req.getParameter("id"));}
   catch (NullPointerException e){sid ="";}
 //  System.out.println("file==================sid)" + sid);
	   Long id = null; 
   try {id = Long.parseLong(sid);}
   catch (NullPointerException | NumberFormatException err) {id =null;}
   Img img = new Img();	  

   String title=req.getParameter("title");  
   if(title == null || title.isBlank()) {title="";}	    	   	    	   
   img.setTitle(title);
   
   String alt=req.getParameter("alt"); 
   if(alt == null || alt.isBlank()) {alt="";}	    
   img.setAlt(alt); 
      
   String url=req.getParameter("url"); 
   if(url == null || url.isBlank()) {url="";}
   img.setUrl(url); 
   /*
   Part filePart = req.getPart("file"); // Retrieves <input type="file" name="file">
   String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.
   InputStream fileContent = filePart.getInputStream();
   
   System.out.println("file==================fileName)" + filePart.getSubmittedFileName());
   System.out.println("file==================fileContent)" + fileContent);
   /*
   String sfile=req.getParameter("file");
   System.out.println("file==================)" + sfile);
   File file = new File(sfile);
   System.out.println("file2==================)" + file.getAbsolutePath());
   try {System.out.println("file2==================)" + file.getCanonicalPath());} catch (IOException e) {e.printStackTrace();}
   System.out.println("file2==================)" + file.getName());
/*   List<Part> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName())).collect(Collectors.toList()); // Retrieves <input type="file" name="file" multiple="true">
   for (Part filePart : fileParts) {
       String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // MSIE fix.} */
   
   
   img.setId(id); 	    	    
   imgService.save(img);


   return new Result("/img/list");
    } catch(IllegalArgumentException e) {  //| IOException | ServletException e
    throw new ActionException(e, 400);
     }

  }
}	
