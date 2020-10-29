package com.example.member.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class DemoExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(DemoExceptionHandler.class);
	
	@ExceptionHandler({DemoException.class})
	public final void handlerException(DemoException demoException) {
		logger.info("handle DemoException...");
		logger.info("error code - {}", demoException.getErrorCode());
		logger.info("error messsage - {}", demoException.getErrorMessage());
	}
}
