package com.heatmap.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "USERDETAILS")
public class UserDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8515680489769108513L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/*@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "userDetails_Sequence")
    @SequenceGenerator(name = "userDetails_Sequence", sequenceName = "UD_SEQ")*/
	@Column(name = "UID")
	private Integer uId;
	
	@Column(name = "FIRSTNAME")
	private String firstName;

	@Column(name = "LASTNAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "COMPANY")
	private String company;


	@Column(name = "NOOFDAYS")
	private int noOfDays;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "UNIQUEID")
	private String uniqueId;

	@Column(name = "CREATEDON")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Column(name = "UPDATEON")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;

	@OneToOne(mappedBy="userDetails")
	private UploadedJsonData uploadedJsonData;
	
	@Column(name = "LASTLOGGEDIN")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoggedIn;
	
	@Column(name="OTP")
	private String otp;
	
	@Column(name = "OTPEXPIRATIONTIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date otpExpirationTime;
	

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public int getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public UploadedJsonData getUploadedJsonData() {
		return uploadedJsonData;
	}

	public void setUploadedJsonData(UploadedJsonData uploadedJsonData) {
		this.uploadedJsonData = uploadedJsonData;
	}

	public Date getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(Date lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Date getOtpExpirationTime() {
		return otpExpirationTime;
	}

	public void setOtpExpirationTime(Date otpExpirationTime) {
		this.otpExpirationTime = otpExpirationTime;
	}

}
