/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.shared;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportBean.
 */
public class ReportBean implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -915957494266395880L;

	/** The date. */
	private String date;

	/** The account id. */
	private String accountId;

	/** The account name. */
	private String accountName;

	/** The status. */
	private String status;

	/** The quota in mb. */
	private String quotaInMB;

	/** The usage in bytes. */
	private String usageInBytes;

	/** The primary account id. */
	private String primaryAccountId;

	/** The primary account name. */
	private String primaryAccountName;

	/** The creation date. */
	private String creationDate;

	/** The last login date. */
	private String lastLoginDate;

	/** The last web mail date. */
	private String lastWebMailDate;

	/** The sur name. */
	private String surName;

	/** The given name. */
	private String givenName;

	/** The service tier. */
	private String serviceTier;

	/** The channel. */
	private String channel;

	/** The suspension reason. */
	private String suspensionReason;

	/** The last popup date. */
	private String lastPopupDate;

	/** The creation time. */
	private String creationTime;

	/** The last login time. */
	private String lastLoginTime;

	/** The last web mail time. */
	private String lastWebMailTime;

	/** The last popuptime. */
	private String lastPopuptime;

	/**
	 * Instantiates a new report bean.
	 */
	public ReportBean() {

	}

	/**
	 * Instantiates a new report bean.
	 *
	 * @param date the date
	 * @param accountId the account id
	 * @param accountName the account name
	 * @param status the status
	 * @param quotaInMB the quota in mb
	 * @param usageInBytes the usage in bytes
	 * @param primaryAccountId the primary account id
	 * @param primaryAccountName the primary account name
	 * @param creationDate the creation date
	 * @param lastLoginDate the last login date
	 * @param lastWebMailDate the last web mail date
	 * @param surName the sur name
	 * @param givenName the given name
	 * @param serviceTier the service tier
	 * @param channel the channel
	 * @param suspensionReason the suspension reason
	 * @param lastPopupDate the last popup date
	 * @param creationTime the creation time
	 * @param lastLoginTime the last login time
	 * @param lastWebMailTime the last web mail time
	 * @param lastPopuptime the last popuptime
	 */
	public ReportBean(String date, String accountId, String accountName, String status, String quotaInMB, String usageInBytes,
			String primaryAccountId, String primaryAccountName, String creationDate, String lastLoginDate, String lastWebMailDate, String surName,
			String givenName, String serviceTier, String channel, String suspensionReason, String lastPopupDate, String creationTime,
			String lastLoginTime, String lastWebMailTime, String lastPopuptime) {
		super();
		this.date = date;
		this.accountId = accountId;
		this.accountName = accountName;
		this.status = status;
		this.quotaInMB = quotaInMB;
		this.usageInBytes = usageInBytes;
		this.primaryAccountId = primaryAccountId;
		this.primaryAccountName = primaryAccountName;
		this.creationDate = creationDate;
		this.lastLoginDate = lastLoginDate;
		this.lastWebMailDate = lastWebMailDate;
		this.surName = surName;
		this.givenName = givenName;
		this.serviceTier = serviceTier;
		this.channel = channel;
		this.suspensionReason = suspensionReason;
		this.lastPopupDate = lastPopupDate;
		this.creationTime = creationTime;
		this.lastLoginTime = lastLoginTime;
		this.lastWebMailTime = lastWebMailTime;
		this.lastPopuptime = lastPopuptime;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId the new account id
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the account name.
	 *
	 * @return the account name
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * Sets the account name.
	 *
	 * @param accountName the new account name
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
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
	 * Gets the quota in mb.
	 *
	 * @return the quota in mb
	 */
	public String getQuotaInMB() {
		return quotaInMB;
	}

	/**
	 * Sets the quota in mb.
	 *
	 * @param quotaInMB the new quota in mb
	 */
	public void setQuotaInMB(String quotaInMB) {
		this.quotaInMB = quotaInMB;
	}

	/**
	 * Gets the usage in bytes.
	 *
	 * @return the usage in bytes
	 */
	public String getUsageInBytes() {
		return usageInBytes;
	}

	/**
	 * Sets the usage in bytes.
	 *
	 * @param usageInBytes the new usage in bytes
	 */
	public void setUsageInBytes(String usageInBytes) {
		this.usageInBytes = usageInBytes;
	}

	/**
	 * Gets the primary account id.
	 *
	 * @return the primary account id
	 */
	public String getPrimaryAccountId() {
		return primaryAccountId;
	}

	/**
	 * Sets the primary account id.
	 *
	 * @param primaryAccountId the new primary account id
	 */
	public void setPrimaryAccountId(String primaryAccountId) {
		this.primaryAccountId = primaryAccountId;
	}

	/**
	 * Gets the primary account name.
	 *
	 * @return the primary account name
	 */
	public String getPrimaryAccountName() {
		return primaryAccountName;
	}

	/**
	 * Sets the primary account name.
	 *
	 * @param primaryAccountName the new primary account name
	 */
	public void setPrimaryAccountName(String primaryAccountName) {
		this.primaryAccountName = primaryAccountName;
	}

	/**
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 *
	 * @param creationDate the new creation date
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the last login date.
	 *
	 * @return the last login date
	 */
	public String getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * Sets the last login date.
	 *
	 * @param lastLoginDate the new last login date
	 */
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * Gets the last web mail date.
	 *
	 * @return the last web mail date
	 */
	public String getLastWebMailDate() {
		return lastWebMailDate;
	}

	/**
	 * Sets the last web mail date.
	 *
	 * @param lastWebMailDate the new last web mail date
	 */
	public void setLastWebMailDate(String lastWebMailDate) {
		this.lastWebMailDate = lastWebMailDate;
	}

	/**
	 * Gets the sur name.
	 *
	 * @return the sur name
	 */
	public String getSurName() {
		return surName;
	}

	/**
	 * Sets the sur name.
	 *
	 * @param surName the new sur name
	 */
	public void setSurName(String surName) {
		this.surName = surName;
	}

	/**
	 * Gets the given name.
	 *
	 * @return the given name
	 */
	public String getGivenName() {
		return givenName;
	}

	/**
	 * Sets the given name.
	 *
	 * @param givenName the new given name
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * Gets the service tier.
	 *
	 * @return the service tier
	 */
	public String getServiceTier() {
		return serviceTier;
	}

	/**
	 * Sets the service tier.
	 *
	 * @param serviceTier the new service tier
	 */
	public void setServiceTier(String serviceTier) {
		this.serviceTier = serviceTier;
	}

	/**
	 * Gets the channel.
	 *
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * Sets the channel.
	 *
	 * @param channel the new channel
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * Gets the suspension reason.
	 *
	 * @return the suspension reason
	 */
	public String getSuspensionReason() {
		return suspensionReason;
	}

	/**
	 * Sets the suspension reason.
	 *
	 * @param suspensionReason the new suspension reason
	 */
	public void setSuspensionReason(String suspensionReason) {
		this.suspensionReason = suspensionReason;
	}

	/**
	 * Gets the last popup date.
	 *
	 * @return the last popup date
	 */
	public String getLastPopupDate() {
		return lastPopupDate;
	}

	/**
	 * Sets the last popup date.
	 *
	 * @param lastPopupDate the new last popup date
	 */
	public void setLastPopupDate(String lastPopupDate) {
		this.lastPopupDate = lastPopupDate;
	}

	/**
	 * Gets the creation time.
	 *
	 * @return the creation time
	 */
	public String getCreationTime() {
		return creationTime;
	}

	/**
	 * Sets the creation time.
	 *
	 * @param creationTime the new creation time
	 */
	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	/**
	 * Gets the last login time.
	 *
	 * @return the last login time
	 */
	public String getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * Sets the last login time.
	 *
	 * @param lastLoginTime the new last login time
	 */
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * Gets the last web mail time.
	 *
	 * @return the last web mail time
	 */
	public String getLastWebMailTime() {
		return lastWebMailTime;
	}

	/**
	 * Sets the last web mail time.
	 *
	 * @param lastWebMailTime the new last web mail time
	 */
	public void setLastWebMailTime(String lastWebMailTime) {
		this.lastWebMailTime = lastWebMailTime;
	}

	/**
	 * Gets the last popuptime.
	 *
	 * @return the last popuptime
	 */
	public String getLastPopuptime() {
		return lastPopuptime;
	}

	/**
	 * Sets the last popuptime.
	 *
	 * @param lastPopuptime the new last popuptime
	 */
	public void setLastPopuptime(String lastPopuptime) {
		this.lastPopuptime = lastPopuptime;
	}

}
