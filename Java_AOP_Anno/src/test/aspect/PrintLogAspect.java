package test.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
/*
 * 횡단 관심사(Cross Concern)를 Aspect로 작성하기
 * 
 * */

/*
 * -Aspectj Expression
 * 
 * 1.execution(public * * (..))->접근 지정자가 public 인 메소드가 point cut
 * 2.execution(* test.service.*.*(..))->test.service 패키지의 모든 메소드 point cut
 * 3.execution(public void insert*(..))->접근 지정자는 public 리턴타입은 void, 
 * 메소드명이 insert로 시작하는 모든 메소드가 point cut
 * 4.execution(* delete *(*))->메소드 명이 delete로 시작하고 인자로 1개 전달받는 
 * 메소드가 point cut
 * 5.execution(*delete * (*.*))->메소드명이 delete로 시작하고 인자로 2개 전달받는
 * 메소드가 point cut
 * 
 */
@Aspect //aspect가 되기 위한 설정
@Component //bean으로 만들기 위한 설정
public class PrintLogAspect {
	
	@Around("execution(public void insert())")
	public Object printLog(ProceedingJoinPoint joinPoint)
			throws Throwable{
		System.out.println("--비즈니스 로직을 수행하기 이전입니다.");
		/*
		 * joinPoint 객체의 proceed() 메소드를 호출하는 시점이
		 * AOP가 적영된 메소드가 수행되는 시점
		 * 
		 * .proceed() 메소드가 리턴해주는 객체가 AOP가 적용된 메소드
		 * 리턴해주는 객체이다.
		 * 
		 * 만일 AOP가 적영된 메소드의 리턴 type이 void 이면
		 * obj는 null이다
		 * */
		Object obj=joinPoint.proceed();
		
		System.out.println("--비즈니스 로직을 수행했습니다.");
		return obj;
		
	}
}
