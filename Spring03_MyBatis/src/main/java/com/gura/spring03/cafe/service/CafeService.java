package com.gura.spring03.cafe.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.cafe.dto.CafeCommentDto;
import com.gura.spring03.cafe.dto.CafeDto;

public interface CafeService {
	public void insert(HttpServletRequest request,CafeDto dto);
	public void delete(HttpServletRequest request,int num);
	public void update(HttpServletRequest request,CafeDto dto);
	public void updateform(HttpServletRequest request,CafeDto dto);
	public void list(ModelAndView mView,int pageNum,
			String keyword, String condition);
	public void getData(HttpServletRequest request);
	public void commentInsert(CafeCommentDto dto);

}
