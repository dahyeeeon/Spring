package test.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import test.service.WritingService;

public class MainClass2 {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("test/main/init.xml");
		//spring bean container로부터 WritingService type 객체의
		//참조값 얻어내기
		WritingService service=context.getBean(WritingService.class);
		service.write();
		service.writeToFriend();
		service.writeToTeacher("김구라");
		service.writeToTeacher(null);
		
	}
}
