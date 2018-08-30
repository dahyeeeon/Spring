package test.main;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class MainClass {
	public static void main(String[] args) {
		//비밀번호라고 가정->select해도 알아볼 수 없도록 암호화하기
		String pwd="1234";
		//비밀번호 인코더 객체 생성
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		//비밀번호를 인코딩하기
		String hash1=encoder.encode(pwd);
		
		//인코딩된 문자열 출력해보기
		System.out.println(hash1);
	}
}
