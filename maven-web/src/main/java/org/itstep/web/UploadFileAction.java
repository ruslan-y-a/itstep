package org.itstep.web;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.itstep.web.action.Action;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class UploadFileAction implements Action {
	
	@Override
	public Result exec(HttpServletRequest req, HttpServletResponse resp) {
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		ServletContext context =req.getServletContext();
		String fileupload = context.getInitParameter("file-upload");
		String path; path = req.getParameter("path");
		if (path==null || path.isBlank()) {path=fileupload;}
		if (path==null || path.isBlank()) {path="c:/Users/Admin/eclipse-workspace/maven-web/src/main/webapp/";}
	//	System.out.println("==================UPLOAD fileupload)" + fileupload);
		try {
		  List<FileItem> multiFiles = fileUpload.parseRequest(req);
		 // System.out.println("==================UPLOAD path)" + path);
		  if (!(multiFiles.size()>0)) {return null;}
		  for (FileItem file : multiFiles) {file.write(new File(path+file.getName()));}
		} catch (Exception e) {}  //e.printStackTrace();
		
		return null;		
	}
}
