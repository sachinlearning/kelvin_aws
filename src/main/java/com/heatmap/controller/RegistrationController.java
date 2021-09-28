package com.heatmap.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.heatmap.constants.AppConstant;
import com.heatmap.model.UserDetails;
import com.heatmap.service.UserDetailsService;
import com.heatmap.util.ExceptionUtil;
import com.heatmap.util.PasswordUtil;
import com.heatmap.util.PropertiesFileUtility;

@Controller
public class RegistrationController {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private PasswordUtil passwordUtil;

	@Autowired
	private PropertiesFileUtility propFileUtility;

	@Autowired
	private ExceptionUtil expUtil;

	@GetMapping(AppConstant.REGISTERURL)
	public String registerUser() {
		logger.info("Executing method register of class RegistrationController");
		return AppConstant.REGISTER;
	}

	@PostMapping(AppConstant.REGISTERACTIONURL)
	public String submitRegisteredForm(Model model, @ModelAttribute("userDetails") UserDetails userDetails,
			HttpServletRequest request) {
		logger.info("Executing method submit of class RegistrationController");
		model.addAttribute("firstName", userDetails.getFirstName());
		model.addAttribute("lastName", userDetails.getLastName());
		model.addAttribute("email", userDetails.getEmail());
		model.addAttribute("company", userDetails.getCompany());
		String confirmPassword = request.getParameter("confirmPassword");
		if (userDetails.getEmail() != null && !(userDetails.getEmail().trim().equals(""))
				&& userDetails.getPassword() != null && !(userDetails.getPassword().trim().equals(""))
				&& userDetails.getFirstName() != null && !(userDetails.getFirstName().trim().equals(""))
				&& userDetails.getLastName() != null && !(userDetails.getLastName().trim().equals(""))
				&& userDetails.getCompany() != null && !(userDetails.getCompany().trim().equals(""))
				&& confirmPassword != null && !(confirmPassword.trim().equals(""))) {
			String enteredEmail = userDetails.getEmail().toLowerCase();
			String enteredPassword = userDetails.getPassword();
			UserDetails ud = userDetailsService.findByEmail(enteredEmail);
			if (ud != null) {
				model.addAttribute(AppConstant.ERROR, propFileUtility
						.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "alreadyRegisteredEmail"));
				return AppConstant.REGISTER;
			} else {
				if (!enteredPassword.equals(confirmPassword)) {
					model.addAttribute(AppConstant.ERROR, propFileUtility
							.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "passwordDidNotMatch"));
					return AppConstant.REGISTER;
				}
			}
			userDetails.setFirstName(userDetails.getFirstName().toLowerCase());
			userDetails.setLastName(userDetails.getLastName().toLowerCase());
			userDetails.setStatus(AppConstant.INACTIVE);
			userDetails.setEmail(enteredEmail);
			userDetails.setCreatedOn(new Date());
			userDetails.setNoOfDays(AppConstant.NOOFDAYSOFMAILACTIVATION);
			userDetails.setPassword(passwordUtil.encryptPassword(userDetails.getPassword()));
			userDetailsService.save(userDetails);
			// send activation mail
			sendActivationMail(enteredEmail, userDetails.getFirstName(), userDetails.getLastName(), request);
			return AppConstant.USERREGISTERED;
		} else {
			model.addAttribute(AppConstant.ERROR,
					propFileUtility.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "allFieldsAreRequired"));
			return AppConstant.REGISTER;
		}
	}

	private void sendActivationMail(String email, String firstName, String lastName, HttpServletRequest request) {
		logger.info("Executing method sendActivationMail of class RegistrationController");
		try {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message);
			helper.setFrom(AppConstant.MAILFROM, AppConstant.FROMNAME);
			helper.setTo(email);
			helper.setText("<html><body><b>Dear " + StringUtils.capitalize(firstName) + " "
					+ StringUtils.capitalize(lastName) + ",</b><br><br>"
					+ propFileUtility.getProperty("mailContentForActivation") + "<br><a href='"
					+ String.format("%s://%s:%d/%s", request.getScheme(), request.getServerName(),
							request.getServerPort(), request.getContextPath())
					+ propFileUtility.getProperty("activation") + "?token=" + URLEncoder.encode(passwordUtil.encryptPassword(email))
					+ "'>Click here</a><body></html>", true);
			helper.setSubject(propFileUtility.getProperty("subjectOfActivation"));
			sender.send(message);
		} catch (UnsupportedEncodingException | MessagingException e) {
			logger.error(expUtil.getExceptionMessage(e));
		}
	}
}
