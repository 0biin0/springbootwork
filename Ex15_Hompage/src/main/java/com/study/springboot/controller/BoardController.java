package com.study.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.study.springboot.domain.Board;
import com.study.springboot.domain.Member;
import com.study.springboot.service.BoardService;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes("loginUser")
public class BoardController {
	@Autowired
	BoardService boardService;
	
	@GetMapping("/list")
	public String list() {
		return "board/list";
	}
	
	@GetMapping("/insertForm")
	public String insertForm() {	
		return "board/insertForm";
	}
	
	@PostMapping("/insert")
	public String insert(Board board, HttpSession session) {
		Member m = (Member)session.getAttribute("loginUser");
		board.setWriter(m.getName());
		boardService.insert(board);
		return "redirect:list";
	}
}