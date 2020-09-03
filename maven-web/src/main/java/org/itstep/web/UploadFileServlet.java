package org.itstep.web;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name="UploadFileServlet")
public class UploadFileServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6901985353326397696L;
	
	//private String savePath=""; //c:/Users/Admin/eclipse-workspace/maven-web/src/main/webapp/
	                         //  "D:\\files\\upload\\" 
	protected void DoPost(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
		ServletContext context =req.getServletContext();
		String fileupload = context.getInitParameter("file-upload");
		String path; path = new String (req.getParameter("path"));
		if (path==null || path.isBlank()) {path=fileupload;}
		if (path==null || path.isBlank()) {path="c:/Users/Admin/eclipse-workspace/maven-web/src/main/webapp/";}
   	   	
		try {
		  List<FileItem> multiFiles = fileUpload.parseRequest(req);
		  for (FileItem file : multiFiles) {file.write(new File(path+file.getName()));}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	

}
