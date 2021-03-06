package com.gura.spring03.cafe.dao;

import java.util.List;

import com.gura.spring03.cafe.dto.CafeCommentDto;

public interface CafeCommentDao {
	public void insert(CafeCommentDto dto);
	//댓글의 글번호를 리턴해주는 메소드
	public int getSequence();
	//댓글 목록을 리턴하는 메소드
	public List<CafeCommentDto> getList(int ref_group);
	public void delete(int num);
	public void update(CafeCommentDto dto);
	public CafeCommentDto getData(CafeCommentDto dto);
}
