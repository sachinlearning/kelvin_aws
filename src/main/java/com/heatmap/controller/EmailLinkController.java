package com.heatmap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.heatmap.constants.AppConstant;

@Controller
public class EmailLinkController {
	
	private static final Logger logger=LoggerFactory.getLogger(EmailLinkController.class);
	
	@GetMapping(AppConstant.EMAILLINKURL)
	public String renderPageFromEmail(){
		logger.info("Executing method renderPageFromEmail of class EmailLinkController");
		return AppConstant.EMAILLINK;
	}

}
