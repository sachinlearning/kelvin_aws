package com.heatmap.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.heatmap.model.UserDetails;
import com.heatmap.service.UserDetailsService;

@Component
public class UpdationDeletionScheduler {

	private static final Logger logger=LoggerFactory.getLogger(UpdationDeletionScheduler.class);
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Scheduled(cron = "${cronExpression}")
	public void scheduleTaskWithCronExpression() {
		logger.info("Executing method scheduleTaskWithCronExpression of class UpdationDeletionScheduler");
		/*deleteUnwantedData();
		updateToDisableEmailLink();
		decreaseNoOfDays();*/
	}

	public void decreaseNoOfDays() {
		logger.info("Executing method decreaseNoOfDays of class UpdationDeletionScheduler");
		List<UserDetails> listOfFilInfos = userDetailService.findAll();
		listOfFilInfos.stream().forEach(fileUploadInfo -> {
			int noOfDays = fileUploadInfo.getNoOfDays();
			fileUploadInfo.setNoOfDays(noOfDays == 0 ? 0 : noOfDays - 1);
			userDetailService.save(fileUploadInfo);
		});
	}

	public void deleteUnwantedData() {
		logger.info("Executing method deleteUnwantedData of class UpdationDeletionScheduler");
		userDetailService.deleteUnnecessaryRecords();
	}

	public void updateToDisableEmailLink() {
		logger.info("Executing method updateToDisableEmailLink of class UpdationDeletionScheduler");
		userDetailService.updateToDisableTheLink();
	}
}
