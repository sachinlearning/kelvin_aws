package com.heatmap.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.heatmap.model.UserDetails;
import com.heatmap.service.UserDetailsService;

@Controller
public class GetDataController {
	
	private static final Logger logger = LoggerFactory.getLogger(GetDataController.class);

	@Autowired
	private UserDetailsService userDetailsService;

	/**Method returns json to plot the chart based on the uniqueId
	 * @param uniqueId it is used to identify the saved map data
	 * @return it return JSON which is used to plot the chart
	 */
	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@GetMapping(value = "/api/getMap", produces = "application/json")
	public ResponseEntity<String> getSavedDataToPlotTheChart(@RequestParam("uniqueId") String uniqueId) {
		logger.info("Executing method getSavedMapData of class GetDataController");
		UserDetails userDetails = userDetailsService.findByUniqueId(uniqueId);
		if (userDetails == null)
			return new ResponseEntity<>("Data Not Found", HttpStatus.NOT_FOUND);
		else {
			if (userDetails.getStatus().equalsIgnoreCase("ACTIVE")) {
				return new ResponseEntity<>(userDetails.getUploadedJsonData().getJson(), HttpStatus.OK);
			}
			return new ResponseEntity<>("Link Expired", HttpStatus.OK);
		}
	}
}
