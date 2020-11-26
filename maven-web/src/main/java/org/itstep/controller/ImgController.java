package org.itstep.controller;
/*
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itstep.help.Files;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itstep.config.ContextKeeper;
//import org.itstep.de_services.ImgServiceImpl;
import org.itstep.entities.Img;
import org.itstep.service.ImgService;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping(value = "/img")
public class ImgController{
	@Autowired private ImgService imgService;
	//@Autowired private ImgServiceImpl imgService;
	private static final Logger logger = LogManager.getLogger();
		
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public ModelAndView home() throws LogicException {
	    List<Img> list = imgService.findAll();	    
	    ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("img/list");
        modelAndView.addObject("list", list);
	    return modelAndView;
	}
	
	@RequestMapping("/edit")
	public ModelAndView editImg(@RequestParam(required = false) Long id) throws LogicException {
	    ModelAndView mav = new ModelAndView("img/edit");
	   if (id!=null) {
	     Img img = imgService.findById(id);
	     mav.addObject("entity", img);
	   }
	    return mav;
	}
/*	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveImg(HttpServletRequest req) throws LogicException {
		Img img = getImg(req);
		 String url=saveloadImg(req);
		 if (url!=null && url.isBlank()) {img.setUrl(url);}
		 imgService.save(img);
	    return "redirect:/img/list";
	}
*/	
	//, @RequestParam String title,  @RequestParam String alt,  @RequestParam(required=false) String url,
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String submit(HttpServletRequest req, @RequestParam(required=false) MultipartFile file, ModelMap modelMap) throws LogicException {
		Img img = getImg(req);   // img.setTitle(title); img.setAlt(alt); 		
		String fileupload = ContextKeeper.getServletContext().getInitParameter("upload-img-shoes");
		String fileuploadTarget = ContextKeeper.getServletContext().getInitParameter("upload-img-shoes-target");
		String filename;
	//	logger.info("=========================img)"+img);
		// System.out.println("=========================img)"+img);
		if (file!=null && !file.getName().isBlank()) { 						
			try {
			    /* byte[] bytes =  file.getBytes();
			     filename = file.getOriginalFilename();			
			     File myimg = new File(fileupload+filename);
			     BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(myimg));
			     stream.write(bytes);
			     stream.close();*/
				 //Files.saveMultipartFile(file, fileupload);			     
				 /*  File myimg1 = new File(fileuploadTarget+filename);
			     BufferedOutputStream stream1 = new BufferedOutputStream(new FileOutputStream(myimg1));
			     stream1.write(bytes);
			     stream1.close();*/
				 filename=Files.saveMultipartFileName(file, fileupload, fileuploadTarget);				 
			     if (filename!=null) {img.setUrl("/img-shoes/"+filename);
			     logger.info("=========================img filename)"+filename);}
		      } catch (Exception e) {e.printStackTrace();} 		
		} 		 
		 imgService.save(img);
	    return "redirect:/img/list";
	}				 
/*	  modelMap.addAttribute("name", name);   modelMap.addAttribute("email", email);
	   modelMap.addAttribute("file", file);  return "fileUploadView";  */
			
	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteImg(@RequestParam long id) throws LogicException {
		imgService.delete(id);
	    return "redirect:/img/list";       
	}
/*	@RequestMapping(value ="/delete", method = RequestMethod.POST)
	public String deleteImg(@RequestParam long []id) throws LogicException {
		for (long i:id) {imgService.delete(i);}		
	    return "redirect:/";       
	}*/
////////////////////////////////////////////////////////////////////////

	private Img getImg(HttpServletRequest req) {
		   String sid = req.getParameter("id");  //} catch (NullPointerException e){sid ="";}		
		   Long id = null; 		  
		   try {id = Long.parseLong(sid);} catch (NullPointerException | NumberFormatException err) {}
		   
		   Img img = new Img();	  

		   String title=req.getParameter("title");  
		   if(title == null || title.isBlank()) {title="";}	    	   	    	   
		   img.setTitle(title);
		//   System.out.println("==========================img title)"+title);
		   
		   String alt=req.getParameter("alt"); 
		   if(alt == null || alt.isBlank()) {alt="";}	    
		   img.setAlt(alt); 
		//   System.out.println("==========================img alt)"+alt);   
		   		  		   
		    String url="";  url=req.getParameter("url"); 
		   if(url == null || url.isBlank()) {url="";} 		   		   				
			img.setUrl(url); 
			
		   img.setId(id); 	
	//	   System.out.println("==========================img url)"+url);   
		   return img;
	}	
	/*
	private String saveloadImg(HttpServletRequest req) {
		 String url="";
		   ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
			ServletContext context =req.getServletContext();
			String fileupload = context.getInitParameter("upload-img");
			
			try {
				  List<FileItem> multiFiles = fileUpload.parseRequest(req);
				  for (FileItem file : multiFiles) {
					  file.write(new File(fileupload+file.getName()));
					  if (file!=null) {url=fileupload+file.getName();}
					  }				 	
				} catch (Exception e) {e.printStackTrace();}
			return url;
	}
	*/
}
