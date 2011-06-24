/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
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
	 * Gets the bulk upload by id.
	 *
	 * @param id the id
	 * @return the bulk upload by id
	 */
	public BulkUpload getBulkUploadById(Long id) {

		return (BulkUpload) get(BulkUpload.class, id);
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
	 * @param userId the user id
	 * @return the bulk upload results
	 */
	public List<BulkUpload> getBulkUploadResults(String userId) {

		return getList(BulkUpload.class, BulkUpload.USERID_PROPERTY, userId);
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
