package com.gura.spring03.cafe.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring03.cafe.dto.CafeCommentDto;
@Repository
public class CafeCommentDaoImpl implements CafeCommentDao{
	@Autowired
	private SqlSession session;
	
	@Override
	public void insert(CafeCommentDto dto) {
		session.insert("cafeComment.insert",dto);
		
	}

	@Override
	public int getSequence() {
		//새로 저장할 댓글의 글번호를 미리 얻어옴
		return session.selectOne("cafeComment.getSequence");
	}

	@Override
	public List<CafeCommentDto> getList(int ref_group) {
		return session.selectList("cafeComment.getList",ref_group);
	}

	@Override
	public void delete(int num) {
		session.delete("cafeComment.delete",num);
		
	}

	@Override
	public void update(CafeCommentDto dto) {
		session.update("cafeComment.update", dto);
		
	}

	@Override
	public CafeCommentDto getData(CafeCommentDto dto) {
		return session.selectOne("cafeComment.getData", dto);
	}

}
