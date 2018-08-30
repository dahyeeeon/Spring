package com.gura.spring03.cafe.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.cafe.dao.CafeCommentDao;
import com.gura.spring03.cafe.dao.CafeDao;
import com.gura.spring03.cafe.dto.CafeCommentDto;
import com.gura.spring03.cafe.dto.CafeDto;
import com.gura.spring03.exception.ForbiddenException;

@Service
public class CafeServiceImpl implements CafeService{
	private static final int PAGE_ROW_COUNT=5;
	//하단 디스플레이 페이지 갯수
	private static final int PAGE_DISPLAY_COUNT=3;
	
	@Autowired
	private CafeDao dao;
	@Autowired
	private CafeCommentDao cafeCommentdao;
	
	@Override
	public void list(ModelAndView mView,int pageNum,
			String keyword, String condition) {

		//dto 객체 생성
		CafeDto dto=new CafeDto();
		if(keyword !=null) {
			//검색어가 전달 된 경우
			if(condition.equals("titlecontent")) { //제목+내용 검색
				dto.setTitle(keyword);
				dto.setContent(keyword);
			}else if(condition.equals("title")) { //제목 검색
				dto.setTitle(keyword);
			}else if(condition.equals("writer")) { //작성자 검색
				dto.setWriter(keyword);
			}
			//list.jsp에서 필요한 내용 담기
			mView.addObject("condition", condition);
			mView.addObject("keyword", keyword);
		}
		

		//보여줄 페이지 데이터의 시작 ResultSet row 번호
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		//보여줄 페이지 데이터의 끝 ResultSet row 번호
		int endRowNum=pageNum*PAGE_ROW_COUNT;
		
		//전체 row 의 갯수를 읽어온다.
		int totalRow=dao.getCount(dto);
		//전체 페이지의 갯수 구하기
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
		//시작 페이지 번호
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		//끝 페이지 번호
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		//끝 페이지 번호가 잘못된 값이라면 
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; //보정해준다. 
		}
		
		// 위에서 만든 CafeDto 에 추가정보를 담는다
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		//1. FileDto 객체를 전달해서 파일 목록을 불러온다 
		List<CafeDto> list=dao.getList(dto);
		
		//2. request 에 담고
		mView.addObject("list", list);
		// 페이징 처리에 관련된 값도 request 에 담기 
		mView.addObject("pageNum", pageNum);
		mView.addObject("startPageNum", startPageNum);
		mView.addObject("endPageNum", endPageNum);
		mView.addObject("totalPageCount", totalPageCount);
		//전체 row의 갯수도 전달하기
		mView.addObject("totalRow", totalRow);
				

		mView.setViewName("/views/cafe/list.jsp");
	
	}
	@Override
	public void insert(HttpServletRequest request, CafeDto dto) {
		
		String id=(String)request.getSession().getAttribute("id");
		dto.setWriter(id);
		dao.insert(dto);
		
	}

	@Override
	public void delete(HttpServletRequest request,int num) {
		/*
		 * 로그인된 아이디와 삭제할 글의 작성자와 다르면
		 * ForbiddenException 발생시키기
		 * */
		String id=(String)request.getSession().getAttribute("id");
		CafeDto dto=new CafeDto();
		dto.setNum(num);
		//해당작성자읽어오기
		String writer=dao.getData(dto).getWriter();
		if(!id.equals(writer)) {
			throw new ForbiddenException();
		}
		dao.delete(num);
		
	}

	@Override
	public void commentInsert(CafeCommentDto dto) {
		//저장할 댓글의 번호를 미리 얻어낸다.
		int seq=cafeCommentdao.getSequence();
		//댓글을 DB 에 저장
		dto.setNum(seq);
		//댓글의 그룹번호를 읽어오는데 0 ehsms ekfms tntwkrk emfdjdlTdma
		int comment_group=dto.getComment_group();
		if(comment_group==0) {//원글의 댓글인 경우
			dto.setComment_group(seq);
		}
		//새 댓글을 저장한다.
		cafeCommentdao.insert(dto);
		
	}
	@Override
	public void getData(HttpServletRequest request) {
		int num=Integer.parseInt(request.getParameter("num"));
		//검색과 관련된 파라미터를 읽어와 본다.
		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");		
		
		//CafeDto 객체를 생성해서 
		CafeDto dto=new CafeDto();
		if(keyword != null) {//검색어가 전달된 경우 
			if(condition.equals("titlecontent")) {//제목+내용 검색
				dto.setTitle(keyword);
				dto.setContent(keyword);
			}else if(condition.equals("title")) {//제목 검색
				dto.setTitle(keyword);
			}else if(condition.equals("writer")) {//작성자 검색
				dto.setWriter(keyword);
			}
			//list.jsp 에서 필요한 내용 담기
			request.setAttribute("condition", condition);
			request.setAttribute("keyword", keyword);
		}		
		//글번호도 dto 에 담는다.
		dto.setNum(num);
		
		//2. CafeDao 를 이용해서 글정보를 읽어와서
		CafeDto resultDto=dao.getData(dto);
		// 글 조회수 올리기
		dao.addViewCount(num);
	
		//3. request 에 담고
		request.setAttribute("dto", resultDto);
		//로그인 여부 확인해서 request에 담기
		String id=(String)request.getSession().getAttribute("id");
		boolean isLogin=false;
		if(id!=null) {
			isLogin=true;
		}
		request.setAttribute("isLogin", isLogin);
		
		List<CafeCommentDto> list=cafeCommentdao.getList(num);
		request.setAttribute("list", list);
	
		
	}
	@Override
	public void update(HttpServletRequest request, CafeDto dto) {
		int num=Integer.parseInt(request.getParameter("num"));
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		//CafeDto에 담기
		
		dto.setTitle(title);
		dto.setContent(content);
		dto.setNum(num);
		//CafeDao를 이용해서 수정 반영
		dao.update(dto);
		
	}
	@Override
	public void updateform(HttpServletRequest request, CafeDto dto) {
		int num=Integer.parseInt(request.getParameter("num")); 
		dto.setNum(num);
		//2.수정할 글정보 얻어오기
		CafeDto resultDto=dao.getData(dto);
		//3.request에 담고 view페이지 보여주기
		request.setAttribute("dto", resultDto);
		
	}


}
