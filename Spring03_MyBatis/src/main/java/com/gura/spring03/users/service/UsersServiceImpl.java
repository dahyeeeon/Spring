package com.gura.spring03.users.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.users.dao.UsersDao;
import com.gura.spring03.users.dto.UsersDto;

@Service
public class UsersServiceImpl implements UsersService{
	//의존객체 DI
	@Autowired
	private UsersDao dao;
	
	@Override
	public boolean canUseId(String id) {
		//인자로 전달된 아이디의 사용가능 여부를 리턴해준다.
		return dao.canUseId(id);
	}
	
	//회원 가입 처리하는 서비스 메소드
	@Override
	public void signup(ModelAndView mView, UsersDto dto) {
		//비밀번호 암호화를 도와주는 객체 생성
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		//UserdDto 에 있는 비밀번호를 암호화 한다
		String encodedPwd=encoder.encode(dto.getPwd());
		//암호화된 비밀번호를 UsersDto에 다시 담는다.
		dto.setPwd(encodedPwd);
		//Dao를 이용해서 회원정보를 저장한다
		dao.insert(dto);
		//request에 담을 내용을 ModelAndView 객체에 담는다.
		mView.addObject("msg",dto.getId()+"회원님이 가입되었습니다.");
		
		
		
	}
	//로그인 관련 처리하는 서비스 메소드
	@Override
	public void login(ModelAndView mView, UsersDto dto, HttpSession session) {
		//인자로 전달된 Dto에 있는 회원의 아이디를 이용해 select
		UsersDto resultDto=dao.getData(dto.getId());
		//로그인 성공여부시 담을 변수
		boolean isLoginSuccess=false;
		//해당 아이디가 db에 존재한다면
		//일치시 로그인
		if(resultDto != null) {
			//사용자가 입력한 비밀번호와 db에 저장된 암호화된 비밀번호 비교
		isLoginSuccess=BCrypt.checkpw(dto.getPwd(), resultDto.getPwd());
		
		}
		if(isLoginSuccess) {
			//로그인 처리
			session.setAttribute("id", dto.getId());
		}
		mView.addObject("isLoginSuccess",isLoginSuccess);
	}

	 @Override
	   public void info(ModelAndView mView, HttpSession session) {
	      //세션에 저장된 아이디를 읽어와서
	      String id=(String)session.getAttribute("id");
	      //해당 회원 정보를 얻어와서
	      UsersDto dto=dao.getData(id);
	      //ModelAndView 객체에 담는다(request에 작업한는 대신)
	      mView.addObject("dto", dto);

	}

	@Override
	public void updateform(ModelAndView mView, HttpSession session) {
		String id=(String)session.getAttribute("id");
		UsersDto dto=dao.getData(id);
		mView.addObject("dto",dto);
	
	}

	@Override
	public void update(UsersDto dto) {
		//db에 수정반영
		dao.update(dto);
		
	}
	//인자로 전달된 비밀번호가 맞는 비밀번호인지
	//여부를 리턴하는 서비스 메소드
	@Override
	public boolean isValidPwd(String inputPwd, HttpSession session) {
		//세션 영역에 저장된 아이디를 읽어온다
		String id=(String)session.getAttribute("id");
		UsersDto dto=dao.getData(id);
		//일치하는지 여부
		boolean isVaild=BCrypt.checkpw(inputPwd, dto.getPwd());
		return isVaild;
	}

	@Override
	public void updatePwd(String pwd, HttpSession session) {
		//세션 영역에 저장된 아이디를 읽어온다
		String id=(String)session.getAttribute("id");
		//비밀번호 암호화
		String encodedPwd=new BCryptPasswordEncoder().encode(pwd);
		//UsersDto 객체에 두개의 정보 담기
		UsersDto dto=new UsersDto();
		dto.setId(id);
		dto.setPwd(encodedPwd);
		//UsersDto 객체를 인자로 전달해서 비밀번호 수정하기
		dao.updatePwd(dto);
	}

	@Override
	public void delete(ModelAndView mView, HttpSession session) {
		String id=(String)session.getAttribute("id");
		//db에서 해당정보 삭제
		dao.delete(id);
		session.invalidate();
		//modelAndView 객체에서 메세지를 담는다.
		mView.addObject("msg",id+"님이 회원탈퇴 되었습니다.");
		
	}
	
}
