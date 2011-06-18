/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.server.dao;

import java.io.Serializable;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Blob;

// TODO: Auto-generated Javadoc
/**
 * The Class BulkUpload.
 */
@PersistenceCapable
public class BulkUpload implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4100110028638340070L;

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
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	/** The description. */
	@Persistent
	private String description;

	/** The blob. */
	@Persistent
	private Blob blob;

	/** The process status. */
	@Persistent
	private String processStatus;

	/** The result status. */
	@Persistent
	private String resultStatus;

	/** The submitted time. */
	@Persistent
	private String submittedTime;

	/** The process strat time. */
	@Persistent
	private String processStratTime;

	/** The process end time. */
	@Persistent
	private String processEndTime;

	/** The no of success records. */
	@Persistent
	private Long noOfSuccessRecords;

	/** The no of failure records. */
	@Persistent
	private Long noOfFailureRecords;

	/** The status. */
	@Persistent
	private String status;

	/**
	 * Instantiates a new bulk upload.
	 */
	public BulkUpload() {

	}

	/**
	 * Instantiates a new bulk upload.
	 *
	 * @param id the id
	 * @param description the description
	 * @param blob the blob
	 * @param processStatus the process status
	 * @param submittedTime the submitted time
	 */
	public BulkUpload(Long id, String description, Blob blob, String processStatus, String submittedTime) {
		super();
		this.id = id;
		this.description = description;
		this.blob = blob;
		this.processStatus = processStatus;
		this.submittedTime = submittedTime;
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
	 * Gets the blob.
	 *
	 * @return the blob
	 */
	public Blob getBlob() {
		return blob;
	}

	/**
	 * Sets the blob.
	 *
	 * @param blob the new blob
	 */
	public void setBlob(Blob blob) {
		this.blob = blob;
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
