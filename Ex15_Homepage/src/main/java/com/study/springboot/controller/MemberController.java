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
@SessionAttributes({ "loginUser" })
public class MemberController {

	@Autowired
	MemberService memberService;
	// 비즈니스 로직
	// MemberService 타입의 빈을 자동으로 주입받습니다. MemberService는 일반적으로 비즈니스 로직을 처리하는 서비스 클래스

	@Autowired
	PasswordEncoder passwordEncoder;
	// 비밀번호 암호화
	// PasswordEncoder 타입의 빈을 자동으로 주입받습니다. PasswordEncoder는 비밀번호를 암호화하거나 비교하는 데 사용

	@Autowired
	HttpSession session;
	// 세션 관리를 위해 사용
	// HttpSession 객체를 자동으로 주입받습니다. 이를 통해 HTTP 세션에 접근하여 세션 데이터를 읽거나 쓸 수 있습니다.

	@RequestMapping("/") // root만 RequestMapping 사용
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
	public boolean idCheck(@RequestParam("id") String id) { // HTTP 요청의 파라미터를 컨트롤러 메서드의 매개변수로 바인딩하는 데 사용되는 어노테이션
		return memberService.idCheck(id);
		/*
		 * 역할: @ResponseBody 어노테이션이 붙은 메서드는 반환된 객체를 JSON, XML, 또는 다른 형태의 데이터를 응답 본문으로
		 * 변환하여 클라이언트에게 전달합니다. 동작 원리: Spring은 @ResponseBody가 붙은 메서드의 반환값을 자동으로
		 * 직렬화(Serialize)하여 HTTP 응답 본문에 넣습니다. 예를 들어, 반환값이 Java 객체라면 이를 JSON이나 XML로 변환하여
		 * 응답으로 보냅니다.
		 * 
		 */
	}

	@PostMapping("/memberInsert")
	public String memberInsert(Member member) {
		String enPass = passwordEncoder.encode(member.getPassword());
		// passwordEncoder : 비밀번호 암호화 / passwordEncoder라는 객체를 사용해 회원이 입력한 비밀번호를 암호화하고,
		// 암호화된 비밀번호를 enPass 변수에 저장 encode는 암호화된 비밀번호를 문자열로
		member.setPassword(enPass);
		// 암호화된 비밀번호(enPass)를 member 객체의 비밀번호로 설정
		memberService.memberInsert(member);
		// memberService라는 서비스 객체를 통해 member 객체를 데이터베이스에 저장하는 작업을 수행
		return "redirect:/";
	}

	@PostMapping("/login")
	public String login(Member member, Model model) { // model 담아서 보내준다 (바구니)
		Optional<Member> loginUser = memberService.login(member);
		// null을 처리하는데 도움됨 Member객체가 있을수도 있고 없을수도 있다
		if (loginUser.isPresent()) {
			Member m = loginUser.get();
			if (passwordEncoder.matches(member.getPassword(), m.getPassword())) {
				// member.getPassword()는 사용자가 입력한 비밀번호를 가져오고, m.getPassword()는 데이터베이스에 저장된 비밀번호를
				// 가져옵니다.
				model.addAttribute("loginUser", m);
				// 원래 requestScope => sessionScope로 바꾸기
				// 클래스에 @SessionAttributes({"loginUser"})어노테이션 달기
				// 로그인된 사용자 정보를 Model 객체에 추가, 뷰에서 이 정보를 사용할 수 있게 함
				// 클래스에 @SessionAttributes({"loginUser"}) 어노테이션을 추가해야
				// 이 데이터를 세션 범위에서 사용할 수 있음
			}
		}

		String url = (String) session.getAttribute("boardDetailUrl");
		// 세션에서 'boardDetailUrl'이라는 이름으로 저장된 URL을 가져옴,
		// 사용자가 특정 페이지에서 로그인했을 때, 로그인 후 해당 페이지로 돌아가기 위해 사용
		if (url == null) {
			url = "/";
		// 만약 세션에 저장된 URL이 없다면, 기본적으로 홈 페이지('/')로 설정
		}
		return "redirect:" + url;
		// 사용자를 로그인 후 특정 페이지로 리디렉션, 'boardDetailUrl'이 없으면 홈 페이지로 이동
	}

	/*
	 * @SessionAttributes + model을 통해 로그인정보를 관리하는 경우 SessionStatus객체를 통해 사용완료 처리해야
	 * 한다. - session객체를 폐기하지 않고 재사용
	 */
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		if (!status.isComplete())
			status.setComplete();
		return "redirect:/";
	}
}
