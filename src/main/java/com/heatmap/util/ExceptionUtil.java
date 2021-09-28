package com.heatmap.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExceptionUtil {
	
	private static final Logger logger= LoggerFactory.getLogger(ExceptionUtil.class);
	
	public String getExceptionMessage(Exception exp){
		logger.info("Executing method getExceptionMessage of class ExceptionUtil");
		StringWriter sw = new StringWriter();
		exp.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}

}
