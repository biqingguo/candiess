package com.itranswarp.crypto.candiess.ueditor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UEditorController {
	@Value("${web.upload-path}")
	String uploadPath = "F:\\image\\";

	@RequestMapping(value = "/config.html")
	public void config(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("application/json");
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		try {
			String exec = new ActionEnter(request, rootPath, uploadPath).exec();
			PrintWriter writer = response.getWriter();
			writer.write(exec);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
