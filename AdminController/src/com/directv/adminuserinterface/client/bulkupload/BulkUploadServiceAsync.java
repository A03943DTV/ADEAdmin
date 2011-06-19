/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client.bulkupload;

import java.util.List;

import com.directv.adminuserinterface.shared.BulkUploadDto;
import com.google.gwt.user.client.rpc.AsyncCallback;

// TODO: Auto-generated Javadoc
/**
 * The Interface BulkUploadServiceAsync.
 */
public interface BulkUploadServiceAsync {

	/**
	 * Process bulk upload.
	 *
	 * @param callback the callback
	 */
	void processBulkUpload(AsyncCallback<Void> callback);

	/**
	 * Dummy.
	 *
	 * @param bulkUploadDto the bulk upload dto
	 * @param callback the callback
	 */
	void dummy(BulkUploadDto bulkUploadDto, AsyncCallback<Void> callback);

	/**
	 * Gets the bulk upload results.
	 *
	 * @param userId the user id
	 * @param callback the callback
	 * @return the bulk upload results
	 */
	void getBulkUploadResults(String userId, AsyncCallback<List<BulkUploadDto>> callback);

}
