package com.gura.spring03.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/*예외처리를 하는 컨트롤러
 * 1.@ControllerAdvice 어노테이션 붙이고
 * 2.component scan해서 bean으로 만들기
 * 3.@ExceptionHandler(예외 type)을 메소드에 붙여준다
 * */

//ControllerAdvice:aop로 적용할 bean
@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(ForbiddenException.class)
	public ModelAndView error403(ForbiddenException fe) {
		ModelAndView mView=new ModelAndView();
		mView.setViewName("error/403"); // ->/views/error/403.jsp
		return mView;

			
	}
	//nodeliveryException 이 발생하면 호출되는 메소드
	@ExceptionHandler(NodeliveryException.class)
	public ModelAndView errorNodelivery(NodeliveryException ne) {
		ModelAndView mView=new ModelAndView();
		mView.addObject("msg",ne.getMessage());
		mView.setViewName("error/data_access");
		return mView;
	}

		/*
		 * @Repository 어노테이션이 붙어있는 Dao 에서 Db관련
		 *Exception이 발생시스프링이 DataAccessException으로
		 *바꿔서 발생시킨다.
		 * 
		 * */
	@ExceptionHandler(DataAccessException.class)
	public ModelAndView errorDataAccess(DataAccessException dae) {
		ModelAndView mView=new ModelAndView();
		//예외정보(문자열)을 request에 담기
		mView.addObject("msg",dae.getMessage());
		
		mView.setViewName("error/data_access");
		return mView;
	}

}
