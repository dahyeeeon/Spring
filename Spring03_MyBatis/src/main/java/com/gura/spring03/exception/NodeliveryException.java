package com.gura.spring03.exception;

import org.springframework.dao.DataAccessException;
//배송 불가능한 상황에서 발생시킬 예외처리
public class NodeliveryException extends DataAccessException {

	public NodeliveryException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
