package com.gura.spring02;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//1.클래스가 컨트롤러 역할을 할 수 있도록
@Controller
public class HomeController {
	//2.어떤 요청을 처리할지 요청 맵핑(value="home")
	@RequestMapping("/home") 
	public String home() {
		//3.비즈니스 로직 처리
		
		//4.view페이지의 정보리턴
		return "home"; //WEB-INF/views/home.jsp
					//(prefix)home(suffix)
	}
	
}
	