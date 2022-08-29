package com.roytuts.spring.rest.multiple.files.upload.rest.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// import io.github.classgraph.Resource;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class MultipleFileUploadRestController {

	private static final Logger logger = Logger.getLogger(MultipleFileUploadRestController.class.getName());

	 
		// Save the uploaded file to this folder
		private static String UPLOADED_FOLDER = "C:\\Users\\nithyashree.muddared\\spring-rest-multiple-files-upload\\target\\uploads";
	 
		// Single File upload   
		@PostMapping(value = "/rest/upload", consumes = {
		   "multipart/form-data"
		})
		
		public ResponseEntity < ? > uploadFile(@RequestParam("file") MultipartFile uploadfile) {
		   logger.info("Single file upload!");
		   if (uploadfile.isEmpty()) {
			  return new ResponseEntity<>("You must select a file!", HttpStatus.OK);
		   }
		   try {
			  saveUploadedFiles(Arrays.asList(uploadfile));
		   } catch (IOException e) {
			  return new ResponseEntity < > (HttpStatus.BAD_REQUEST);
		   }
		   return new ResponseEntity<>("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
		   
		}
	 
		     // Multiple File upload   
			 @PostMapping(value = "/rest/multipleupload", consumes = {

				"multipart/form-data"})
		@Operation(summary = "Upload multiple Files")

   public ResponseEntity uploadFiles(@RequestPart String metaData, @RequestPart(required = true) MultipartFile[] uploadfiles) 
		
		{
			logger.info("Multiple file upload!");
		   String uploadedFileName = Arrays.stream(uploadfiles).map(x -> x.getOriginalFilename()).collect(Collectors.joining(" , "));
		   if (uploadedFileName.isEmpty()) {
			return new ResponseEntity<>("You must select a file!", HttpStatus.OK);
		 }
		   
	
		   try {
			  saveUploadedFiles(Arrays.asList(uploadfiles));
		   } catch (IOException e) {
			e.printStackTrace();
			  return new ResponseEntity < > (HttpStatus.BAD_REQUEST);
		   }
		   return ResponseEntity.ok().body("SUCCESS");
		  
		}
	 
		// Single File download
		// @RequestMapping(path = "/rest/download", method = RequestMethod.GET)
	 
		// public ResponseEntity < Resource > downloadFile(String fileName) throws IOException {
		//    File file = new File(UPLOADED_FOLDER + fileName);
		//    HttpHeaders headers = new HttpHeaders();
		//    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		//    headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		//    headers.add("Pragma", "no-cache");
		//    headers.add("Expires", "0");
		//    Path path = (Path) Paths.get(UPLOADED_FOLDER + fileName);
		
	 
		//     ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes((java.nio.file.Path) path));
			
	 
		//   return (ResponseEntity<Resource>) ResponseEntity.ok();
		// 	    .headers(headers)
		// 	  .contentLength(file.length())
		// 	  .contentType(MediaType.APPLICATION_OCTET_STREAM)
		// 	  .body(resource);
	 
		// }
	 
		// save file
		private void saveUploadedFiles(List<MultipartFile> files) throws IOException {
			if (files == null || files.size() == 0) {
						throw new RuntimeException("You must select at least one file for uploading");
					}
		   File folder = new File(UPLOADED_FOLDER);
		   if (!folder.exists()) {
			  folder.mkdir();
		   }
		   for (MultipartFile file: files) {
			  if (file.isEmpty()) {
				 continue;
				 
			  }
			  byte[] bytes = file.getBytes();
			  Path path = Path.of(UPLOADED_FOLDER + file.getOriginalFilename());
			  Files.write(path, bytes);
		} 
	}
	 
 }

	




