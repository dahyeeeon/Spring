package com.gura.spring03.users.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.users.dto.UsersDto;
import com.gura.spring03.users.service.UsersService;

@Controller
public class UsersController {
	@Autowired
	private UsersService service;
	
	//회원 가입 요청 처리
	   @RequestMapping("/users/signup")
	   public ModelAndView signup(@ModelAttribute UsersDto dto) {
		   //서비스에 전달할 ModelAndView 객체 생성
	      ModelAndView mView=new ModelAndView();
	      //서비스에 객체와 폼 전송된 회원 가입정보가
	      //담겨있는 객체를 전달한다.
	      service.signup(mView, dto);
	      //ModelAndView 객체에 view 페이지 정보를 담고
	      mView.setViewName("users/signup");
	      //리턴
	      return mView;
	      
	   }
	
	@RequestMapping("/users/signup_form")
	public String signupForm() {
		return "users/signup_form";
	}
	
	//아이디 중복확인 ajax 요청에 대한 JSON응답
	//ajax를 json으로 쉽게
	//JSON응답하기위해서 1.pon.xml에 jackson라이브러리 추가
	//2.@ResponseBody 어노테이션 추가
	//3.{}:Map or Dto 리턴 [] List 리턴
	@RequestMapping("/users/checkid")
	@ResponseBody
	public Map<String, Object> checkid(@RequestParam String inputId){
		//서비스 객체를 이용해서 사용가능 여부를 boolean type으로 리턴받음
		boolean canUse=service.canUseId(inputId);
		Map<String,Object> map=new HashMap<>();
		map.put("canUse", canUse);
		//{"canUse":true} or {"canUse": false}
		return map;
	}
	
	  //로그인 폼 요청 처리
	   @RequestMapping("/users/loginform")
	   public String loginForm(HttpServletRequest request) {
	      //로그인 후 이동할 url 주소를 읽어온다
	      String url=request.getParameter("url");
	      //만일 전달되지 않았으면
	      if(url==null) {
	         //context폴더로 이동
	         url=request.getContextPath()+"/";
	      }
	      //request에 담기
	      request.setAttribute("url", url);
	      return "users/loginform";
	   }
	
	 public ModelAndView loginForm2(@RequestParam String url, HttpServletRequest request) {
	     
	      //만일 전달되지 않았으면
	      if(url==null) {
	         //인덱스로 이동할 수 있도록
	         url=request.getContextPath()+"/";
	      }
	     
	      ModelAndView mView=new ModelAndView();
	      mView.addObject("url", url);
	      mView.setViewName("users/loginform");
	      
	      return mView; //View 페이지 정보
	   }

	
	   //로그인 요청 처리
	   @RequestMapping("/users/login")
	   public ModelAndView login(@ModelAttribute UsersDto dto, 
	         @RequestParam String url, HttpSession session) {
	      ModelAndView mView=new ModelAndView();
	      //서비스를 통해서 로그인 처리를 한다
	      service.login(mView, dto, session);
	      
	      //로그인 후 이동할 url
	      mView.addObject("url", url);
	      mView.setViewName("users/login");
	      return mView;
	   }
	   //로그아웃 요청 처리
	   @RequestMapping("/users/logout")
	   public String logout(HttpSession session) {
		   //세션 초기화
		   session.invalidate();
		   //view 페이지 정보리턴
		   return "users/logout";
	   }
	
	   
	   //개인정보 보기 요청처리
	   @RequestMapping("/users/info")
	   public ModelAndView authinfo(HttpSession session,HttpServletRequest request) {
		   ModelAndView mView=new ModelAndView();
		   service.info(mView,session);
		   //view 페이지의 정보 담아서
		   mView.setViewName("users/info");
		   return mView;
	   }
	   
	   //회원가입정보 수정폼 요청처리
	   @RequestMapping("/users/updateform")
	   //로그인해야만 할 수 있게 auth*
	   public ModelAndView authUpdateForm(HttpServletRequest request,
			   HttpSession session) {
		   //ModelAndView 객체를 생성
		   ModelAndView mView=new ModelAndView();
		   //서비스에 인자로 전달,회원정보가 담기게 하고
		   service.updateform(mView, session);
		   //view 페이지에서 회원 정보수정 폼을 출력
		   mView.setViewName("users/updateform");
		   return mView;
	   }
	   
	   //회원정보 수정 요청처리
	   @RequestMapping("/users/update")
	   public ModelAndView authUpdate(HttpServletRequest request,
			   @ModelAttribute UsersDto dto) {
		   service.update(dto);
		   //개인정보보기 페이지로 리다일렉트 이동
		  return new ModelAndView("redirect:/users/info.do");
		  // mView.setViewName("redirect:/users/info.do");
		//return mView;
	   }
	   
	   @RequestMapping("/users/pw_changeform")
	   public ModelAndView authPwUpdateForm(HttpServletRequest request) {
		   
		   return new ModelAndView("users/pw_changeform");
	   }
	   @RequestMapping("/users/pw_check")
	   @ResponseBody
	   public Map<String, Object> pwCheck(@RequestParam String inputPwd,
			   HttpSession session){
		   boolean isValid=service.isValidPwd(inputPwd, session);
		   Map<String,Object> map=new HashMap<>();
		   map.put("isValid", isValid);
		   return map;
	   }
	   
	   //비밀번호 수정 반영하는 요청처리
	   @RequestMapping("/users/pw_update")
	   public ModelAndView authPwUpdate(HttpServletRequest request,
			   @RequestParam String pwd, HttpSession session) {
		   //서비스를 이용해서 비밀번호 수정
		   service.updatePwd(pwd, session);
		   //개인정보 보기로 리다일렉트
		   return new ModelAndView("redirect:/users/info.do");
	   }
	   
	   //회원탈퇴 요청처리
	   @RequestMapping("/users/delete")
	   public ModelAndView authDelete(HttpServletRequest request,
			   ModelAndView mView) {
		   //서비스를 통해서 회원 탈퇴 처라
		   service.delete(mView, request.getSession());
		   //view페이지로 이동
		   mView.setViewName("users/delete");
		   return mView;
	   }
}
