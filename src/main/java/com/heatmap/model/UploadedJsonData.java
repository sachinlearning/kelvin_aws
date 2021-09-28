package com.heatmap.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "UPLOADEDJSONDATA")
public class UploadedJsonData implements Serializable {

	
	private static final long serialVersionUID = -3067871867519479651L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/*@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "uploadJsonData_Sequence")
    @SequenceGenerator(name = "uploadJsonData_Sequence", sequenceName = "UD_SEQ")*/
	@Column(name = "JID")
	private Integer jid;

	@Column(name = "JSOND")
	private String json;

	@Column(name = "CREATEDON")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Column(name = "UPDATEON")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedOn;
	
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "UID")
	private UserDetails userDetails;


	public Integer getJid() {
		return jid;
	}


	public void setJid(Integer jid) {
		this.jid = jid;
	}


	public String getJson() {
		return json;
	}


	public void setJson(String json) {
		this.json = json;
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


	public UserDetails getUserDetails() {
		return userDetails;
	}


	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

}
