package com.example.member.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class MemberExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(MemberExceptionHandler.class);
	
	@ExceptionHandler({MemberException.class})
	public final void handlerException(MemberException memberException) {
		logger.info("handle DemoException...");
		logger.info("error code - {}", memberException.getErrorCode());
		logger.info("error messsage - {}", memberException.getMessage());
	}
}
