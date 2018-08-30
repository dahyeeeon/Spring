package com.gura.spring03.shop.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.shop.dao.OrderDao;
import com.gura.spring03.shop.dao.ShopDao;
import com.gura.spring03.shop.dto.OrderDto;
import com.gura.spring03.shop.dto.ShopDto;

@Service
public class ShopServiceImpl implements ShopService {
	@Autowired
	private ShopDao shopDao;
	@Autowired
	private OrderDao orderDao;
	@Override
	public void getList(ModelAndView mView) {
		//ShopDao 객체를 이용해서 상품목록을 얻어와서
				List<ShopDto> list=shopDao.getList();
				//인자로 전달된 ModelAndView 객체에 담는다.
				//request영역 담기
				mView.addObject("list",list);
	}
	
	/* 트랜잭션 설정 방법
	 * 1.pom.xml 에 spring-tx depndency 추가
	 * 2.servlet-context.xml에 transaction 설정 추가
	 * 3.트랜잭션 적용할 서비스의 메소드에 @Transactional 어노테이션 추가
	 * 
	 * */
	@Transactional
	@Override
	public void buy(HttpServletRequest request, ModelAndView mView) {
		//로그인된 아이디
		String id=(String)request.getSession().getAttribute("id");
		//구입할 상품 번호
		int num=Integer.parseInt(request.getParameter("num"));
		//두개의 정보를 ShopDto에 담고
		//1.상품의 가격정보 얻어오기
		int price=shopDao.getPrice(num);
	//2.가격 만큼 계좌 잔액을 줄인다.
		ShopDto dto=new ShopDto();
		dto.setPrice(price);
		dto.setNum(num);
		dto.setId(id);
		shopDao.minusMoney(dto);
		//3.가격의 10%를 포인트로 적립
		shopDao.plusPoint(dto);
		//4.재고의 갯수를 -1
		shopDao.minusCount(num);
		//5.배송 요청 정보를 입력한다.
		OrderDto orderDto=new OrderDto();
		orderDto.setId(id);
		orderDto.setCode(num);
		orderDto.setAddr("노량진");
		orderDao.addOrder(orderDto);
	}
}
