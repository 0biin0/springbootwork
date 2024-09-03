package com.study.springboot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.study.springboot.domain.Board;
import com.study.springboot.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	BoardRepository boardRepository;

	public Board insert(Board board) {
		return boardRepository.save(board);		
//		 save의 역할 : 1. 저장 2. 업데이트
	}

	public Page<Board> list(PageRequest of) {
//         PageRequest.of()는 스프링 데이터 JPA에서 페이징 처리를 설정하기 위한 메서드입니다. 
//		 이 메서드는 Pageable 인터페이스를 구현한 PageRequest 객체를 생성합니다.
//		 PageRequest는 페이지 번호, 페이지 크기, 정렬 방식 등을 지정하여 데이터베이스 쿼리를 페이징 처리하는 데 사용
		return boardRepository.findAll(of);
//		findAll() 메서드는 주어진 엔티티 타입에 대해 데이터베이스에 저장된 모든 레코드를 조회
	}

	public Optional<Board> selectDetail(Long bno) {
		return boardRepository.findById(bno) // bno를 기준으로 
							  .map(board-> { // map은 Optional에 포함된 값이 존재할 경우 수행되는 연산을 정의
							      board.setCount(board.getCount() + 1); // 게시글의 조회 수를 증가시키는 코드 / getCount() 메서드를 호출하여 현재 조회 수를 가져오고, 이를 1 증가
							      return boardRepository.save(board);
							   });
	}

	public Board update(Board board) {
		Board rboard = boardRepository.findById(board.getBno()).get();
		System.out.println("rboard : " + rboard);
		rboard.setTitle(board.getTitle());
		rboard.setContent(board.getContent());
		return boardRepository.save(rboard);
	}

	public void delete(Long bno) {
		boardRepository.deleteById(bno);
	}


}
