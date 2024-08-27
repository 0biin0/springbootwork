package com.study.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.study.springboot.domain.Board;
import com.study.springboot.service.BoardService;
import com.study.springboot.service.ReplyService;

@Controller
@SessionAttributes("loginUser")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	ReplyService replyService;
	
	@GetMapping("/list")
	public String list(@RequestParam(value="nowPage", defaultValue="0") int nowPage, Model model) {
		Page<Board> pageList = boardService.list(PageRequest.of(nowPage, 10, Sort.by(Sort.Direction.DESC, "bno")));
		
		int pagePerBlock = 5;	// [1][2][3][4][5]
		int endPage = Math.min(pageList.getTotalPages(), nowPage + pagePerBlock);

		model.addAttribute("boardPage", pageList);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("endPage", endPage);
		return "board/list";
	}
	
	@GetMapping("/insertForm")
	public String insertForm() {
		return "board/insertForm";
	}
	
	@PostMapping("/insert")
	public String insert(Board board) {
		boardService.insert(board);
		return "redirect:list";
	}
	
	@GetMapping("/detailForm")
	public String detailForm(@RequestParam(value="bno") Long bno, Model model) {
		model.addAttribute("board", boardService.selectDetail(bno).get());
		model.addAttribute("reply", replyService.selectAll(bno));
		return "board/detailForm";
	}

	@PostMapping("/update")
	public String update(Board board, Model model) {
		boardService.update(board);
		return "redirect:list";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam(value="bno") Long bno) {
		boardService.delete(bno);
		return "redirect:list";
	}
}
/*
	 @GetMapping
	역할: HTTP GET 요청을 처리하는 메서드에 붙입니다. 주로 데이터를 조회하거나, 웹 페이지를 반환하는 작업에 사용됩니다.
	특징: GET 요청은 서버에 데이터를 요청하거나 특정 자원을 조회할 때 사용되며, URL에 파라미터를 포함할 수 있습니다. GET 요청은 보통 브라우저에서 페이지를 로드할 때 사용됩니다.
	
	@PostMapping
	역할: HTTP POST 요청을 처리하는 메서드에 붙입니다. 주로 서버에 데이터를 전송하거나, 자원을 생성할 때 사용됩니다.
	특징: POST 요청은 데이터를 서버로 전송할 때 사용됩니다. 보통 폼 데이터 제출, 파일 업로드 등과 같은 작업에 사용됩니다. POST 요청의 데이터는 요청 본문(body)에 포함되므로, GET 요청보다 더 많은 데이터를 보낼 수 있고, URL에 직접 나타나지 않습니다.
 */
