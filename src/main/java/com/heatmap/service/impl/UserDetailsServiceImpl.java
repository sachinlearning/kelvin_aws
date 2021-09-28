package com.heatmap.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.heatmap.model.UserDetails;
import com.heatmap.model.UploadedJsonData;
import com.heatmap.repository.UploadedJsonDataRepository;
import com.heatmap.repository.UserDetailsRepository;
import com.heatmap.service.UserDetailsService;
import com.heatmap.util.ExceptionUtil;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger=LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Autowired
	private UploadedJsonDataRepository uploadedJsonDataRepository;
	
	@Autowired
	private ExceptionUtil expUtil;

	@Override
	public int saveFileUploadData(UserDetails userDetails, UploadedJsonData uploadedJsonData) {
		logger.info("Executing method saveFileUploadData of class UserDetailsServiceImpl");
		userDetails.setUploadedJsonData(uploadedJsonData);
		uploadedJsonData.setUserDetails(userDetails);
		try {
			save(uploadedJsonData);
		} catch (DataAccessException e) {
			logger.error(expUtil.getExceptionMessage(e));
			return 0;
		}
		return 1;
	}


	@Override
	public UserDetails findByUniqueId(String uniqueId) {
		logger.info("Executing method findByUniqueId of class UserDetailsServiceImpl");
		return userDetailsRepository.findByUniqueId(uniqueId);
	}

	@Override
	public List<UserDetails> findAll() {
		logger.info("Executing method findAll of class UserDetailsServiceImpl");
		return userDetailsRepository.findAll();
	}

	@Override
	public UserDetails save(UserDetails userDetails) {
		logger.info("Executing method save of class UserDetailsServiceImpl");
		return userDetailsRepository.save(userDetails);
	}
	
	@Override
	public UploadedJsonData save(UploadedJsonData uploadedJsonData) {
		logger.info("Executing method save of class UserDetailsServiceImpl");
		return uploadedJsonDataRepository.save(uploadedJsonData);
	}

	@Override
	public int deleteUnnecessaryRecords() {
		logger.info("Executing method deleteUnnecessaryRecords of class UserDetailsServiceImpl");
		return userDetailsRepository.deleteUnnecessaryRecords();
	}

	@Override
	public int updateToDisableTheLink() {
		logger.info("Executing method updateToDisableTheLink of class UserDetailsServiceImpl");
		return userDetailsRepository.updateToDisableTheLink();
	}

	@Override
	public UserDetails findByEmail(String email) {
		logger.info("Executing method findByEmail of class UserDetailsServiceImpl");
		return userDetailsRepository.findByEmail(email);
	}

	@Override
	public int updateloggedInTime(Date loggedInTime, int uid) {
		logger.info("Executing method updateloggedInTime of class UserDetailsServiceImpl");
		return userDetailsRepository.updateloggedInTime(loggedInTime, uid);
	}

	@Override
	public int generateOtp(String otp, Date date, int uid) {
		logger.info("Executing method generateOtp of class UserDetailsServiceImpl");
		return userDetailsRepository.generateOtp(otp, date, uid);
	}

	@Override
	public int updatePassword(String password,Date updatedOn,String email) {
		logger.info("Executing method updatePassword of class UserDetailsServiceImpl");
		return userDetailsRepository.updatePassword(password,updatedOn, email);
	}
}
