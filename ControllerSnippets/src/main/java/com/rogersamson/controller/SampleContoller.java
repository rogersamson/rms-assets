package com.rogersamson.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SampleContoller {

	@PostMapping(value = "/doc-octet", consumes = "application/octet-stream")
	public ResponseEntity<String> uploadDocuments(@RequestHeader Map<String, String> headers,
			HttpServletRequest request) {

		headers.forEach((key, value) -> {
			log.info(String.format("Header '%s' = %s", key, value));
		});

//			ServletInputStream inputStream = request.getInputStream();
//			new BufferedReader(new InputStreamReader(inputStream)).lines().forEach(System.out::println);
		String fileName = request.getParameter("fileName");
		log.info("FILENAME = {}", fileName);

		try (InputStream inputStream = request.getInputStream()) {
			File targetFile = new File(
					"C:/MyTools/Workspace/CMA-CGM_Codes/ControllerSnippets/Documents/Received/cma-cgm-logo.png");
			// Use standard Java NIO to copy the stream to a file
			log.info("STREAM={}",inputStream);
			Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			return ResponseEntity.ok("File uploaded successfully");
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
		}

	}

	@PostMapping(value = "/doc-multipart")
	public ResponseEntity<String> uploadClaimDocuments(@RequestHeader Map<String, String> headers,
			@RequestParam("file") MultipartFile file) {

		headers.forEach((key, value) -> {
			log.info(String.format("Header '%s' = %s", key, value));
		});

		Path filePath = Paths.get("C:/MyTools/Workspace/CMA-CGM_Codes/ControllerSnippets/Documents/Received/"
				+ file.getOriginalFilename());
		log.info("FILENAME: " + filePath.getFileName());
		try (InputStream inputStream = file.getInputStream();
				OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE)) {
			byte[] buffer = new byte[8192];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);

				// hip-noprod-hip-pre-mulesoft-api
			}
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file");
		}

		return ResponseEntity.ok("File uploaded successfully");
	}

}
