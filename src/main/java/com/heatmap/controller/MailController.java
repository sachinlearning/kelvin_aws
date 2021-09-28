package com.heatmap.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heatmap.constants.AppConstant;
import com.heatmap.model.UserDetails;
import com.heatmap.service.UserDetailsService;
import com.heatmap.util.ExceptionUtil;
import com.heatmap.util.PropertiesFileUtility;

@RestController
public class MailController {

	@Autowired
	private JavaMailSender sender;

	private static final Logger logger = LoggerFactory.getLogger(MailController.class);

	@Autowired
	private PropertiesFileUtility propFileUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ExceptionUtil expUtil;


	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@PostMapping("/api/sendMail")
	public ResponseEntity<String> sendMail(HttpSession session, HttpServletRequest request) {
		logger.info("Executing method sendMail of class MailController");
		try {
			String email = (String) session.getAttribute(AppConstant.USEREMAIL);
			if (email == null)
				return new ResponseEntity<>("User has not logged in", HttpStatus.OK);
			UserDetails userDetails = userDetailsService.findByEmail(email);
			setDataToSendEmail(userDetails.getFirstName(), userDetails.getLastName(), email, userDetails.getUniqueId(),
					request);
		} catch (UnsupportedEncodingException | MessagingException e) {
			logger.error(expUtil.getExceptionMessage(e));
			return new ResponseEntity<>("Exception in sending mail", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("Mail Sent Successfully", HttpStatus.OK);
	}

	private void setDataToSendEmail(String firstName, String lastName, String email, String uniqueString,
			HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
		logger.info("Executing method setDataToSendEmail of class MailController");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(AppConstant.MAILFROM,AppConstant.FROMNAME);
		helper.setTo(email);
		helper.setText("<html><body><b>Dear " + StringUtils.capitalize(firstName) + " " + StringUtils.capitalize(lastName) + ",</b><br><br>"
				+ propFileUtil.getProperty("mailContent") + "<br><br><a href='"
				+ String.format("%s://%s:%d/%s", request.getScheme(), request.getServerName(),
						request.getServerPort(),request.getContextPath())
				+ propFileUtil.getProperty("link") + "?uniqueId=" + uniqueString + "'>Click here</a><body></html>", true);
		helper.setSubject(propFileUtil.getProperty("subject"));
		sender.send(message);
	}

}
