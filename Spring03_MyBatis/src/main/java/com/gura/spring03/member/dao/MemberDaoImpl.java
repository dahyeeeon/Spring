package com.gura.spring03.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring03.member.dto.MemberDto;

//1.MemberDaoImpl 객체가 spring bean 컨테이너에서 관리되게 한다.
@Repository
public class MemberDaoImpl implements MemberDao {
	
	//의존객체 자동주입(Dependency Injection)
	@Autowired
	private SqlSession session;
	
	@Override
	public void insert(MemberDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(MemberDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MemberDto getData(int num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MemberDto> getList() {
		List<MemberDto> list=session.selectList("member.getList");
		return list;
	}

}
