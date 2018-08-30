package test.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import test.service.CafeService;
import test.service.MemberService;

/*-AOP란 Aspect Oriented Programming (관전,관심 지향 프로그래밍)
 * 1.로그 찍고 분석하는 작업
 * 2.메소드의 성능(실행시간)측정
 * 3.트랜잭션 관리
 * 4.인증작업

 * */
public class MainClass {
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("test/main/init.xml");
	//MemberService type 객체의 참조값 얻어오기
		MemberService mService=context.getBean(MemberService.class);
		
		mService.insert();
		mService.select();
		
		CafeService cService=context.getBean(CafeService.class);
		cService.insert();
		cService.select();
	
	}
}
