package com.study.springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springboot.domain.Member;
import com.study.springboot.service.MemberService;

@Controller
public class MemberController {
   
   @Autowired
   MemberService memberService;
   
   @RequestMapping("/")
   public String root() {
      return "menu";
   }
   
   @RequestMapping("/selectByNameLike1")
   public String selectByNameLike1(String name, Model model) {
      String search = name + "%";
      List<Member> result = memberService.selectByNameLike1(search);
      model.addAttribute("members", result);
      return "selectList1";
   }
   
   @RequestMapping("/selectByNameLike1")
   public String selectByNameLike2(String name, int page, Model model) {
      String search = name + "%";
      
      Sort sort = Sort.by(Sort.Order.asc("id"));
      Pageable pageable = PageRequest.of(page-1, 10, sort);
      
      Page<Member> result = memberService.selectMembers3(search, pageable);
      
      model.addAttribute("members", result.getContent());
      model.addAttribute("members", result.getTotalElements());
      
      return "selectList1";
   }
   
   
   
}



