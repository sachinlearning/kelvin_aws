package com.heatmap.service;


import org.springframework.stereotype.Service;

import com.heatmap.model.UploadedJsonData;
import com.heatmap.model.UserDetails;

@Service
public interface UploadJsonDataService {
	UploadedJsonData findByUserDetails(UserDetails userDetails);

}
