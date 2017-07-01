package com.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.util.Util;
import com.util.FileUploadUtil;
import com.util.MessageBrokerUtil;
import com.util.storage.StorageFileNotFoundException;
import com.util.storage.StorageService;

@Controller
public class FileUploadController {
	
    private final StorageService storageService;
    private FileUploadUtil utilUploadFile;
    private MessageBrokerUtil utilWebOSocketMsgBroker;
    private static AtomicInteger fileCount = new AtomicInteger();
    
    @Autowired
    public FileUploadController(StorageService storageService, MessageBrokerUtil utilWebOSocketMsgBroker, FileUploadUtil utilUploadFile) {
    	System.out.println("BasicController() called");
        this.storageService = storageService;
        this.utilUploadFile = utilUploadFile;
        this.utilWebOSocketMsgBroker = utilWebOSocketMsgBroker;
    }
	
    
    /**
     * 
     * @param file
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
    	/** 將file存入 **/
    	Util.getConsoleLogger().info("handleFileUpload() starts");
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        fileCount.incrementAndGet();
        
        Util.getConsoleLogger().info("handleFileUpload() here02");
        
        /** 通知前端要更新畫面 **/
		String fileUri = this.utilUploadFile.getFileUri();
		FileUploadUtil.lastFileUrl = fileUri;
		// ex. result - "http://localhost:8085/files/pic02.png"
		Util.getConsoleLogger().info("handleFileUpload() fileUri: " + fileUri);
		
        
		this.utilWebOSocketMsgBroker.sendMsgToTopicSubcriber(MessageBrokerUtil.CHANNEL_fileUploaded, fileUri);
        
        Util.getConsoleLogger().info("handleFileUpload() ends");
        return "redirect:/";
    }    
    
    /**
     * DONWLOAD FILE:
     * first, you'll get an uri for a certain file.
     * Then, you'll be able to use that uri to download the file through this mapping
     * This method is called in listUploadedFiles MvcUriComponentsBuilder.fromMethodName(... ,... ,...)
     * @param filename
     * @return
     */
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    private ResponseEntity<Resource> serveFile(@PathVariable String filename) {
    	Util.getConsoleLogger().info("serveFile() starts");
        Resource file = storageService.loadAsResource(filename);
        Util.getConsoleLogger().info("serveFile() ends");
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
    	Util.getConsoleLogger().info("handleStorageFileNotFound() starts");
    	Util.getConsoleLogger().info("handleStorageFileNotFound() ends");
    	
        return ResponseEntity.notFound().build();
    }

//  /**
//   * All files are cached in storageService
//   * If you type "/" you'll be redirected to "uploadForm"(jsp) and a list of file pathes will be put in thymeleaf with the key "files"
//   * @param model
//   * @return
//   * @throws IOException
//   */
//  @GetMapping("/")
//  public String listUploadedFiles(Model model) throws IOException {
//  	Util.getConsoleLogger().info("listUploadedFiles() starts");
//      model.addAttribute("files", storageService
//              .loadAll()
//              .map(path ->
//                      MvcUriComponentsBuilder
//                              .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
//                              .build().toString())
//              .collect(Collectors.toList()));
//      
//      /** 加上評分歷史紀錄,並讓最新的評論在最上面 **/
//      String RatingHistoryListResult = BasicController.getRatingHistoryListOutput();
//      System.out.println("listUploadedFiles() input RatingHistoryListResult: " + RatingHistoryListResult);
//      model.addAttribute("ratingHistoryList", RatingHistoryListResult); // "ratingHistoryList" 關聯前端，謹慎更動
//      
//      Util.getConsoleLogger().info("listUploadedFiles() ends");
//      
//      return "rate02";
////      return "uploadForm";
//  }
}