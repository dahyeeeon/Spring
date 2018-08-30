package com.gura.spring03.cafe.dao;

import java.util.List;

import com.gura.spring03.cafe.dto.CafeDto;

public interface CafeDao {
	//새글을 저장하는 메소드
	public void insert(CafeDto dto);
	//글의 갯수를 리턴하는 메소드 
	public int getCount(CafeDto dto);
	//글목록을 리턴하는 메소드(startrownum 같은 정보를 가져옴)
	public List<CafeDto> getList(CafeDto dto);
	//글 하나의 정보를 리턴하는 메소드(prevnum,nextnum..)
	public CafeDto getData(CafeDto dto);
	//글정보를 삭제하는 메소드
	public void delete(int num);
	//글정보 수정하는 메소드 
	public void update(CafeDto dto);
	//조회수를 증가 시키는 메소드
	public void addViewCount(int num);
}
