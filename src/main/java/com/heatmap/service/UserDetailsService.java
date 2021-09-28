package com.heatmap.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.heatmap.model.UserDetails;
import com.heatmap.model.UploadedJsonData;

@Service
public interface UserDetailsService {
	
	int saveFileUploadData(UserDetails fileUploadInfo, UploadedJsonData uploadedJsonData);
	int updateloggedInTime(Date loggedInTime,int uid);
	UploadedJsonData save(UploadedJsonData uploadedJsonData);
	int generateOtp(String otp,Date date,int uid);
	int updatePassword(String password,Date updatedOn,String email);
	UserDetails findByEmail(String email);
	UserDetails findByUniqueId(String uniqueId);
	List<UserDetails> findAll();
	UserDetails save(UserDetails userDetails);
	int deleteUnnecessaryRecords();
	int updateToDisableTheLink();

}
