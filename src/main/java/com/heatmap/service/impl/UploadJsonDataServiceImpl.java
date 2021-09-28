package com.heatmap.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.heatmap.model.UploadedJsonData;
import com.heatmap.model.UserDetails;
import com.heatmap.repository.UploadedJsonDataRepository;
import com.heatmap.service.UploadJsonDataService;

@Component
public class UploadJsonDataServiceImpl implements UploadJsonDataService {

	private static final Logger logger=LoggerFactory.getLogger(UploadJsonDataServiceImpl.class);
	
	@Autowired
	private UploadedJsonDataRepository uploadedJsonDataRepository;

	@Override
	public UploadedJsonData findByUserDetails(UserDetails userDetails) {
		logger.info("Executing method findByUid of class UploadJsonDataServiceImpl");
		return uploadedJsonDataRepository.findByUserDetails(userDetails);
	}

}
