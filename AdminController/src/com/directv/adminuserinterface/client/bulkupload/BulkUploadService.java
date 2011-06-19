/*
 * Author  : Meiyazhagan Arjunan
 * Company : Ilink Multitech Solutions
 */
package com.directv.adminuserinterface.client.bulkupload;

import java.util.List;

import com.directv.adminuserinterface.shared.BulkUploadDto;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

// TODO: Auto-generated Javadoc
/**
 * The Interface BulkUploadService.
 */
@RemoteServiceRelativePath("bulkUpload")
public interface BulkUploadService extends RemoteService {

	/**
	 * Process bulk upload.
	 */
	void processBulkUpload();

	/**
	 * Dummy.
	 *
	 * @param bulkUploadDto the bulk upload dto
	 */
	public void dummy(BulkUploadDto bulkUploadDto);

	/**
	 * Gets the bulk upload results.
	 *
	 * @param userId the user id
	 * @return the bulk upload results
	 */
	List<BulkUploadDto> getBulkUploadResults(String userId);

}
