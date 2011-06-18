/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.rest;

import java.util.List;

import javax.jdo.PersistenceManager;

import com.directv.adminuserinterface.server.dao.BulkUpload;


// TODO: Auto-generated Javadoc
/**
 * The Class BulkUploadDaoImpl.
 */
public class BulkUploadDaoImpl extends GenericDaoImpl {

	/**
	 * Adds the bulk upload.
	 *
	 * @param bulkUpload the bulk upload
	 * @return the bulk upload
	 */
	public BulkUpload addBulkUpload(BulkUpload bulkUpload) {

		return (BulkUpload) add(bulkUpload);
	}

	/**
	 * Gets the un processed bu list.
	 *
	 * @return the un processed bu list
	 */
	public List<BulkUpload> getUnProcessedBUList() {

		return getList(BulkUpload.class, BulkUpload.PROCESS_STATUS_PROPERTY, BulkUpload.PROCESS_STATUS_TO_BE_PROCESSED);
	}

	/**
	 * Gets the bulk upload results.
	 *
	 * @return the bulk upload results
	 */
	public List<BulkUpload> getBulkUploadResults() {

		return getNotEqualList(BulkUpload.class, BulkUpload.PROCESS_STATUS_PROPERTY, BulkUpload.PROCESS_STATUS_TO_BE_PROCESSED);
	}

	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount() {

		return getList(BulkUpload.class, null, null).size();
	}

	/**
	 * Update bulk upload.
	 *
	 * @param bulkUpload the bulk upload
	 * @return the bulk upload
	 */
	public BulkUpload updateBulkUpload(BulkUpload bulkUpload) {

		BulkUpload bulkUploadDB = null;
		PersistenceManager pm = PMF.getPersistenceManagerFactory().getPersistenceManager();
		try {

			pm.currentTransaction().begin();

			bulkUploadDB = pm.getObjectById(BulkUpload.class, bulkUpload.getId());
			bulkUploadDB.setBlob(bulkUpload.getBlob());
			bulkUploadDB.setDescription(bulkUpload.getDescription());
			bulkUploadDB.setProcessStatus(bulkUpload.getProcessStatus());
			bulkUploadDB.setResultStatus(bulkUpload.getResultStatus());
			bulkUploadDB.setSubmittedTime(bulkUpload.getSubmittedTime());
			bulkUploadDB.setProcessStratTime(bulkUpload.getProcessStratTime());
			bulkUploadDB.setProcessEndTime(bulkUpload.getProcessEndTime());
			bulkUploadDB.setNoOfSuccessRecords(bulkUpload.getNoOfSuccessRecords());
			bulkUploadDB.setNoOfFailureRecords(bulkUpload.getNoOfFailureRecords());
			bulkUploadDB.setStatus(bulkUpload.getStatus());

			pm.makePersistent(bulkUploadDB);
			pm.currentTransaction().commit();

		} catch (Exception ex) {
			pm.currentTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			pm.close();
		}
		return bulkUpload;
	}
}
