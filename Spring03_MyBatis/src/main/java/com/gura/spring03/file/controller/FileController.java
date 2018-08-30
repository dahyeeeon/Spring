package com.gura.spring03.file.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring03.file.dto.FileDto;
import com.gura.spring03.file.service.FileService;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;
	
	@RequestMapping("/file/list")
	public ModelAndView list(ModelAndView mView,
			@RequestParam(defaultValue="1") int pageNum) {
		//pageNum 이라는 파라미터가 넘어오지 않으면 1페이지가 된다.
		fileService.getList(mView, pageNum);
		mView.setViewName("file/list");
		return mView;
	}
	
	@RequestMapping("/file/upload_form")
	public ModelAndView authUploadForm(HttpServletRequest request) {
		
		return new ModelAndView("file/upload_form");
	}
	
	@RequestMapping("/file/upload")
	//title,file 파라미터전송. enctype="multipart/form-data"
	//@ModelAttribute에서 받아서 ServiceImpl에서 받음
	public ModelAndView authUpload(HttpServletRequest request,
			@ModelAttribute FileDto dto) {
		//FileDto 에는 업로드된 파일의 정보도 같이 들어있다.
		fileService.insert(request, dto);
		//파일목록보기로 다시 리다일렉트
		return new ModelAndView("redirect:/file/list.do");
	}
	
	//파일 다운로드 요청 처리
	@RequestMapping("/file/download")
	public ModelAndView download(@RequestParam int num,
			ModelAndView mView) {
		//ModelAndView 객체에 다운로드
		fileService.getData(mView, num);
		
		//파일 다운로드 view로 forward이동해서 다운로드 시키기
		
		//mView.setViewName("file/download");jsp의 마크업 대신
		//파일다운로드 시켜주는 bean의 이름을 전달(빈을 찾는데 없으면 jsp를 찾게됨)
		mView.setViewName("fileDownView");
		
		return mView;
	}
	//파일삭제
	@RequestMapping("/file/delete")
	public ModelAndView authDelete(HttpServletRequest request,
			@RequestParam int num) {
		//서비스를 통해서 파일정보 삭제
		fileService.delete(request, num);
		//파일 목록 보기로 리다일렉트 이동
		return new ModelAndView("redirect:/file/list.do");
	}
}
