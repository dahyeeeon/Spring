package com.gura.spring03.shop.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring03.shop.dto.ShopDto;

//@Repository를 붙여야 sql에서 데이터acception으로 바꾸어줌
@Repository
public class ShopDaoImpl implements ShopDao{

	@Autowired
	private SqlSession session;
	
	@Override
	public List<ShopDto> getList() {
		return session.selectList("shop.getList");
	}

	@Override
	public void minusCount(int num) {
		//재고 개수
		session.update("shop.minusCount",num);
		
	}

	@Override
	public void minusMoney(ShopDto dto) {
		//고객 잔고를 상품의 가격만큼 감소시키기
		session.update("shop.minusMoney",dto);
		
	}

	@Override
	public void plusPoint(ShopDto dto) {
		//상품 가격의 10% 를 포인트적립
		session.update("shop.plusPoint",dto);
		
	}

	@Override
	public int getPrice(int num) {
		//인자로 전달된 상품 번호에 해당하는 상품의 가격을 리턴하는 메소드
		return session.selectOne("shop.getPrice",num);
	}

}
