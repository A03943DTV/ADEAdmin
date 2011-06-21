/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Transactional;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
@XmlRootElement(name = "userList")
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class User implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -6671354226828460153L;

	/** The Constant LOCATION_PARAM. */
	public static final String LOCATION_PARAM = "location";

	/** The Constant STATUS_SUCCESS. */
	public static final String STATUS_SUCCESS = "Success";

	/** The Constant STATUS_ERROR. */
	public static final String STATUS_ERROR = "Error";

	/** The user id. */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String userId;

	/** The first name. */
	@Persistent
	private String firstName;

	/** The last name. */
	@Persistent
	private String lastName;

	/** The group. */
	@Persistent
	private String group;

	/** The location. */
	@Persistent
	private String location;

	/** The managers id. */
	@Persistent
	private String managersId;

	/** The role. */
	@Persistent
	private String role;

	/** The campaign. */
	@Persistent
	private String campaign;

	/** The credential. */
	@Persistent
	private String credential;

	/** The status. */
	@Transactional
	private String status;

	/** The error message. */
	@Transactional
	private String errorMessage;

	/** The last login date time. */
	@Transactional
	private String lastLoginDateTime;

	/** The created date time. */
	@Transactional
	private String createdDateTime;

	/**
	 * Instantiates a new user.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param userId the user id
	 * @param group the group
	 * @param location the location
	 * @param managersId the managers id
	 * @param role the role
	 * @param campaign the campaign
	 * @param credential the credential
	 */
	public User(String firstName, String lastName, String userId, String group, String location, String managersId, String role, String campaign,
			String credential) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
		this.group = group;
		this.location = location;
		this.managersId = managersId;
		this.role = role;
		this.campaign = campaign;
		setCredential(credential);
	}

	/**
	 * Instantiates a new user.
	 */
	public User() {
		super();
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	@XmlElement
	public String getFirstName() {
		return firstName == null ? "" : firstName.trim();
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	@XmlElement
	public String getLastName() {
		return lastName == null ? "" : lastName.trim();
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	@XmlElement
	public String getUserId() {
		return userId == null ? "" : userId.trim();
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	@XmlElement
	public String getGroup() {
		return group == null ? "" : group.trim();
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	@XmlElement
	public String getLocation() {
		return location == null ? "" : location.trim();
	}

	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the managers id.
	 *
	 * @return the managers id
	 */
	@XmlElement
	public String getManagersId() {
		return managersId == null ? "" : managersId.trim();
	}

	/**
	 * Sets the managers id.
	 *
	 * @param managersId the new managers id
	 */
	public void setManagersId(String managersId) {
		this.managersId = managersId;
	}

	/**
	 * Gets the role.
	 *
	 * @return the role
	 */
	@XmlElement
	public String getRole() {
		return role == null ? "" : role.trim();
	}

	/**
	 * Sets the role.
	 *
	 * @param role the new role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * Gets the campaign.
	 *
	 * @return the campaign
	 */
	@XmlElement
	public String getCampaign() {
		return campaign == null ? "" : campaign.trim();
	}

	/**
	 * Sets the campaign.
	 *
	 * @param campaign the new campaign
	 */
	public void setCampaign(String campaign) {
		this.campaign = campaign;
	}

	/**
	 * Gets the credential.
	 *
	 * @return the credential
	 */
	@XmlElement
	public String getCredential() {
		return credential == null ? "" : credential.trim();
	}

	/**
	 * Sets the credential.
	 *
	 * @param credential the new credential
	 */
	public void setCredential(String credential) {
		if (credential != null && !credential.equals("")) {
			this.credential = credential;
		}
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	@XmlElement
	public String getStatus() {
		return status == null ? "" : status.trim();
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	@XmlElement
	public String getErrorMessage() {
		return errorMessage == null ? "" : errorMessage.trim();
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the last login date time.
	 *
	 * @return the last login date time
	 */
	@XmlElement
	public String getLastLoginDateTime() {
		return lastLoginDateTime == null ? "" : lastLoginDateTime.substring(0, 4).equals("1969") ? "Never logged in" : lastLoginDateTime;
	}

	/**
	 * Sets the last login date time.
	 *
	 * @param lastLoginDateTime the new last login date time
	 */
	public void setLastLoginDateTime(String lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}

	/**
	 * Gets the created date time.
	 *
	 * @return the created date time
	 */
	@XmlElement
	public String getCreatedDateTime() {
		return createdDateTime == null ? "" : createdDateTime.trim();
	}

	/**
	 * Sets the created date time.
	 *
	 * @param createdDateTime the new created date time
	 */
	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

}
