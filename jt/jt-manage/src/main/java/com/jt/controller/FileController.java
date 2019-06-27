package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.ImageVo;

@Controller
public class FileController {
	
	@Autowired
	private FileService fileService;
	
      //当用户上传完成重定向到页面 
	@RequestMapping("/file")
	/**
	 * 1.获取文件的嘻嘻,包含文件名称
	 * 2.指定文件上传的路径 
	 * 3.实现文件的上传
	 * @param fileImage
	 * @return
	 */
	
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		//获取input标签中的name属性
		String inputName=fileImage.getName();
		System.out.println(inputName);
		
		//2获取文件的名称
		String fileName=fileImage.getOriginalFilename();
		
		//定义文件夹路径
		File fileDir = new File("D:/123/123");
		if(!fileDir.exists()) {
			fileDir.mkdirs();
		}
		//实现文件的上传
		fileImage.transferTo(new File("D:/123/123"+fileName));
		return "redirect:/file.jsp";
	}
     //实现文件上传
	@RequestMapping("/pic/upload")
	@ResponseBody
	public ImageVo fileUpload(MultipartFile uploadFile) {
		
		return fileService.updateFile(uploadFile);
	}
}
