package com.study.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springboot.domain.Member;
import com.study.springboot.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired //생성자 주입 / 수정자(Setter) 주입, 혹은 메서드 주입 / 필드 주입
	MemberService memberService;
	
	@RequestMapping("/")
	public String root() { //root 최상위 경로 -> /
		return "index";
	}
	
	@RequestMapping("/minsert")
	public String minsert(Member member, Model model) {
		Member m = memberService.minsert(member);
		model.addAttribute("member", m);
		return "minsert";
	}

	@RequestMapping("/mupdate")
	public String mupdate(String id, String name, Model model) {
		Optional<Member> rm = memberService.selectById(id);
		//Optional find할 결과 담기
		Member m = rm.get();
		m.setName(name);
		
		Member member = memberService.minsert(m);
		model.addAttribute("member", member);
		return "index";
	}
	
	@RequestMapping("/modify")
	public String modify(String id, String name, Model model) {// Model model -> 페이지 이동할 때 담아두는 값
		Optional<Member> rm = memberService.selectById(id);
		if(!rm.isPresent()) {
			model.addAttribute("error", 22);
			return "index";
		}
		Member m = rm.get();
		m.setName(name);
		Member mn = memberService.modify(m);
		model.addAttribute("mn" , mn); //addAttribute ("key", vlaue)
		return "modify";
		
	}
}