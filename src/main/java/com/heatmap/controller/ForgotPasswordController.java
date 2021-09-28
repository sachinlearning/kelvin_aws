package com.heatmap.controller;

import java.io.UnsupportedEncodingException;
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
import com.heatmap.util.CommonUtil;
import com.heatmap.util.ExceptionUtil;
import com.heatmap.util.PasswordUtil;
import com.heatmap.util.PropertiesFileUtility;

@Controller
public class ForgotPasswordController {

	private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private PropertiesFileUtility propFileUtility;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PropertiesFileUtility propFileUtil;

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private ExceptionUtil exceptionUtil;

	@Autowired
	private PasswordUtil passwordUtil;

	@GetMapping(AppConstant.FORGOTPASSWORDURL)
	public String forgotPassword() {
		logger.info("Executing method forgotPassword of class ForgotPasswordController");
		return AppConstant.FORGOTPASSWORD;
	}

	
	@PostMapping(AppConstant.SENDOTP)
	public String sendOtp(Model model, @ModelAttribute("userDetails") UserDetails userDetails) {
		logger.info("Executing method sendOtp of class ForgotPasswordController");
		if (userDetails != null && userDetails.getEmail() != null && !(userDetails.getEmail().trim().equals(""))) {
			String enteredEmail = userDetails.getEmail().toLowerCase();
			userDetails = userDetailsService.findByEmail(enteredEmail);
			model.addAttribute(AppConstant.USEREMAIL, enteredEmail);
			if (userDetails != null) {
				String otp = commonUtil.getOtp();
				userDetailsService.generateOtp(otp, commonUtil.getOtpExpirationTime(), userDetails.getuId());
				try {
					sendOtpOnEmail(enteredEmail, otp, userDetails.getFirstName(), userDetails.getLastName());
				} catch (UnsupportedEncodingException | MessagingException e) {
					logger.error(exceptionUtil.getExceptionMessage(e));
				}
				return AppConstant.OTP;
			}
			model.addAttribute(AppConstant.ERROR, propFileUtility
					.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, AppConstant.EMAILNOTFOUND));
			return AppConstant.FORGOTPASSWORD;
		} else {
			model.addAttribute(AppConstant.ERROR,
					propFileUtility.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "blankEmail"));
			return AppConstant.FORGOTPASSWORD;
		}
	}

	private void sendOtpOnEmail(String email, String otp, String firstName, String lastName)
			throws UnsupportedEncodingException, MessagingException {
		logger.info("Executing method sendOtpOnEmail of class ForgotPasswordController");
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(AppConstant.MAILFROM, AppConstant.FROMNAME);
		helper.setTo(email);
		helper.setText("<html><body><b>Dear " + StringUtils.capitalize(firstName) + " " + StringUtils.capitalize(lastName) + ",</b><br><br>"
				+ propFileUtil.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "otpMsg").replace("%1", otp)
				+ "<body></html>", true);
		helper.setSubject(propFileUtil.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "otpSubject"));
		sender.send(message);
	}

	@PostMapping(AppConstant.CHECKOTP)
	public String checkOtp(Model model, @ModelAttribute("userDetails") UserDetails userDetails) {
		logger.info("Executing method checkOtp of class ForgotPasswordController");
		model.addAttribute(AppConstant.USEREMAIL, userDetails.getEmail());
		if (userDetails.getEmail() != null && !userDetails.getEmail().trim().equals("") && userDetails.getOtp() != null
				&& !userDetails.getOtp().trim().equals("")) {
			String email = userDetails.getEmail();
			String otp = userDetails.getOtp().trim();
			userDetails = userDetailsService.findByEmail(email);
			if (userDetails != null) {
				if (userDetails.getOtp().trim().equals(otp)) {
					if (new Date().compareTo(userDetails.getOtpExpirationTime()) > 0) {
						model.addAttribute(AppConstant.ERROR, propFileUtility
								.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "otpExpired"));
						return AppConstant.OTP;
					} else {
						return AppConstant.CHANGEPASSWORD;
					}
				} else {
					model.addAttribute(AppConstant.ERROR, propFileUtility
							.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "otpDidntMatch"));
					return AppConstant.OTP;
				}
			}
			model.addAttribute(AppConstant.ERROR, propFileUtility
					.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, AppConstant.EMAILNOTFOUND));
			return AppConstant.OTP;
		} else {
			model.addAttribute(AppConstant.ERROR,
					propFileUtility.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "blankOTP"));
			return AppConstant.OTP;
		}
	}

	@PostMapping(AppConstant.UPDATEPASSWORD)
	public String updatePassword(Model model, @ModelAttribute("userDetails") UserDetails userDetails,HttpServletRequest request) {
		logger.info("Executing method updatePassword of class ForgotPasswordController");
		String confirmPassword=request.getParameter("confirmPassword");
		if (userDetails.getEmail() != null && !(userDetails.getEmail().trim().equals(""))
				&& userDetails.getPassword() != null && !(userDetails.getPassword().trim().equals(""))
				&& confirmPassword != null && !(confirmPassword.trim().equals(""))) {
			String enteredEmail = userDetails.getEmail();
			String enteredPassword = userDetails.getPassword();
			if(!enteredPassword.equals(confirmPassword)){
				model.addAttribute(AppConstant.USEREMAIL, userDetails.getEmail());
				model.addAttribute(AppConstant.ERROR,
						propFileUtility.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "passwordDidNotMatch"));
				return AppConstant.CHANGEPASSWORD;
			}
			userDetailsService.updatePassword(passwordUtil.encryptPassword(enteredPassword),new Date(), enteredEmail);
			return AppConstant.PASSWORDUPDATED;
			
		} else {
			model.addAttribute(AppConstant.USEREMAIL, userDetails.getEmail());
			model.addAttribute(AppConstant.ERROR,
					propFileUtility.getValueFromPropertiesFile(AppConstant.PROPERTIESFILENAME, "blankPassword"));
			return AppConstant.CHANGEPASSWORD;
		}
	}
}
