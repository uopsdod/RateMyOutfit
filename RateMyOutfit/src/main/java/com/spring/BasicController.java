package com.spring;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.util.Util;

@Controller
public class BasicController {
	
	public BasicController(){
		System.out.println("BasicController() called");
	}

	@RequestMapping("/")
	public String welcome(Map<String, String> model) {
		model.put("message", "hello");
		return "welcome";
	}
	
	@RequestMapping(value = "/rate01", method = RequestMethod.GET)
	public String rate01(	        
			Map<String, String> model,
	        HttpServletRequest request, 
	        HttpServletResponse response) throws IOException, ServletException {
		Util.getConsoleLogger().info("rate01() starts");
		model.put("message", "hello rate01");
		
//		Part part = request.getPart("upfile1");	
//		
//		if (part != null){
//			Util.getConsoleLogger().info("get part file!");
//			InputStream in = part.getInputStream();
//			byte[] buf = new byte[in.available()];
//			in.read(buf);
//			in.close();
//		}
		
		
		
		
		Util.getConsoleLogger().info("rate01() ends");
		return "rate01";
	}
	
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadFile(Map<String, String> model,
	        HttpServletRequest request, 
	        HttpServletResponse response) throws Exception {
		Util.getConsoleLogger().info("uploadFile() starts");
		
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		Util.getConsoleLogger().info("rate01() isMultipart: " + isMultipart);
		
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = request.getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		List<FileItem> items = upload.parseRequest(request);
		
		
		// Process the uploaded items
		Iterator<FileItem> iter = items.iterator();
		
		Util.getConsoleLogger().info("uploadFile() iter.hasNext(): " + iter.hasNext());
		while (iter.hasNext()) {
		    FileItem item = iter.next();

		    if (item.isFormField()) {
		        processFormField(item);
		    } else {
		        processUploadedFile(item);
		    }
		}
		
		Util.getConsoleLogger().info("uploadFile() ends");
		return null;
	}


	private void processFormField(FileItem item) {
	    String name = item.getFieldName();
	    String value = item.getString();
	    Util.getConsoleLogger().info("processFormField name: " + name);
	    Util.getConsoleLogger().info("processFormField value: " + value);		
	}	
	
	private void processUploadedFile(FileItem item) throws Exception {
	    String fieldName = item.getFieldName();
	    String fileName = item.getName();
	    String contentType = item.getContentType();
	    boolean isInMemory = item.isInMemory();
	    long sizeInBytes = item.getSize();
	    
	    Util.getConsoleLogger().info("processFormField fieldName: " + fieldName);
	    Util.getConsoleLogger().info("processFormField fileName: " + fileName);
	    Util.getConsoleLogger().info("processFormField contentType: " + contentType);
	    Util.getConsoleLogger().info("processFormField isInMemory: " + isInMemory);
	    Util.getConsoleLogger().info("processFormField sizeInBytes: " + sizeInBytes);
	    
	 // Process a file upload
	    boolean writeToFile = true;
	    if (writeToFile) {
	        File uploadedFile = new File("/" + fileName);
	        item.write(uploadedFile);
	    } else {
	        InputStream uploadedStream = item.getInputStream();
	        uploadedStream.close();
	    }
	    
	}

	
	@RequestMapping("/rate02")
	public String rate02(Map<String, String> model) {
		model.put("message", "hello");
		return "rate02";
	}	
	
	
//	/**
//	 * Generate a PDF report...
//	 */
//	@RequestMapping(value = "/rate01/{objectId}", method = RequestMethod.GET)
//	@ResponseBody
//	public String uploadFile(
//	        @PathVariable("objectId") Long objectId, 
//	        Map<String, String> model,
//	        HttpServletRequest request, 
//	        HttpServletResponse response) {
//		Util.getConsoleLogger().info("uploadFile() starts");
//		Util.getConsoleLogger().info("uploadFile() input objectId: " + objectId);
//		Util.getFileLogger().info("uploadFile() starts");
//		Util.getFileLogger().info("uploadFile() input objectId: " + objectId);
//	    // ...
//	    // Here you can use the request and response objects like:
//	    // response.setContentType("application/pdf");
//	    // response.getOutputStream().write(...);
//		
//		Util.getConsoleLogger().info("uploadFile() ends");
//		Util.getFileLogger().info("uploadFile() ends");
//		
//		return "rate01";
//	}	
	

}