package com.dzj.exception.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.dzj.exception.UserException;
import com.dzj.utils.JSONResult;


@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	
	private static final Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value= Exception.class)
	public JSONResult handlerException(Exception e) {
		if(e instanceof UserException) {
			UserException userException =(UserException) e;
			logger.error(userException.getMessage());
			return JSONResult.errorException(userException.getMessage());
		}
		
		return null;
		
	}
}
