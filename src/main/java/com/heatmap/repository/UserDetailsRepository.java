package com.heatmap.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.heatmap.model.UserDetails;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {

	//UserDetails findByIp(String ip);
	UserDetails findByEmail(String email);

	UserDetails findByUniqueId(String uniqueId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM FILEUPLOADINFO WHERE NOOFDAYS=0 AND UNIQUEID IS NULL", nativeQuery = true)
	int deleteUnnecessaryRecords();
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE FILEUPLOADINFO SET STATUS='INACTIVE' WHERE NOOFDAYS=0 AND UNIQUEID IS NOT NULL;", nativeQuery = true)
	int updateToDisableTheLink();
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE USERDETAILS SET LASTLOGGEDIN=?1 WHERE UID=?2", nativeQuery = true)
	int updateloggedInTime(Date loggedInTime,int uid);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE USERDETAILS U SET U.OTP=?1, U.OTPEXPIRATIONTIME =?2 WHERE U.UID=?3", nativeQuery = true)
	int generateOtp(String otp,Date date,int uid);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE USERDETAILS U SET U.PASSWORD=?1,U.UPDATEON=?2 WHERE U.EMAIL=?3", nativeQuery = true)
	int updatePassword(String password,Date updatedOn,String email);
}
