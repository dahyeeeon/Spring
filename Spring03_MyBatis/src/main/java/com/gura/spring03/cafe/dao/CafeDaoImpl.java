package com.gura.spring03.cafe.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring03.cafe.dto.CafeDto;

@Repository
public class CafeDaoImpl implements	CafeDao{
	@Autowired
	private SqlSession session;

	@Override
	public void insert(CafeDto dto) {
		session.insert("cafe.insert",dto);
		
	}

	@Override
	public int getCount(CafeDto dto) {
		return session.selectOne("cafe.getCount",dto);
		
	}

	@Override
	public List<CafeDto> getList(CafeDto dto) {
		return session.selectList("cafe.getList",dto);
	}

	@Override
	public CafeDto getData(CafeDto dto) {
		return session.selectOne("cafe.getData",dto);
	}

	@Override
	public void delete(int num) {
		session.delete("cafe.delete",num);
		
	}

	@Override
	public void update(CafeDto dto) {
		session.update("cafe.update",dto);
	}

	@Override
	public void addViewCount(int num) {
		session.update("cafe.addViewCount", num);
		
	}
	
	
}
