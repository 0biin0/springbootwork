package com.study.springboot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.springboot.domain.Member;
import com.study.springboot.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	MemberRepository memberRepository;

	public boolean idCheck(String id) {
		// existsById() : id가 존재하는지 DB검색 하여 반환결과 true or false 반환
		return memberRepository.existsById(id);
	}

	public Member memberInsert(Member member) {
		return memberRepository.save(member);
		/*
		  save 메서드는 전달된 member 객체를 데이터베이스에 저장합니다.
		  만약 member 객체가 새로 생성된 객체라면, 이 메서드는 데이터베이스에 새로운 레코드를 추가(Insert)합니다.
		  만약 member 객체가 이미 데이터베이스에 존재하는 레코드와 관련된 객체라면, 이 메서드는 해당 레코드를 업데이트(Update)합니다.
		 */
	}

	public Optional<Member> login(Member member) {
		return memberRepository.findById(member.getId());	
	}
	
	

}
