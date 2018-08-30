package com.gura.spring03.cafe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.cafe.dto.CafeCommentDto;
import com.gura.spring03.cafe.dto.CafeDto;
import com.gura.spring03.cafe.service.CafeService;
import com.gura.spring03.member.dto.MemberDto;

@Controller
public class CafeController {
	@Autowired
	private CafeService cafeservice;
	
	@RequestMapping("/cafe/update")
	public ModelAndView update(HttpServletRequest request,
			@ModelAttribute CafeDto dto) {
		cafeservice.update(request,dto);
		 return new ModelAndView("redirect:/cafe/list.do");
	
	}

	@RequestMapping("/cafe/updateform")
	public ModelAndView authUpdateform(HttpServletRequest request,
			@ModelAttribute CafeDto dto) {
		cafeservice.updateform(request,dto);
		return new ModelAndView("cafe/updateform");
	}
	
	//새 댓글 저장 요청처리
	@RequestMapping("/cafe/comment_insert")
	//폼전송되는 파라미터를 자동으로 추출할때 @ModelAttribute(파라미터명이 같아야함)
	public ModelAndView authcommentInsert(HttpServletRequest request,
			@ModelAttribute CafeCommentDto dto) {
		cafeservice.commentInsert(dto);
		//글 자세히 보기로 리다일렉트
		
		return new ModelAndView("redirect:/cafe/detail.do?num="+dto.getRef_group());
	}
	
	   //글 자세히 보기 요청 처리
	   @RequestMapping("/cafe/detail")
	   public ModelAndView detail(HttpServletRequest request) {
	      //서비스 객체를 이용해서 글 자세히 보기에 관련 된 
	      //Model 이 request 에 담기게 하고
		   cafeservice.getData(request);
	      //view 페이지로 forward 이동해서 응답하기
	      return new ModelAndView("cafe/detail");
	   }
	
	@RequestMapping("/cafe/delete")
	public ModelAndView authdelete(HttpServletRequest request, int num) {
		cafeservice.delete(request,num);
		return new ModelAndView("redirect:/cafe/list.do");
	} 
	
	@RequestMapping("/cafe/insert")
	public ModelAndView authinsert(HttpServletRequest request,
			@ModelAttribute CafeDto dto) {
		cafeservice.insert(request,dto);
		 return new ModelAndView("redirect:/cafe/list.do");
	}

	
	
	@RequestMapping("/cafe/insertform")
	public ModelAndView authinsertForm(HttpServletRequest request) {
		return new ModelAndView("cafe/insertform");
	} 
	
	@RequestMapping("/cafe/list")
	public ModelAndView list(ModelAndView mView,
			@RequestParam(defaultValue="1") int pageNum,
			String keyword, String condition
			) {
		//인자로 전달받은 httpservlet객체를 서비스에 전달해서
		//비즈니스 로직을 수행하고 view 페이지에서 필요한 데이터가
		//request 영역에 담기게 한다
		
	/*@RequestMapping("/cafe/list")
	public ModelAndView list(HttpServletRequest request){
		
	}
		 * 
		 * */
		cafeservice.list(mView,pageNum,keyword,condition);
		
		
		// 뷰페이지의 정보 설정 
		mView.setViewName("cafe/list");
		return mView;
	}
}
