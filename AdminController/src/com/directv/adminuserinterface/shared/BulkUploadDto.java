/*
 * Author : Meiy
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

	/** The id. */
	private Long id;

	/** The description. */
	private String description;

	/** The blob byte. */
	private byte[] blobByte;

	/** The process status. */
	private String processStatus;

	/** The result status. */
	private String resultStatus;

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
	 * @param blobByte the blob byte
	 * @param processStatus the process status
	 * @param resultStatus the result status
	 * @param submittedTime the submitted time
	 * @param processStratTime the process strat time
	 * @param processEndTime the process end time
	 * @param noOfSuccessRecords the no of success records
	 * @param noOfFailureRecords the no of failure records
	 * @param status the status
	 */
	public BulkUploadDto(Long id, String description, byte[] blobByte, String processStatus, String resultStatus, String submittedTime,
			String processStratTime, String processEndTime, Long noOfSuccessRecords, Long noOfFailureRecords, String status) {

		super();
		this.id = id;
		this.description = description;
		this.blobByte = blobByte;
		this.processStatus = processStatus;
		this.resultStatus = resultStatus;
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
		return description;
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
		return processStatus;
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
	 * Gets the result status.
	 *
	 * @return the result status
	 */
	public String getResultStatus() {
		return resultStatus;
	}

	/**
	 * Sets the result status.
	 *
	 * @param resultStatus the new result status
	 */
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	/**
	 * Gets the submitted time.
	 *
	 * @return the submitted time
	 */
	public String getSubmittedTime() {
		return submittedTime;
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
		return processStratTime;
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
		return processEndTime;
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
		return noOfSuccessRecords;
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
		return noOfFailureRecords;
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
}
