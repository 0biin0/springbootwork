package com.study.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {
	@RequestMapping("/")
	public @ResponseBody String root() {
		return "jsp로 실행";
	}
	
	//http://localhost:8080/test1
	@RequestMapping("/test1")
	public String test1() {
		return "test1";
		// 호출시 /WEB-INF/views/test1.jsp 찾아서 web으로 보여줌
	}
	@RequestMapping("/test2")
	public String test2() {
		return "sub/test2";
		//호출시 /WEB-INF/views/sub/test2.jsp
	}
}
