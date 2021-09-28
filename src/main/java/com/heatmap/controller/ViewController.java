package com.heatmap.controller;

import java.net.URLDecoder;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heatmap.constants.AppConstant;
import com.heatmap.model.UserDetails;
import com.heatmap.service.UserDetailsService;
import com.heatmap.util.PasswordUtil;

@Controller
public class ViewController {

	private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordUtil passwordUtil;

	@GetMapping(AppConstant.TEMPLATEDOWNLOADURL)
	public String templateDownload(Model model, HttpSession session) {
		logger.info("Executing method templateDownload of class ViewController");
		if (session.getAttribute(AppConstant.USEREMAIL) == null)
			return AppConstant.LOGIN;
		model.addAttribute(AppConstant.USERENAME, session.getAttribute(AppConstant.USERENAME));
		return AppConstant.TEMPLATEDOWNLOAD;
	}

	@GetMapping(AppConstant.UPLOADFILEURL)
	public String uploadFile(Model model, HttpSession session) {
		logger.info("Executing method uploadFile of class ViewController");
		if (session.getAttribute(AppConstant.USEREMAIL) == null)
			return AppConstant.LOGIN;
		model.addAttribute(AppConstant.USERENAME, session.getAttribute(AppConstant.USERENAME));
		return AppConstant.UPLOADFILE;
	}

	@GetMapping(AppConstant.CHARTURL)
	public String chart(Model model, HttpSession session) {
		logger.info("Executing method chart of class ViewController");
		if (session.getAttribute(AppConstant.USEREMAIL) == null)
			return AppConstant.LOGIN;
		model.addAttribute(AppConstant.USERENAME, session.getAttribute(AppConstant.USERENAME));
		return AppConstant.CHART;
	}

	@GetMapping(AppConstant.ACTIVATE)
	public String activate(@RequestParam String token) {
		logger.info("Executing method activate of class ViewController");
		if (token == null || token.trim().equals("")) {
			return AppConstant.INVALIDTOKEN;
		} else {
			System.out.println("token=="+token);
			String email = passwordUtil.decryptPassword(token);
			UserDetails ud = userDetailsService.findByEmail(email);
			if (ud == null) {
				return AppConstant.INVALIDTOKEN;
			} else {
				if (ud.getStatus().equals(AppConstant.ACTIVE)) {
					return AppConstant.LOGIN;
				} else {
					ud.setStatus(AppConstant.ACTIVE);
					userDetailsService.save(ud);
					return AppConstant.ACCOUNTACTIVATED;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new ViewController().activate("VNxPa2dW8RVg5y4xksJwV63X7OfLcJPf+3C6qLg2YTY=");
	}

}
