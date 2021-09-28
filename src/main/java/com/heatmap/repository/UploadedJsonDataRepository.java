package com.heatmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.heatmap.model.UploadedJsonData;
import com.heatmap.model.UserDetails;

@Repository
public interface UploadedJsonDataRepository extends JpaRepository<UploadedJsonData, Integer> {

	UploadedJsonData findByUserDetails(UserDetails userDetails);
}
