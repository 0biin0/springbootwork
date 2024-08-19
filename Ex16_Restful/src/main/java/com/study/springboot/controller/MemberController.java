package com.study.springboot.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.study.springboot.domain.Member;
import com.study.springboot.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
@SessionAttributes({"loginUser"})
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping("/")
	public String root() {
		return "index";
	}
	
	@GetMapping("/enrollForm")
	public String enrollForm() {
		return "member/enrollForm";
	}

	@GetMapping("/myPage")
	public String myPage() {
		return "member/myPage";
	}
	
	@GetMapping("/idCheck")
	@ResponseBody
	public boolean idCheck(@RequestParam("id") String id) {
		return memberService.idCheck(id);
		/*
		    역할: @ResponseBody 어노테이션이 붙은 메서드는 반환된 객체를 JSON, XML, 또는 다른 형태의 데이터를 응답 본문으로 변환하여 클라이언트에게 전달합니다.
			동작 원리: Spring은 @ResponseBody가 붙은 메서드의 반환값을 자동으로 직렬화(Serialize)하여 HTTP 응답 본문에 넣습니다. 예를 들어, 반환값이 Java 객체라면 이를 JSON이나 XML로 변환하여 응답으로 보냅니다.

		 */
	}
	
	@PostMapping("/memberInsert")
	public String memberInsert(Member member) {
		String enPass = passwordEncoder.encode(member.getPassword());
		//passwordEncoder : 비밀번호 암호화
		member.setPassword(enPass);
		memberService.memberInsert(member);
		return "redirect:/";
	}
	
	@PostMapping("/login")
	public String login(Member member, Model model) {
		Optional<Member> loginUser = memberService.login(member);
		//null을 처리하는데 도움됨 Member객체가 있을수도 있고 없을수도 있다
		if(loginUser.isPresent()) {
			Member m = loginUser.get();
			if(passwordEncoder.matches(member.getPassword(), m.getPassword())) {
				//member.getPassword()는 사용자가 입력한 비밀번호를 가져오고, m.getPassword()는 데이터베이스에 저장된 비밀번호를 가져옵니다.
				model.addAttribute("loginUser", m);		
				// 원래 requestScope => sessionScope로 바꾸기							
				// 클래스에 @SessionAttributes({"loginUser"})어노테이션 달기
			}
		}
		String url = (String)session.getAttribute("boardDetailUrl");
		if(url == null) {
			url = "/";
		}
		return "redirect:" + url;
	}
	
	/*
	 * @SessionAttributes + model을 통해 로그인정보를 관리하는 경우
	     SessionStatus객체를 통해 사용완료 처리해야 한다.
	     - session객체를 폐기하지 않고 재사용 
	 */
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		if(!status.isComplete())
			status.setComplete();
		return "redirect:/";
	}
}
