package com.example.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.encrypt.GenerateKeys;

@Controller
public class DownloadController {
	@GetMapping("/public-key")
	public void download1(HttpServletResponse response) throws IOException {
	    try {
	      //File file = ResourceUtils.getFile("classpath:static/publicKey.txt");
	    	File file = new File(GenerateKeys.PUBLIC_KEY_FILE);
	      System.out.println(file.getName());
	      byte[] data = FileUtils.readFileToByteArray(file);
	      // Thiết lập thông tin trả về
	      response.setContentType("application/octet-stream");
	      response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
	      response.setContentLength(data.length);
	      InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
	      FileCopyUtils.copy(inputStream, response.getOutputStream());
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
	@GetMapping("/private-key")
	public void download2(HttpServletResponse response) throws IOException {
	    try {
//	      File file = ResourceUtils.getFile("classpath:static/privateKey.txt");
	      File file = new File(GenerateKeys.PRIVATE_KEY_FILE);
	      byte[] data = FileUtils.readFileToByteArray(file);
	      // Thiết lập thông tin trả về
	      response.setContentType("application/octet-stream");
	      response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
	      response.setContentLength(data.length);
	      InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
	      FileCopyUtils.copy(inputStream, response.getOutputStream());
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
}
