package com.heatmap.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public String getOtp() {
		logger.info("Executing method getOtp of class CommonUtil");
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	public Date getOtpExpirationTime() {
		Date d =new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, 10);
		return cal.getTime();
	}

}
