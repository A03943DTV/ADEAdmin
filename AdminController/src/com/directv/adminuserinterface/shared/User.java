/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.shared;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
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

	/** The Constant SUB_ORG_PARAM. */
	public static final String SUB_ORG_PARAM = "subOrganization";

	/** The Constant USERID_PARAM. */
	public static final String USERID_PARAM = "userId";

	/** The Constant STATUS_SUCCESS. */
	public static final String STATUS_SUCCESS = "Success";

	/** The Constant STATUS_ERROR. */
	public static final String STATUS_ERROR = "Error";

	/** The user id. */
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String userId;

	/** The old user id. */
	@NotPersistent
	private String oldUserId;

	/** The bu action. */
	@NotPersistent
	private String buAction;

	/** The first name. */
	@Persistent
	private String firstName;

	/** The last name. */
	@Persistent
	private String lastName;

	/** The group. */
	@Persistent
	private String group;

	/** The organization. */
	@Persistent
	private String organization;

	/** The sub organization. */
	@Persistent
	private String subOrganization;

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
	@NotPersistent
	private String status;

	/** The error message. */
	@NotPersistent
	private String errorMessage;

	/** The last login date time. */
	@NotPersistent
	private String lastLoginDateTime;

	/** The created date time. */
	@NotPersistent
	private String createdDateTime;

	/** The admin. */
	@NotPersistent
	private Boolean admin;

	/** The reset password. */
	@NotPersistent
	private Boolean resetPassword;

	/**
	 * Instantiates a new user.
	 *
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param userId the user id
	 * @param group the group
	 * @param organization the organization
	 * @param subOrganization the sub organization
	 * @param location the location
	 * @param managersId the managers id
	 * @param role the role
	 * @param campaign the campaign
	 * @param credential the credential
	 */
	public User(String firstName, String lastName, String userId, String group, String organization, String subOrganization, String location,
			String managersId, String role, String campaign, String credential) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userId = userId;
		this.group = group;
		this.organization = organization;
		this.subOrganization = subOrganization;
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
	 * Copy data.
	 *
	 * @param toObject the to object
	 * @return the user
	 */
	public User copyData(User toObject) {

		toObject.setFirstName(this.getFirstName());
		toObject.setLastName(this.getLastName());
		toObject.setGroup(this.getGroup());
		toObject.setLocation(this.getLocation());
		toObject.setManagersId(this.getManagersId());
		toObject.setRole(this.getRole());
		toObject.setCampaign(this.getCampaign());
		toObject.setOldUserId(this.getOldUserId());
		toObject.setOrganization(this.getOrganization());
		toObject.setSubOrganization(this.getSubOrganization());
		toObject.setCredential(this.getCredential());

		return toObject;
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
	 * Gets the old user id.
	 *
	 * @return the old user id
	 */
	@XmlElement
	public String getOldUserId() {
		return oldUserId == null ? "" : oldUserId.trim();
	}

	/**
	 * Sets the old user id.
	 *
	 * @param oldUserId the new old user id
	 */
	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}

	/**
	 * Gets the bu action.
	 *
	 * @return the bu action
	 */
	@XmlElement
	public String getBuAction() {
		return buAction == null ? "" : buAction.trim();
	}

	/**
	 * Sets the bu action.
	 *
	 * @param buAction the new bu action
	 */
	public void setBuAction(String buAction) {
		this.buAction = buAction;
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
	 * Gets the organization.
	 *
	 * @return the organization
	 */
	@XmlElement
	public String getOrganization() {
		return organization == null ? "" : organization.trim();
	}

	/**
	 * Sets the organization.
	 *
	 * @param organization the new organization
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * Gets the sub organization.
	 *
	 * @return the sub organization
	 */
	@XmlElement
	public String getSubOrganization() {
		return subOrganization == null ? "" : subOrganization.trim();
	}

	/**
	 * Sets the sub organization.
	 *
	 * @param subOrganization the new sub organization
	 */
	public void setSubOrganization(String subOrganization) {
		this.subOrganization = subOrganization;
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

	/**
	 * Gets the admin.
	 *
	 * @return the admin
	 */
	@XmlElement
	public Boolean getAdmin() {
		return admin;
	}

	/**
	 * Sets the admin.
	 *
	 * @param admin the new admin
	 */
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	/**
	 * Gets the reset password.
	 *
	 * @return the reset password
	 */
	@XmlElement
	public Boolean getResetPassword() {
		return resetPassword;
	}

	/**
	 * Sets the reset password.
	 *
	 * @param resetPassword the new reset password
	 */
	public void setResetPassword(Boolean resetPassword) {
		this.resetPassword = resetPassword;
	}
}
