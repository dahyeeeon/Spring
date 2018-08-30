package test.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WritingAspect {
	/*전급지정자:public
	 * 리턴타입:void
	 * 메소드명:write로 시작하는 메소드
	 * 메소드에 전달되는 인자:없음
	 * 위와 같은 모양의 메소드가 실행되기 이전에 적용되는 Advice
	 * 
	 * */
	//advice를 어디다 적용 시킬지
	@Before("execution(public void write*(..))")
	public void preparePen() {
		System.out.println("[글을 쓰기 위한 펜을 준비중...]");
		
	}
	/*
	 * 접근지정자 상관 없음
	 * 리턴타입:상관 없음
	 * 메소드명 write로 시작
	 * 메소드에 전달되는 인자 없음
	 * 
	 * 위와 같은 메소드가 실행된 이후에 적용되는 Advice
	 * */
	@After("execution(* write*())")
	public void endPen() {
		System.out.println("[글을 다 작성하고 펜을 닫는다...]");
		
	}
	
	@Around("execution(* write*(java.lang.String))") //직접 타입지정
	public void aroundWrite(ProceedingJoinPoint joinPoint)
	throws Throwable{
		//aop가 적영된 메소드에 전달된 인자를 Object[]로 얻어내기
		Object[] args=joinPoint.getArgs();
		//반복문 돌면서 하나씩 참조
		for(Object tmp:args) {
			//만일 우리가 찾는 타입이면(이 예제에서는 String type)
			if(tmp instanceof String) {
				//원하는 작업을 한다.
				System.out.println("aop에서 미리 조사함");
				System.out.println("전달된 name:"+tmp);
			}
		}
		
		System.out.println("[준비작업을 해요]");
		//aop가 적용된 메소드 수행하기
		joinPoint.proceed();
		System.out.println("[마무리 작업을 해요]");
		
	}
	
	@Around("execution(String write*(int))")
	public Object aroundWrite2(ProceedingJoinPoint joinPoint) 
	throws Throwable{
		Object[] args=joinPoint.getArgs();
		//전달된 인자가 1개이고 타입이 정수인게 확실함
		int num=(int)args[0];
		System.out.println("인자로 전달된 숫자:"+num);
		//aop가 적용된 메소드를 호출하고 그 메소드가
		//리턴해주는 객체를 Object type으로 받기
		Object obj=joinPoint.proceed();
		//return type이 String 이므로 casting
		String result=(String)obj;
		System.out.println("리턴된 문자열:"+result);
		
		//원한다면 다른 정보 리턴도 가능
		result="에이콘";
		return result;
	}
	
}

//
//package com.gura.step04.aspect;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.ModelAndView;
//
//@Aspect
//@Component
//public class AuthAspect {
//	/*
//	 *  컨트롤러에 특정 메소드에 aop 를 적용해서 로그인 했는지
//	 *  여부를 검사를 하는 메소드
//	 */
//	@Around("execution(* auth*(..))")
//	public Object loginCheck(ProceedingJoinPoint joinPoint) throws Throwable{
//		//aop 가 적용된 메소드에 전달된 인자들 얻어오기
//		Object[] args=joinPoint.getArgs();
//		//반복문 돌면서 하나씩 추출해서
//		for(Object tmp:args){
//			//만일 객체가 HttpServletRequest type 이라면
//			if(tmp instanceof HttpServletRequest){
//				//원래 type 으로 casting 해서 
//				HttpServletRequest request=(HttpServletRequest)tmp;
//				//로그인 정보가 있는지 확인 
//				String id=(String)request.getSession().getAttribute("id");
//				if(id==null){
//					//로그인 정보가 없으면 여기가 수행된다.
//					ModelAndView mView=new ModelAndView();
//					
//					//query 문자열 읽어오기
//					// a=xxx&b=xxx&c=xxx
//					String query=request.getQueryString();
//					
//					//원래 가야할 요청명 
//					String url=null;
//					if(query==null){
//						url=request.getRequestURI();
//					}else{
//						url=request.getRequestURI()+"?"+query;
//					}
//					
//					mView.setViewName("redirect:/users/loginform.do?url="+url);
//					// Spring Framework 에 ModelAndView 객체를 바로 리턴
//					return mView;
//				}
//			}
//		}
//		//정상적으로 수행하기
//		return joinPoint.proceed();
//	}
//}
