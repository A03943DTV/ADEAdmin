/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

// TODO: Auto-generated Javadoc
/**
 * The Class BulkUploadDto.
 */
public class BulkUploadDto implements IsSerializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8438043394383476179L;

	/** The Constant PROCESS_STATUS_TO_BE_PROCESSED. */
	public static final String PROCESS_STATUS_TO_BE_PROCESSED = "ToBeProcessed";

	/** The Constant PROCESS_STATUS_PROCESSING. */
	public static final String PROCESS_STATUS_PROCESSING = "Processing";

	/** The Constant PROCESS_STATUS_COMPLETED. */
	public static final String PROCESS_STATUS_COMPLETED = "Completed";

	/** The Constant PROCESS_STATUS_PROPERTY. */
	public static final String PROCESS_STATUS_PROPERTY = "processStatus";

	/** The Constant STATUS_SUCCESS. */
	public static final String STATUS_SUCCESS = "Success";

	/** The Constant STATUS_ERROR. */
	public static final String STATUS_ERROR = "Error";

	/** The id. */
	private Long id;

	/** The description. */
	private String description;

	/** The user id. */
	private String userId;

	/** The blob byte. */
	private byte[] blobByte;

	/** The process status. */
	private String processStatus;

	/** The submitted time. */
	private String submittedTime;

	/** The process strat time. */
	private String processStratTime;

	/** The process end time. */
	private String processEndTime;

	/** The no of success records. */
	private Long noOfSuccessRecords;

	/** The no of failure records. */
	private Long noOfFailureRecords;

	/** The status. */
	private String status;

	/**
	 * Instantiates a new bulk upload dto.
	 *
	 * @param id the id
	 * @param description the description
	 * @param userId the user id
	 * @param blobByte the blob byte
	 * @param processStatus the process status
	 * @param submittedTime the submitted time
	 * @param processStratTime the process strat time
	 * @param processEndTime the process end time
	 * @param noOfSuccessRecords the no of success records
	 * @param noOfFailureRecords the no of failure records
	 * @param status the status
	 */
	public BulkUploadDto(Long id, String description, String userId, byte[] blobByte, String processStatus, String submittedTime,
			String processStratTime, String processEndTime, Long noOfSuccessRecords, Long noOfFailureRecords, String status) {

		super();
		this.id = id;
		this.description = description;
		this.userId = userId;
		this.blobByte = blobByte;
		this.processStatus = processStatus;
		this.submittedTime = submittedTime;
		this.processStratTime = processStratTime;
		this.processEndTime = processEndTime;
		this.noOfSuccessRecords = noOfSuccessRecords;
		this.noOfFailureRecords = noOfFailureRecords;
		this.status = status;
	}

	/**
	 * Instantiates a new bulk upload dto.
	 */
	public BulkUploadDto() {

	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description == null ? "" : description.trim();
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
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
	 * Gets the blob byte.
	 *
	 * @return the blob byte
	 */
	public byte[] getBlobByte() {
		return blobByte;
	}

	/**
	 * Sets the blob byte.
	 *
	 * @param blobByte the new blob byte
	 */
	public void setBlobByte(byte[] blobByte) {
		this.blobByte = blobByte;
	}

	/**
	 * Gets the process status.
	 *
	 * @return the process status
	 */
	public String getProcessStatus() {
		return processStatus == null ? "" : processStatus.trim();
	}

	/**
	 * Sets the process status.
	 *
	 * @param processStatus the new process status
	 */
	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	/**
	 * Gets the submitted time.
	 *
	 * @return the submitted time
	 */
	public String getSubmittedTime() {
		return submittedTime == null ? "" : submittedTime.trim();
	}

	/**
	 * Sets the submitted time.
	 *
	 * @param submittedTime the new submitted time
	 */
	public void setSubmittedTime(String submittedTime) {
		this.submittedTime = submittedTime;
	}

	/**
	 * Gets the process strat time.
	 *
	 * @return the process strat time
	 */
	public String getProcessStratTime() {
		return processStratTime == null ? "" : processStratTime.trim();
	}

	/**
	 * Sets the process strat time.
	 *
	 * @param processStratTime the new process strat time
	 */
	public void setProcessStratTime(String processStratTime) {
		this.processStratTime = processStratTime;
	}

	/**
	 * Gets the process end time.
	 *
	 * @return the process end time
	 */
	public String getProcessEndTime() {
		return processEndTime == null ? "" : processEndTime.trim();
	}

	/**
	 * Sets the process end time.
	 *
	 * @param processEndTime the new process end time
	 */
	public void setProcessEndTime(String processEndTime) {
		this.processEndTime = processEndTime;
	}

	/**
	 * Gets the no of success records.
	 *
	 * @return the no of success records
	 */
	public Long getNoOfSuccessRecords() {
		return noOfSuccessRecords == null ? 0 : noOfSuccessRecords;
	}

	/**
	 * Sets the no of success records.
	 *
	 * @param noOfSuccessRecords the new no of success records
	 */
	public void setNoOfSuccessRecords(Long noOfSuccessRecords) {
		this.noOfSuccessRecords = noOfSuccessRecords;
	}

	/**
	 * Gets the no of failure records.
	 *
	 * @return the no of failure records
	 */
	public Long getNoOfFailureRecords() {
		return noOfFailureRecords == null ? 0 : noOfFailureRecords;
	}

	/**
	 * Sets the no of failure records.
	 *
	 * @param noOfFailureRecords the new no of failure records
	 */
	public void setNoOfFailureRecords(Long noOfFailureRecords) {
		this.noOfFailureRecords = noOfFailureRecords;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
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
}
