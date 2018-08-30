package test.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import test.service.WritingService;

public class MainClass3 {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("test/main/init.xml");
		//spring bean container로부터 WritingService type 객체의
		//참조값 얻어내기
		WritingService service=context.getBean(WritingService.class);
		String result=service.writeAndGet(999);
		//writeAndGet메소드로 전달. 호출되기 전후에 aop에서 읽어낼수있다
		
		System.out.println("main 메소드 result:"+result);
		
	}
}
