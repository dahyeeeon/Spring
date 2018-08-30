package test.service;

import org.springframework.stereotype.Component;


//component scan 했을때 bean으로 만드는 어노테이션
@Component

//3가지타입
//extends object
//WritingService,WritingServiceImpl ->스프링에서 주로 interface
public class WritingServiceImpl implements WritingService{

	@Override
	public void write() {
		System.out.println("글쓰기 작업중..");
		try {
			Thread.sleep(1000);
			
		}catch(Exception e) {};
		
	}

	@Override
	public void writeToFriend() {
		System.out.println("친구에게 글쓰기 작업중..");
		try {
			Thread.sleep(1000);
			
		}catch(Exception e) {};
		
	}

	@Override
	public void writeToTeacher(String name) {
		System.out.println(name+"선생님 글쓰는중");
		try {
			Thread.sleep(1000);
			
		}catch(Exception e) {};
		
	}

	@Override
	public String writeAndGet(int num) {
		System.out.println("글쓰기 작업하고 문자열을 돌려줌");
		return "Acorn";
	}

}
