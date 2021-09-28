package com.heatmap.controller;

import javax.servlet.RequestDispatcher;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyErrorController implements ErrorController {

	private static final Logger logger = LoggerFactory.getLogger(MyErrorController.class);

	@GetMapping("/error")
	public String handleError(HttpServletRequest request) {
		logger.info("Executing method handleError of class ErrorController");
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error-404";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "error-500";
			}
		}
		return "error";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
